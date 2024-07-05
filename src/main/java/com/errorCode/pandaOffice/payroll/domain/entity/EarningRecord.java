package com.errorCode.pandaOffice.payroll.domain.entity;

import com.errorCode.pandaOffice.payroll.dto.request.PayrollRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/* 지급 기록 Entity */
@Entity
@Table(name = "earning_record")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class EarningRecord {


    /* 지급 코드 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int earningId;

    /* 지급 항목 코드 */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "earning_category_id")
    private EarningCategory earningCategory;

    /* 지급 금액 */
    @Column(name = "amount")
    private int amount;

    public static EarningRecord of(PayrollRequest.EarningRequest request, EarningCategory categoryEntity) {
        EarningRecord newRecord = new EarningRecord();
        newRecord.earningCategory = categoryEntity;
        newRecord.amount = request.getAmount();
        return newRecord;
    }


    /*
     * 들어오는 JSON
     * {
     *   {사번: 123142,
     *   지급: [{지급 항목:1, 금액:인풋값}, {지급 항목:2, 금액:인풋값]
     *   공제: [{공제 항목:1, 금액:인풋값}, {공제 항목:2, 금액:인풋값]
     * }
     *
     * {
     *   empId: 123142
     *   List<지급기록>: [{지급 항목:1, 금액:인풋값}, {지급 항목:2, 금액:인풋값]
     *   List<공제기록>: [{공제 항목:1, 금액:인풋값}, {공제 항목:2, 금액:인풋값]
     * }
     * */
}
