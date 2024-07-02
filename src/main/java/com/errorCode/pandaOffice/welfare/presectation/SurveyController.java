package com.errorCode.pandaOffice.welfare.presectation;

import com.errorCode.pandaOffice.employee.domain.repository.EmployeeRepository;
import com.errorCode.pandaOffice.welfare.domain.repository.ReplyRecordRepository;
import com.errorCode.pandaOffice.welfare.domain.repository.SurveyRepository;
import com.errorCode.pandaOffice.welfare.dto.request.CreateSurveyRequest;
import com.errorCode.pandaOffice.welfare.dto.request.ReplyRecordRequest;
import com.errorCode.pandaOffice.welfare.dto.request.UpdateSurveyQuestionRequest;
import com.errorCode.pandaOffice.welfare.dto.response.SurveyQuestionDTO;
import com.errorCode.pandaOffice.welfare.dto.response.SurveyResponse;
import com.errorCode.pandaOffice.welfare.service.SurveyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
//@RequestMapping("/survey")
public class SurveyController {
    private final SurveyService surveyService;
    private final SurveyRepository surveyRepository;
    private final ReplyRecordRepository replyRecordRepository;
    private final EmployeeRepository employeeRepository;

    /* Entity내의 참조, Entity 연관관계, cascade시 동시 저장 되는것 주의*/
    @GetMapping("/survey")
    public ResponseEntity<List<SurveyResponse>> getSurveyList() {
        List<SurveyResponse> response = surveyService.getAllSurvey();
        return ResponseEntity.ok(response);
    }

    /*설문 ID에 해당하는 설문이 현재 활성 상태인지 확인하고, 활성 상태인 경우 설문 데이터를 반환.
    비활성 상태인 경우에는 접근을 금지*/
    @GetMapping("/survey/{id}")
    public ResponseEntity<SurveyResponse> getSurvey(@PathVariable int id) {
        if (!surveyService.isSurveyActive(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        SurveyResponse response = surveyService.getSurveyById(id);
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


    // 설문 응답을 저장하고, 저장된 응답 데이터를 반환합니다.
    @PostMapping("/survey/reply-count")
    public ResponseEntity<Void> saveReplyRecord(@RequestBody ReplyRecordRequest replyRecordDTO) {
        surveyService.saveSurveyReply(replyRecordDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }




}
