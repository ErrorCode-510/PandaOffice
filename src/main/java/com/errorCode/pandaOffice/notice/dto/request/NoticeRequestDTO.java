package com.errorCode.pandaOffice.notice.dto.request;
import lombok.*;


// 공지사항 요청 DTO 클래스
@Getter
@ToString
@RequiredArgsConstructor
public class NoticeRequestDTO {

    private final String title;  // 게시글 제목
    private final String content; // 게시글 내용
    private final String category;  // 분류 (전체, 그룹, 경조사)
    private final String subCategory;  // 소분류(그룹 : 부서별 / 경조사 : 결혼, 부고, 돌잔치)
    private final int viewCount;  // 조회수
    private final char status;  // 공개여부(Y/N)


}
