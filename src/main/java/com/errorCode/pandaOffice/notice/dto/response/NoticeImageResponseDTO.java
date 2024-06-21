package com.errorCode.pandaOffice.notice.dto.response;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class NoticeImageResponseDTO {

    private int imageId;  // 이미지 코드(PK)
    private String path;  // 이미지 파일경로
    private String name;  // 이미지 파일이름
    private String extention;  // 이미지 확장자
    private String uploadUrl;  // 추가된 메타데이터 필드

    public NoticeImageResponseDTO(int imageId, String path, String name, String extention, String uploadUrl) {
        this.imageId = imageId;
        this.path = path;
        this.name = name;
        this.extention = extention;
        this.uploadUrl = uploadUrl;
    }
}
