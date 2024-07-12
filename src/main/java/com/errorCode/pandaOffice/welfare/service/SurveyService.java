package com.errorCode.pandaOffice.welfare.service;

import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.employee.domain.entity.Job;
import com.errorCode.pandaOffice.employee.domain.repository.EmployeeRepository;
import com.errorCode.pandaOffice.welfare.domain.entity.ReplyRecord;
import com.errorCode.pandaOffice.welfare.domain.entity.Survey;
import com.errorCode.pandaOffice.welfare.domain.entity.SurveyCategory;
import com.errorCode.pandaOffice.welfare.domain.entity.SurveyQuestion;
import com.errorCode.pandaOffice.welfare.domain.repository.ReplyRecordRepository;
import com.errorCode.pandaOffice.welfare.domain.repository.SurveyCategoryRepository;
import com.errorCode.pandaOffice.welfare.domain.repository.SurveyQuestionRepository;
import com.errorCode.pandaOffice.welfare.domain.repository.SurveyRepository;
import com.errorCode.pandaOffice.welfare.dto.request.CreateSurveyRequest;
import com.errorCode.pandaOffice.welfare.dto.request.ReplyRecordRequest;
import com.errorCode.pandaOffice.welfare.dto.request.UpdateSurveyQuestionRequest;
import com.errorCode.pandaOffice.welfare.dto.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SurveyService {



    private final SurveyRepository surveyRepository;
    private final SurveyCategoryRepository categoryRepository;
    private final SurveyQuestionRepository surveyQuestionRepository;
    private final ReplyRecordRepository replyRecordRepository;
    private  final EmployeeRepository employeeRepository;

//    모든 설문 카테고리를 가져오는 메서드
    public List<SurveyCategoryDTO> getAllCategories() {
        List<SurveyCategory> categories = categoryRepository.findAll();
        return categories.stream()
            .map(category -> new SurveyCategoryDTO(category.getId(), category.getCategoryName()))
            .collect(Collectors.toList());
}

// 설문 생성
    public int createSurvey(CreateSurveyRequest request) {
        log.debug("Received request(외않된데): {}", request);

        if (request.getQuestion() == null) {
            throw new IllegalArgumentException("Questions must not be null(아니 왜??)");
        }

        /* DTO(request)를 바탕으로 엔티티 작성 */
        List<SurveyQuestion> question = request.getQuestion().stream().map(
                que -> SurveyQuestion.of(que)
        ).toList(); // 만들어진 질문 리스트 엔티티
        /* 카테고리 ID 로 카테고리 찾아오기 */
        SurveyCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow();

        /* 만들어진 엔티티를 survey 엔티티 List 에 할당 (casCade 이용) */
        Survey survey = Survey.of(request, question, category);
        /* 레파지토리에 만든 엔티티를 저장 명령 */
        surveyRepository.save(survey);

        /* 변경사항이 모두 DB에 저장됨 */
        return survey.getId();
    }

    //설문 조회
    public List<SurveyResponse> getAllSurvey() {
        List<Survey> surveys = surveyRepository.findAll();
        return surveys.stream().map(
                surveyEntity->SurveyResponse.of(surveyEntity)
        ).toList();
    }


//대시보드에서 사용
public SurveyResponse getActiveSurvey() {
    LocalDate today = LocalDate.now();
    Survey survey = surveyRepository.findFirstByStartDateLessThanEqualAndEndDateGreaterThanEqual(today, today)
            .orElseThrow(() -> new RuntimeException("No active survey found"));
    return SurveyResponse.of(survey);
}


//설문조회(질문, 문항 포함 차트 뿌려주기용, 설문 날짜 포함)
public SurveyDetailsResponse getSurveyDetails(int surveyId) {
    LocalDate today = LocalDate.now();
    Survey survey = surveyRepository.findByIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(surveyId, today, today)
            .orElseThrow(() -> new RuntimeException("Survey not found or not active"));

    List<SurveyQuestionDTO> questionDTOs = survey.getQuestion().stream()
            .map(SurveyQuestionDTO::new)
            .collect(Collectors.toList());

    List<ReplyRecordDTO> replyDTOs = survey.getReplyRecords().stream()
            .map(ReplyRecordDTO::new)
            .collect(Collectors.toList());

    System.out.println("쳌: " + survey + "\n" + questionDTOs + "\n" + replyDTOs);

    return new SurveyDetailsResponse(survey, questionDTOs, replyDTOs);
}

//    응답자 수 조회
    public int getSurveyRespondentCount(int surveyId) {
        return replyRecordRepository.countDistinctBySurveyId(surveyId);
    }

    //질문 수정
    public SurveyQuestionDTO updateSurveyQuestion(int id, UpdateSurveyQuestionRequest request) {
        SurveyQuestion existingQuestion = surveyQuestionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SurveyQuestion not found"));

        Survey survey = surveyRepository.findById(request.getSurveyId())
                .orElseThrow(() -> new RuntimeException("Survey not found"));

        existingQuestion.updateWithSurvey(request.getQuestionOrder(), request.getQuestion(), survey);

        SurveyQuestion updatedQuestion = surveyQuestionRepository.save(existingQuestion);

        // 엔티티를 DTO로 변환하여 반환
        return new SurveyQuestionDTO(updatedQuestion);
    }

    //질문 삭제
    @Transactional
    public void deleteSurveyQuestion(int id) {
        SurveyQuestion existingQuestion = surveyQuestionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SurveyQuestion not found"));
        surveyQuestionRepository.delete(existingQuestion);
    }



    // 질문 문항 응답 반환
    public void saveSurveyReply(ReplyRecordRequest replyRecordRequest) {
        Employee employee = employeeRepository.findById(replyRecordRequest.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        //        questionId값 확인
        System.out.println("Received questionId: " + replyRecordRequest.getQuestionId());

        Survey survey = surveyRepository.findById(replyRecordRequest.getSurveyId())
                .orElseThrow(() -> new RuntimeException("Survey not found"));


        //원래코드임
        SurveyQuestion question = surveyQuestionRepository.findById(replyRecordRequest.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found"));

        // 중복 참여 확인
        boolean alreadyParticipated = replyRecordRepository.findBySurveyAndEmployeeAndQuestion(survey, employee, question).isPresent();
        if (alreadyParticipated) {
            throw new RuntimeException("참여한 설문입니다.");
        }

        Job job = employee.getJob();

        ReplyRecord replyRecord = replyRecordRepository.findBySurveyAndEmployeeAndQuestion(survey, employee, question)
                .orElse(new ReplyRecord(employee, job, survey, question, replyRecordRequest.getAnswer()));


        replyRecord.updateAnswer(replyRecordRequest.getAnswer());

        replyRecordRepository.save(replyRecord);

    }


    // 특정 질문(문항)을 조회
    public SurveyQuestionDTO getSurveyQuestionById(int questionId) {
        SurveyQuestion question = surveyQuestionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        return new SurveyQuestionDTO(question);
    }




    //카테고리 ID로 설문을 조회
    public List<SurveyResponse> getSurveysByCategoryId(int categoryId) {
        System.out.println("이건뜸?");
        List<Survey> surveys = surveyRepository.findByCategoryId(categoryId);
        System.out.println("Found " + surveys.size() + " surveys for category ID " + categoryId);
    return surveys.stream().map(SurveyResponse::of).collect(Collectors.toList());
}



}
