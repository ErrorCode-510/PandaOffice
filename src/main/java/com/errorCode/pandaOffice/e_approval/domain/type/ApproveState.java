package com.errorCode.pandaOffice.e_approval.domain.type;

public enum ApproveState {
    APPROVE("결재 완료"),
    PROGRESS("진행중"),
    REJECTION("반려");

    private String state;

    private ApproveState(String state) {
        this.state = state;
    }

    public String getState() {return state;}
}
