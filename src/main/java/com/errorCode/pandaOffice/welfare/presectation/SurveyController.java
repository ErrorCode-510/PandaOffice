package com.errorCode.pandaOffice.welfare.presectation;

import com.errorCode.pandaOffice.employee.domain.repository.EmployeeRepository;
import com.errorCode.pandaOffice.welfare.domain.entity.ReplyRecord;
import com.errorCode.pandaOffice.welfare.domain.entity.Survey;
import com.errorCode.pandaOffice.welfare.domain.entity.SurveyCategory;
import com.errorCode.pandaOffice.welfare.domain.entity.SurveyQuestion;
import com.errorCode.pandaOffice.welfare.domain.repository.ReplyRecordRepository;
import com.errorCode.pandaOffice.welfare.domain.repository.SurveyRepository;
import com.errorCode.pandaOffice.welfare.dto.request.CreateSurveyRequest;
import com.errorCode.pandaOffice.welfare.dto.request.ReplyRecordRequest;
import com.errorCode.pandaOffice.welfare.dto.request.UpdateSurveyQuestionRequest;
import com.errorCode.pandaOffice.welfare.dto.response.*;
import com.errorCode.pandaOffice.welfare.service.SurveyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
//@RequestMapping("/survey")
public class SurveyController {
    private final SurveyService surveyService;
    private final SurveyRepository surveyRepository;
    private final ReplyRecordRepository replyRecordRepository;
    private final EmployeeRepository employeeRepository;

//    모든 설문 카테고리를 가져오는 API 엔드포인트
        @GetMapping("/survey/categories")
        public List<SurveyCategoryDTO> getAllCategories() {
            return surveyService.getAllCategories();
        }

    /* Entity내의 참조, Entity 연관관계, cascade시 동시 저장 되는것 주의*/
    @GetMapping("/survey")
    public ResponseEntity<List<SurveyResponse>> getSurveyList() {
        List<SurveyResponse> response = surveyService.getAllSurvey();
        return ResponseEntity.ok(response);
    }



    /* 설문을 등록하는 API */
    @PostMapping("/survey")
    /* (DTO(request)로 정보 받기) */
    public ResponseEntity<Void> createSurvey(@RequestBody CreateSurveyRequest request) {
        /* 컨트롤러에서 서비스에 DTO(request) 같이 보내서 DB 작업 위임 */
        int surveyId = surveyService.createSurvey(request);
        System.out.println("새로 생성된 설문의 아이디 : " + surveyId);
        /*
         * 반환값 BODY 는 null 이지만
         * 헤더에 만들어진 객체와 관련된 정보를 담는 메소드
         * */
        return ResponseEntity.created(URI.create("/survey/" + surveyId)).build();
    }

    //    응답조 조회
    @GetMapping("/survey/respondent-count/{surveyId}")
    public ResponseEntity<Integer> getSurveyRespondentCount(@PathVariable int surveyId) {
        int respondentCount = surveyService.getSurveyRespondentCount(surveyId);
        return ResponseEntity.ok(respondentCount);
    }


    //질문 수정 API
    @PutMapping("/survey/questions/{id}")
    public ResponseEntity<SurveyQuestionDTO> updateSurveyQuestion(@PathVariable int id, @RequestBody UpdateSurveyQuestionRequest request) {
        System.out.println("Request ID: " + id);
        System.out.println("Update Request: " + request.getSurveyId());
        try {
            // 서비스 계층에 요청을 전달하여 SurveyQuestion을 업데이트하고, DTO로 반환
            SurveyQuestionDTO updatedQuestion = surveyService.updateSurveyQuestion(id, request);
            // 성공적으로 업데이트된 경우 HTTP 200 응답과 함께 업데이트된 SurveyQuestionDTO를 반환
            return ResponseEntity.ok(updatedQuestion);
        } catch (RuntimeException e) {
            // SurveyQuestion이나 Survey를 찾을 수 없는 경우 HTTP 404 응답을 반환
            return ResponseEntity.notFound().build();
        }
    }

    //    질문 삭제 API
    @DeleteMapping("/survey/questions/{id}")
    public ResponseEntity<Void> deleteSurveyQuestion(@PathVariable int id) {
        try {
            surveyService.deleteSurveyQuestion(id);
            return ResponseEntity.noContent().build(); // HTTP 204 No Content 응답
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // HTTP 404 Not Found 응답
        }
    }


    //대시보드에서 사용(열려있는 설문 가져오는 용도)
    @GetMapping("/survey/active")
    public ResponseEntity<SurveyResponse> getActiveSurvey() {
        try {
            SurveyResponse response = surveyService.getActiveSurvey();
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }
    @GetMapping("/survey/most-recent-ended")
    public ResponseEntity<SurveyResponse> getMostRecentEndedSurvey() {
        try {
            Survey survey = surveyService.getMostRecentEndedSurvey();
            SurveyResponse response = SurveyResponse.of(survey);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }



    //설문조회(질문, 문항 포함 차트 뿌려주기용)
    @GetMapping("/survey/survey-details/{id}")
    public ResponseEntity<SurveyDetailsResponse> getSurveyDetails(@PathVariable int id) {
        SurveyDetailsResponse response = surveyService.getSurveyDetails(id);
        return ResponseEntity.ok(response);
    }


    // 질문 문항 기록 저장
    @PostMapping("/survey/reply-count")
    public ResponseEntity<Void> saveReplyRecord(@RequestBody ReplyRecordRequest replyRecordDTO) {
        try {
            surveyService.saveSurveyReply(replyRecordDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (RuntimeException e) {
            // 예외 발생 시 HTTP 상태 코드와 함께 빈 응답 반환
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/question/{id}")
    public ResponseEntity<SurveyQuestionDTO> getSurveyQuestionById(@PathVariable int id) {
        SurveyQuestionDTO question = surveyService.getSurveyQuestionById(id);
        return ResponseEntity.ok(question);
    }


//카테고리 ID로 설문 조회
@GetMapping("/category/{categoryId}")
public ResponseEntity<List<SurveyResponse>> getSurveysByCategoryId(@PathVariable int categoryId) {
    System.out.println("Controller: getSurveysByCategoryId 호출됨");
    List<SurveyResponse> surveys = surveyService.getSurveysByCategoryId(categoryId);
    if (surveys.isEmpty()) {
        System.out.println("Controller: No surveys found for category ID " + categoryId);
        return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(surveys);
}

}
