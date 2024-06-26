package com.errorCode.pandaOffice.attendance.dto.AnnualLeaveRecord.response;

import com.errorCode.pandaOffice.attendance.domain.entity.AnnualLeaveRecord;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AnnualLeaveRecordResponse {

    /* 2.내 연차 내역(Annual Leave Record)의 기능들 */

    /* 1.클래스 안에 클래스를 명시해준다.
    * 아래에 적힌 클래스 명들은 피그마 한 페이지 안에 들어가는 API 하나를 의미한다. */
    private CalculateLeaveRecord calculateLeaveRecord;

    private List<UsedLeaveRecord> usedLeave;

    private List<CreatedRecord> createdRecord;


    /* 2.AnnualLeaveRecordResponse 타입의 of 메소드를 만들어 준다.
     * 1.이 메소드는 service 클래스에서 쓸 것이다.
     * 2.이 메소드는 AnnualLeaveRecord를 리스트로 형태로 받아들이고
     * 그걸 변환 해준뒤 response 형태로 내보내는 것이다. */
    public static AnnualLeaveRecordResponse of(List<AnnualLeaveRecord> recordList) {

        AnnualLeaveRecordResponse response = new AnnualLeaveRecordResponse();

        response.usedLeave = recordList.stream()
                .filter(record->record
                        .getAnnualLeaveCategory()   //카테고리 가져오기
                        .getType()                  // 타입명 가져오기
                        .equals("소진"))              // 비교 결과값 (필터 완료)
                .map(
                        /* 한번 더 of 메소드를 작성해서 내보낼 값을 설정해준다. */
                        recordEntity -> UsedLeaveRecord.of(recordEntity)
                )
                .toList();

        response.createdRecord = recordList.stream()
                .filter(record -> record
                        .getAnnualLeaveCategory()
                        .getType()
                        .equals("부여"))
                .map(
                        recordEntity -> CreatedRecord.of(recordEntity)
                )
                .toList();

        response.calculateLeaveRecord = CalculateLeaveRecord.of(recordList);

        return response;
    }

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 연차 계산 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */


    @Getter
    @RequiredArgsConstructor
    // 연차를 계산하기 위한 클래스를 따로 만들어 준다.
    public static class CalculateLeaveRecord{
        private double generatedLeave;
        private double adjustedLeave;
        private double totalLeave;
        private double usedLeave;
        private double remainingLeave;

        public static CalculateLeaveRecord of(List<AnnualLeaveRecord> recordList) {

            // 계산된 연차 내역을 위한 변수명들을 선언해준다.
            double generatedLeave = 0.0;
            double adjustedLeave = 0.0;
            double totalLeave = 0.0;
            double usedLeave = 0.0;

            for (AnnualLeaveRecord record : recordList) {

                String type = record.getAnnualLeaveCategory().getType();
                String name = record.getAnnualLeaveCategory().getName();

                double amount = record.getAmount();

                if (name.equals("기본 발생")) {
                    generatedLeave += amount;
                }

                switch (type) {
                    case "부여":
                        generatedLeave += amount;
                        adjustedLeave += amount;
                        totalLeave += amount;
                        break;
                    case "소진":
                        adjustedLeave -= amount;
                        usedLeave += amount;
                        break;
                    default:
                        // 기타 다른 타입이 있다면 무시
                        break;
                }
            }

            double remainingLeave = generatedLeave + adjustedLeave - usedLeave;

            CalculateLeaveRecord response = new CalculateLeaveRecord();
            response.generatedLeave = generatedLeave;
            response.adjustedLeave = adjustedLeave;
            response.totalLeave = totalLeave;
            response.usedLeave = usedLeave;
            response.remainingLeave = remainingLeave;

            return response;
        }
    }

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 연차 사용 내역 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */

    @Getter
    @RequiredArgsConstructor
    // 연차 사용 내역을 파악하기 위한 클래스를 따로 만들어준다.
    public static class UsedLeaveRecord{

        /* UsedLeaveRecord 에서 내보낼 필드명들을 적어준다. */
        private String employeeName;
        private String departmentName;
        private String categoryName;
        private LocalDate startDate;
        private LocalDate endDate;
        private double amount;


        public static UsedLeaveRecord of(AnnualLeaveRecord recordEntity) {

            UsedLeaveRecord response = new UsedLeaveRecord();

            response.employeeName = recordEntity.getEmployee().getName();
            response.departmentName = recordEntity.getEmployee().getDepartment().getName();
            response.categoryName = recordEntity.getAnnualLeaveCategory().getName();
            response.startDate = recordEntity.getDate();
            response.endDate = recordEntity.getDate().plusDays(
                    (long)Math.floor(recordEntity.getAmount())
            );
            response.amount = recordEntity.getAmount();
            return response;
        }
    }

    /* ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 연차 생성 내역 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
    @Getter
    @RequiredArgsConstructor
    public static class CreatedRecord{

        private LocalDate registDate;
        private LocalDate validityDate;
        private String content;
        private double amount;

        public static CreatedRecord of(AnnualLeaveRecord recordEntity) {

            CreatedRecord response = new CreatedRecord();

            response.registDate = recordEntity.getDate();
            response.validityDate = recordEntity.getDate().plusYears(1);
            response.content = recordEntity.getContent();
            response.amount = recordEntity.getAmount();

            return response;
        }
    }
}

//
//    /* 연차 기록 코드 */
//    private int id;
//
//    /* 연차 기록 변동일 */
//    private LocalDate date;
//
//    /* 잔여 연차 */
//    private double nowAmount;
//
//    /* 수량 (연차를 얼마나 썼는지, 받았는지에 대한 수량임) */
//    private double amount;
//
//    /* 연차에 대한 내용 */
//    private String content;
//
//    /* 발생 연차 */
//    private double generatedLeave;
//
//    /* 조정 연차 */
//    private double adjustedLeave;
//
//    /* 총 연차 */
//    private double totalLeave;
//
//    /* 사용 연차 */
//    private double usedLeave;
//
//    /* 잔여 연차 */
//    private double remainingLeave;
//
//    public AnnualLeaveRecordResponse(int id, LocalDate date, double nowAmount, double amount) {
//        this.id = id;
//        this.date = date;
//        this.nowAmount = nowAmount;
//        this.amount = amount;
//    }
//
//    public AnnualLeaveRecordResponse(double generatedLeave, double adjustedLeave, double totalLeave, double usedLeave, double remainingLeave) {
//        this.generatedLeave = generatedLeave;
//        this.adjustedLeave = adjustedLeave;
//        this.totalLeave = totalLeave;
//        this.usedLeave = usedLeave;
//        this.remainingLeave = remainingLeave;
//    }
