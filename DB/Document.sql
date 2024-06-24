INSERT INTO approval_document_template(id, title, document, `status`, last_editor_id, edit_able_status, folder_id)
VALUES(1, '템플릿 1', "템플릿 테스트", 1, 201515001, 0, 1);
INSERT INTO approval_document (id, document_template_id, title, draft_employee_id, approval_date, last_approval_date, department_id, document, `status`)
VALUES
    (1, 1, 'Document 1', 201511001, '2023-01-01', '2023-01-05', 12, 'document1.pdf', 1),  -- 승인
    (2, 1, 'Document 2', 201512001, '2023-02-01', '2023-02-10', 13, 'document2.pdf', 2),  -- 반려
    (3, 1, 'Document 3', 201511002, '2023-03-01', '2023-03-15', 11, 'document3.pdf', 0),  -- 진행
    (4, 1, 'Document 4', 201512002, '2023-04-01', '2023-04-20', 12, 'document4.pdf', 0),  -- 진행
    (5, 1, 'Document 5', 201511004, '2023-05-01', '2023-05-25', 11, 'document5.pdf', 1);  -- 승인

INSERT INTO approval_line (document_id, employee_id, handling_date, id, `order`, `status`)
VALUES
    (1, 201514001, '2024-01-01', 1, 1, 2),
    (1, 201514002, null, 2, 1, 1),
    (1, 201515001, null, 3, 1, 0),
    (1, 201515002, null, 4, 1, 0),
    (1, 201515003, null, 5, 1, 0),
    (1, 201511005, null, 6, 1, 0),
    (1, 201512004, null, 7, 1, 0);