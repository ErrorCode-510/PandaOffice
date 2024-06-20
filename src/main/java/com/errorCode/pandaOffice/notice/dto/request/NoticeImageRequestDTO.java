package com.errorCode.pandaOffice.notice.dto.request;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoticeImageRequestDTO {

    private int noticeId;  // 게시글 코드
    private String path;  // 이미지 파일경로
    private String name;  // 이미지 파일이름
    private String extention;  // 이미지 확장자

}
