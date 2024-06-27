package com.errorCode.pandaOffice.employee.service;

import com.errorCode.pandaOffice.employee.domain.entity.*;
import com.errorCode.pandaOffice.employee.domain.repository.*;
import com.errorCode.pandaOffice.employee.dto.response.OrganizationResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganizationService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private HobbyRepository hobbyRepository;
    @Autowired
    private OrganizationEmployeeImageRepository organizationEmployeeImageRepository;


    // 모든 사원 조회
    public List<OrganizationResponseDTO> getAllEmployee() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 특정 부서에 속한 사원 조회
    public List<OrganizationResponseDTO> getEmployeeByDepartment(int departmentId) {
        List<Employee> employees = employeeRepository.findByDepartmentId(departmentId);
        return employees.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 특정 직급에 속한 사원 조회
    public List<OrganizationResponseDTO> getEmployeeByJob(int jobId) {
        List<Employee> employees = employeeRepository.findByJobId(jobId);
        return employees.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // [검색기능] 이름을 기준으로 사원을 검색
    public List<OrganizationResponseDTO> searchEmployee(String name) {
        List<Employee> employees = employeeRepository.findByNameContaining(name);
        return employees.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 모든 부서를 조회
    public List<Department> getAllDepartment() {
        return departmentRepository.findAll();
    }

    // 모든 직급을 조회
    public List<Job> getAllJob() {
        return jobRepository.findAll();
    }

    // 특정 사원의 ID를 기반으로 취미를 조회
    public List<Hobby> getHobbyByEmployee(int employeeId) {
        return hobbyRepository.findByEmployee(employeeId);
    }

    // Employee 엔티티를 OrganizationResponseDTO 로 변환
    private OrganizationResponseDTO convertToDTO(Employee employee) {
        OrganizationResponseDTO dto = new OrganizationResponseDTO();
        dto.setEmployeeName(employee.getName());
        dto.setJobTitle(employee.getJob().getTitle());
        dto.setDepartmentName(employee.getDepartment().getName());
        dto.setBirthDate(employee.getBirthDate());
        dto.setAge(calculateAge(employee.getBirthDate(), LocalDate.now()));
        dto.setGender(employee.getGender());
        dto.setEmail(employee.getEmail());
        dto.setSelfIntroduction(employee.getSelfIntroduction());


        // EmployeePhoto 조회
        List<EmployeePhoto> employeePhotos = organizationEmployeeImageRepository.findByEmployee(employee.getEmployeeId());
        if (!employeePhotos.isEmpty()) {
            dto.setEmployeeImage(employeePhotos.get(0).getPath());
        } else {
            dto.setEmployeeImage("N/A");
        }

        // Hobby 조회
        List<Hobby> hobbies = hobbyRepository.findByEmployee(employee.getEmployeeId());
        if (hobbies != null && !hobbies.isEmpty()) {
            dto.setHobby(hobbies.stream().map(Hobby::getHobby).collect(Collectors.joining(", ")));
        } else {
            dto.setHobby("N/A");
        }
        return dto;
    }

    // 생년월일을 기반으로 나이를 계산
    private int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        return Period.between(birthDate, currentDate).getYears();
    }
}
