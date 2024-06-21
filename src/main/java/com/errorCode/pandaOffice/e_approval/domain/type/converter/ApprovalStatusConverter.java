package com.errorCode.pandaOffice.e_approval.domain.type.converter;

import com.errorCode.pandaOffice.e_approval.domain.type.ApprovalStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/* DB 와 ENUM 사이 컨버터 */
@Converter(autoApply = true)
public class ApprovalStatusConverter implements AttributeConverter<ApprovalStatus, Integer> {
    /* DB 에 저장될 때 실행되는 메소드 */
    @Override
    public Integer convertToDatabaseColumn(ApprovalStatus status) {
        if (status == null) {
            return null;
        }
        return status.getValue();
    }

    /* DB 값을 가져와서 ENUM 을 순회하여 비교한 뒤 나온 결과값 */
    @Override
    public ApprovalStatus convertToEntityAttribute(Integer value) {
        if (value == null) {
            return null;
        }
        return ApprovalStatus.fromValue(value);
    }
}
