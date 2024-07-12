package com.errorCode.pandaOffice.e_approval.domain.type;

import lombok.Getter;

@Getter
public enum ApprovalStatus {
    IN_PROGRESS(0, "진행중"),
    APPROVE(1, "승인"),
    DENY(2, "반려");
    private final int value;
    private final String description;

    ApprovalStatus(int value, String description) {
        this.value = value;
        this.description = description;
    }

    /* 클래스 생성 없이 사용할 수 있도록 static 으로 선언하는 유틸리티 메소드 */
    public static ApprovalStatus fromValue(int value) {
        /* ENUM 전체 반복 */
        for (ApprovalStatus status : ApprovalStatus.values()) {
            /* 받은 value 와 getValue 가 같을 경우 */
            if (status.getValue() == value) {
                /* 그 value 를 반환 */
                return status;
            }
        }
        /* 값이 return 되지 않을 경우 익셉션 발생 */
        throw new IllegalArgumentException("찾을 수 없는 값: " + value);
    }
    public static ApprovalStatus fromDescription(String  description) {
        for (ApprovalStatus status : ApprovalStatus.values()) {
            if (status.getDescription() == description) {
                return status;
            }
        }
        /* 값이 return 되지 않을 경우 익셉션 발생 */
        throw new IllegalArgumentException("찾을 수 없는 값: " + description);
    }
}
