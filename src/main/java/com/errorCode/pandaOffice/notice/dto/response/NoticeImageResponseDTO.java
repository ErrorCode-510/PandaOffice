package com.errorCode.pandaOffice.notice.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NoticeImageResponseDTO {

    private int id;  // 이미지 코드(PK)
    private String path;  // 이미지 파일경로
    private String name;  // 이미지 파일이름
    private String extension;  // 이미지 확장자

    public NoticeImageResponseDTO(int id, String path, String name, String extension) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.extension = extension;
    }

    @Override
    public String toString() {
        return "NoticeImageResponseDTO{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", name='" + name + '\'' +
                ", extension='" + extension + '\'' +
                '}';
    }
}
