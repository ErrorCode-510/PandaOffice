package com.errorCode.pandaOffice.employee.service;
import com.errorCode.pandaOffice.employee.domain.entity.*;
import com.errorCode.pandaOffice.employee.domain.repository.*;
import com.errorCode.pandaOffice.employee.dto.response.OrganizationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final JobRepository jobRepository;
    private final HobbyRepository hobbyRepository;
    private final OrganizationEmployeeImageRepository organizationEmployeeImageRepository;

    // 모든 사원 조회
    public List<OrganizationResponseDTO> getAllEmployee() {
        return employeeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 특정 부서에 속한 사원 조회
    public List<OrganizationResponseDTO> getEmployeeByDepartment(int departmentId) {
        return employeeRepository.findByDepartmentId(departmentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 특정 직급에 속한 사원 조회
    public List<OrganizationResponseDTO> getEmployeeByJob(int jobId) {
        return employeeRepository.findByJobId(jobId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // [검색기능] 이름을 기준으로 사원을 검색
    public List<OrganizationResponseDTO> searchEmployee(String name) {
        return employeeRepository.findByNameContaining(name).stream()
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

    // 특정 사원의 이메일, 취미, 자기소개를 업데이트
    public void updateEmployee(int employeeId, OrganizationResponseDTO requestDTO) {

        // 필요한 필드만 업데이트
        if (requestDTO.getEmail() != null) {
            employeeRepository.updateEmail(employeeId, requestDTO.getEmail());
        }
        if (requestDTO.getSelfIntroduction() != null) {
            employeeRepository.updateSelfIntroduction(employeeId, requestDTO.getSelfIntroduction());
        }
        if (requestDTO.getHobby() != null && !requestDTO.getHobby().isEmpty()) {
            // 기존 취미 삭제
            hobbyRepository.deleteByEmployee(employeeId);
            // 새로운 취미 추가
            Employee employee = employeeRepository.findById(employeeId).orElseThrow();
            requestDTO.getHobby().forEach(h -> hobbyRepository.save(new Hobby(0, employee, h)));
        }
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
        Optional<EmployeePhoto> employeePhotos = organizationEmployeeImageRepository.findByEmployee_EmployeeId(employee.getEmployeeId());
        dto.setEmployeeImage(employeePhotos.isPresent() ? employeePhotos.get().getPath() : "N/A");

        // Hobby 조회
        List<Hobby> hobbies = hobbyRepository.findByEmployee(employee.getEmployeeId());
        dto.setHobby(hobbies.isEmpty() ? List.of("N/A") : hobbies.stream().map(Hobby::getHobby).collect(Collectors.toList()));

        return dto;
    }

    // 생년월일을 기반으로 나이를 계산
    private int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        return Period.between(birthDate, currentDate).getYears();
    }
}
