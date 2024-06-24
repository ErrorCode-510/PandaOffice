package com.errorCode.pandaOffice.e_approval.domain.type;

import lombok.Getter;

@Getter
public enum ApproveType {
    SCHEDULED(0, "예정"),
    PENDING(1, "대기"),
    APPROVE(2, "승인"),
    DELEGATED_APPROVE(3, "승인(代)"),
    PRE_APPROVE(4, "선결"),
    ALL_APPROVE(5, "전결"),
    DENY(6, "반려"),
    DENY_AFTER(7, ""),
    CONFIRM_ONLY(8, "후열");

    private final int value;
    private final String description;

    ApproveType(int value, String description){
        this.value = value;
        this.description = description;
    }

    public static ApproveType fromValue(int value){
        for(ApproveType type : ApproveType.values()){
            if(type.getValue() == value){
                return type;
            }
        }
        throw new IllegalArgumentException("찾을 수 없는 값: " + value);
    }
}
