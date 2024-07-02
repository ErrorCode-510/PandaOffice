package com.errorCode.pandaOffice.employee.presentation;

import com.errorCode.pandaOffice.employee.domain.entity.Department;
import com.errorCode.pandaOffice.employee.domain.entity.Hobby;
import com.errorCode.pandaOffice.employee.domain.entity.Job;
import com.errorCode.pandaOffice.employee.dto.response.OrganizationResponseDTO;
import com.errorCode.pandaOffice.employee.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/org")
@RequiredArgsConstructor
public class OrganizationController {

    private  final OrganizationService organizationService;

    // 모든 사원 조회
    @GetMapping("/employee")
    public ResponseEntity<List<OrganizationResponseDTO>> getAllEmployee() {
        List<OrganizationResponseDTO> employees = organizationService.getAllEmployee();
        return ResponseEntity.ok(employees);
    }

    // 특정 부서에 속한 사원 조회
    @GetMapping("/employee/department/{departmentId}")
    public ResponseEntity<List<OrganizationResponseDTO>> getEmployeeByDepartment(@PathVariable int departmentId) {
        List<OrganizationResponseDTO> employees = organizationService.getEmployeeByDepartment(departmentId);
        return ResponseEntity.ok(employees);
    }

    // 특정 직급에 속한 사원 조회
    @GetMapping("/employee/job/{jobId}")
    public ResponseEntity<List<OrganizationResponseDTO>> getEmployeeByJob(@PathVariable int jobId) {
        List<OrganizationResponseDTO> employees = organizationService.getEmployeeByJob(jobId);
        return ResponseEntity.ok(employees);
    }

    // [검색기능] 이름을 기반으로 사원을 검색
    @GetMapping("/employee/search")
    public ResponseEntity<List<OrganizationResponseDTO>> searchEmployee(@RequestParam String name) {
        List<OrganizationResponseDTO> employees = organizationService.searchEmployee(name);
        return ResponseEntity.ok(employees);
    }

    // 모든 부서 조회
    @GetMapping("/department")
    public ResponseEntity<List<Department>> getAllDepartment() {
        List<Department> departments = organizationService.getAllDepartment();
        return ResponseEntity.ok(departments);
    }

    // 모든 직급 조회
    @GetMapping("/job")
    public ResponseEntity<List<Job>> getAllJob() {
        List<Job> jobs = organizationService.getAllJob();
        return ResponseEntity.ok(jobs);
    }

    // 특정 사원의 ID를 기반으로 취미를 조회
    @GetMapping("/employee/{employeeId}/hobby")
    public ResponseEntity<List<Hobby>> getHobbyByEmployee(@PathVariable int employeeId) {
        List<Hobby> hobbies = organizationService.getHobbyByEmployee(employeeId);
        return ResponseEntity.ok(hobbies);
    }

    // 사원 정보 수정
    @PutMapping("/employee/{employeeId}")
    public ResponseEntity<Void> updateEmployee(@PathVariable int employeeId, @RequestBody OrganizationResponseDTO requestDTO) {
        organizationService.updateEmployee(employeeId, requestDTO);
        return ResponseEntity.noContent().build();
    }
}
