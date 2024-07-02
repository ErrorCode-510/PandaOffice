package com.errorCode.pandaOffice.e_approval.presectation;

import com.errorCode.pandaOffice.e_approval.dto.departmentBox.request.CreateDepartmentBoxRequest;
import com.errorCode.pandaOffice.e_approval.dto.departmentBox.response.DepartmentBoxResponse;
import com.errorCode.pandaOffice.e_approval.service.DepartmentBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DepartmentBoxController {
    private final DepartmentBoxService departmentBoxService;
    @GetMapping("department-box/sidebar")
    public ResponseEntity<List<DepartmentBoxResponse>> getDepartmentBoxSidebar(){
        List<DepartmentBoxResponse> response = departmentBoxService.getDepartmentBox();
        return ResponseEntity.ok(response);
    }
    @PostMapping("department-box")
    public ResponseEntity<DepartmentBoxResponse> createDepartmentBox(@RequestBody CreateDepartmentBoxRequest request){
        DepartmentBoxResponse response = departmentBoxService.createDepartmentBox(request);

        return null;
    }
}
