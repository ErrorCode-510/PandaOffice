package com.errorCode.pandaOffice.notice.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

// 공지사항(게시글) 이미지
@Entity
@Table(name = "notice_image")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class NoticeImage {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imageId;  // 게시글 이미지 코드(PK)

    @Column(name = "path", nullable = false)
    private String path;  // 게시글 이미지 파일경로

    @Column(name = "name", nullable = false)
    private String name;  // 게시글 이미지 파일이름

    @Column(name = "extension", nullable = false)
    private String extension;  // 게시글 이미지 확장자

    public static NoticeImage of(String name, String path, String contentType) {
        NoticeImage noticeImage = new NoticeImage();
        noticeImage.path = path;
        noticeImage.name = name;
        noticeImage.extension = contentType;
        return noticeImage;
    }
}
