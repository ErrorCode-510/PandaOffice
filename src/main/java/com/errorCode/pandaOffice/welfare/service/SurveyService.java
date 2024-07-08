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
import com.errorCode.pandaOffice.welfare.dto.response.ReplyRecordDTO;
import com.errorCode.pandaOffice.welfare.dto.response.SurveyDetailsResponse;
import com.errorCode.pandaOffice.welfare.dto.response.SurveyQuestionDTO;
import com.errorCode.pandaOffice.welfare.dto.response.SurveyResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    public int createSurvey(CreateSurveyRequest request) {
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

//설문조회(질문, 문항 포함 차트 뿌려주기용)
    public SurveyDetailsResponse getSurveyDetails(int surveyId) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("Survey not found"));

        List<SurveyQuestionDTO> questionDTOs = survey.getQuestion().stream()
                .map(SurveyQuestionDTO::new)
                .collect(Collectors.toList());

        List<ReplyRecordDTO> replyDTOs = survey.getReplyRecords().stream()
                .map(ReplyRecordDTO::new)
                .collect(Collectors.toList());

        System.out.println("쳌: " + survey +"\n"+questionDTOs +"\n"+replyDTOs );

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


/* 날짜를 체크해서 설문 열림 닫힘 체크 */
//해당하는 설문조회(날짜)
    public SurveyResponse getSurveyById(int id) {
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Survey not found"));
        return SurveyResponse.of(survey);
    }


//    설문 상태 체크(시작날짜 & 종료날짜)
    public boolean isSurveyActive(int surveyId) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("Survey not found"));
        LocalDate today = LocalDate.now();
        return !today.isBefore(survey.getStartDate()) && !today.isAfter(survey.getEndDate());
    }




    // 질문 문항 응답 반환
    public void saveSurveyReply(ReplyRecordRequest replyRecordDTO) {
        Employee employee = employeeRepository.findById(replyRecordDTO.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Survey survey = surveyRepository.findById(replyRecordDTO.getSurveyId())
                .orElseThrow(() -> new RuntimeException("Survey not found"));

        SurveyQuestion question = surveyQuestionRepository.findById(replyRecordDTO.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found"));

        Job job = employee.getJob();

        ReplyRecord replyRecord = replyRecordRepository.findBySurveyAndEmployeeAndQuestion(survey, employee, question)
                .orElse(new ReplyRecord(employee, job, survey, question, replyRecordDTO.getAnswer()));


        replyRecord.updateAnswer(replyRecordDTO.getAnswer());

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
