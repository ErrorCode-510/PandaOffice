package com.errorCode.pandaOffice.notice.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NoticeImageRequestDTO {

    private String path;  // 이미지 파일경로
    private String name;  // 이미지 파일이름
    private String extension;  // 이미지 확장자

    public NoticeImageRequestDTO(String path, String name, String extension) {
        this.path = path;
        this.name = name;
        this.extension = extension;
    }

    @Override
    public String toString() {
        return "NoticeImageRequestDTO{" +
                "path='" + path + '\'' +
                ", name='" + name + '\'' +
                ", extension='" + extension + '\'' +
                '}';
    }
}
