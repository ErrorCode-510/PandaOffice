package com.errorCode.pandaOffice.e_approval.domain.type.converter;

import com.errorCode.pandaOffice.e_approval.domain.type.ApprovalStatus;
import com.errorCode.pandaOffice.e_approval.domain.type.ApproveType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ApprovalTypeConverter implements AttributeConverter<ApproveType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ApproveType type) {
        if (type == null) {
            return null;
        }
        return type.getValue();
    }

    @Override
    public ApproveType convertToEntityAttribute(Integer value) {
        if (value == null) {
            return null;
        }
        return ApproveType.fromValue(value);
    }
}
