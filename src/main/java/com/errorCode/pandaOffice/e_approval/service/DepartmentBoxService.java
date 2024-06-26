package com.errorCode.pandaOffice.e_approval.service;

import com.errorCode.pandaOffice.auth.util.TokenUtils;
import com.errorCode.pandaOffice.e_approval.domain.entity.DepartmentBox;
import com.errorCode.pandaOffice.e_approval.domain.repository.DepartmentBoxRepository;
import com.errorCode.pandaOffice.e_approval.dto.departmentBox.request.CreateDepartmentBoxRequest;
import com.errorCode.pandaOffice.e_approval.dto.departmentBox.response.DepartmentBoxResponse;
import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.employee.domain.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentBoxService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentBoxRepository departmentBoxRepository;
    public List<DepartmentBoxResponse> getDepartmentBox() {
        int employeeId = TokenUtils.getEmployeeId();
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow();
        List<DepartmentBox> boxEntityList = departmentBoxRepository.findByDepartment(employee.getDepartment());
        List<DepartmentBoxResponse> boxResponseList = boxEntityList.stream().map(
                entity->DepartmentBoxResponse.of(entity)
        ).toList();
        return boxResponseList;
    }

    public DepartmentBoxResponse createDepartmentBox(CreateDepartmentBoxRequest request) {
        return null;
    }
}
