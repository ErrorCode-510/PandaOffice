package com.errorCode.pandaOffice.e_approval.domain.repository;

import com.errorCode.pandaOffice.e_approval.domain.entity.DocumentTemplateFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentTemplateFolderRepository extends JpaRepository<DocumentTemplateFolder, Integer> {
    List<DocumentTemplateFolder> findByRefFolderId(int folderId);
}
