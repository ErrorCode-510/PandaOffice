package com.errorCode.pandaOffice.notice.domain.entity;

import jakarta.el.ArrayELResolver;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

// 공지사항(게시글) 이미지
@Entity
@Table(name = "notice_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class NoticeImage {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  // 게시글 이미지 코드(PK)

    @Column(name = "path")
    private String path;  // 게시글 이미지 파일경로

    @Column(name = "name")
    private String name;  // 게시글 이미지 파일이름

    @Column(name = "extention")
    private String extention;  // 게시글 이미지 확장자

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "notice_id")
    private Notice notice;  // 게시글 코드(FK)

}
