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

    public static DocumentAttachedFile from(String name, String path, String type) {
        DocumentAttachedFile documentAttachedFile = new DocumentAttachedFile();
        documentAttachedFile.name = name;
        documentAttachedFile.path = path;
        documentAttachedFile.type = type;
        return documentAttachedFile;
    }
}
