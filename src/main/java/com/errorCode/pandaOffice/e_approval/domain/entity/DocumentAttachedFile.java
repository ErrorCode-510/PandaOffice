package com.errorCode.pandaOffice.e_approval.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "document_attached_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
/* 문서 첨부 파일 */
public class DocumentAttachedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /* 파일명 */
    private String name;
    /* 파일 경로 */
    private String path;
    /* 파일 타입 */
    private String type;
    /* 문서 ID */
    private int documentId;
}
