package com.errorCode.pandaOffice.payroll.service;

import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.employee.domain.repository.EmployeeRepository;
import com.errorCode.pandaOffice.payroll.domain.entity.*;
import com.errorCode.pandaOffice.payroll.domain.repository.*;
import com.errorCode.pandaOffice.payroll.dto.request.PayrollRequest;
import com.errorCode.pandaOffice.payroll.dto.response.DeductionCategoryResponse;
import com.errorCode.pandaOffice.payroll.dto.response.EarningCategoryResponse;
import com.errorCode.pandaOffice.payroll.dto.response.EmplPayrollResponse;
import com.errorCode.pandaOffice.recruitment.domain.entity.InterviewSchedule;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PayrollService {

    private final EmployeeRepository employeeRepository;
    private final EarningCategoryRepository earningCategoryRepository;
    private final DeductionCategoryRepository deductionCategoryRepository;
    private final PayrollRepository payrollRepository;

    /* 사원 전체 조회 */
    @Transactional(readOnly = true)
    public List<EmplPayrollResponse> getAllEmployeesPayroll() {
        List<Employee> employees = employeeRepository.findAll();

        List<EmplPayrollResponse> responses = employees.stream()
                .map(EmplPayrollResponse::from)
                .collect(Collectors.toList());

        return responses;
    }

    /* 지급항목 카테고리 */
    @Transactional(readOnly = true)
    public List<EarningCategoryResponse> getAllEarningCategory() {
        List<EarningCategory> earningCategories = earningCategoryRepository.findAll();

        List<EarningCategoryResponse> responses = earningCategories.stream()
                .map(EarningCategoryResponse::from)
                .collect(Collectors.toList());

        return responses;
    }

    /* 공제항목 카테고리 */
    @Transactional(readOnly = true)
    public List<DeductionCategoryResponse> getAllDeductionCategory() {
        List<DeductionCategory> deductionCategories = deductionCategoryRepository.findAll();

        List<DeductionCategoryResponse> responses = deductionCategories.stream()
                .map(DeductionCategoryResponse::from)
                .collect(Collectors.toList());

        return responses;
    }

    /* 사원 개별 조회(수정 해야함) */
    @Transactional(readOnly = true)
    public EmplPayrollResponse getEmplPayrollByEmployeeId(int employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + employeeId));

        return EmplPayrollResponse.from(employee);
    }


    /* 사원 급여 등록 */
    /* payrollRequest: 프론트에서 입력받은 정보가 들어있음 (누가? 얼마를? 공제금액은?
    *  엔티티 타입으로 생성자(of: new 연산자 대신 사용 가능한 간결?한 생성자)를 만들어서 payrollRequest에 있는 정보를 엔티티에 삽입
    *  사원 급여를 등록하는 데 필요한 필드(?) 사원아이디, 급여*/
    @Transactional
    public int saveEmplPay(PayrollRequest payrollRequest) {
        Objects.requireNonNull(payrollRequest, "payrollRequest must not be null");

        Employee employeeEntity = employeeRepository.findById(payrollRequest.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + payrollRequest.getEmployeeId()));

        List<EarningRecord> earningRecordEntityList = payrollRequest.getEarningRecordList().stream()
                .map(request -> {
                    EarningCategory categoryEntity = earningCategoryRepository.findById(request.getEarningCategoryId())
                            .orElseThrow(() -> new EntityNotFoundException("Earning category not found with id: " + request.getEarningCategoryId()));
                    EarningRecord recordEntity = EarningRecord.of(request, categoryEntity);
                    return recordEntity;
                }).collect(Collectors.toList());

        List<DeductionRecord> deductionRecordEntityList = payrollRequest.getDeductionRecordList().stream()
                .map(request -> {
                    DeductionCategory categoryEntity = deductionCategoryRepository.findById(request.getDeductionCategoryId())
                            .orElseThrow(() -> new EntityNotFoundException("Deduction category not found with id: " + request.getDeductionCategoryId()));
                    DeductionRecord recordEntity = DeductionRecord.of(request, categoryEntity);
                    return recordEntity;
                }).collect(Collectors.toList());

        PayrollRecord newRecord = PayrollRecord.of(payrollRequest, employeeEntity, earningRecordEntityList, deductionRecordEntityList);
        PayrollRecord savedRecord = payrollRepository.save(newRecord);

        return savedRecord.getId();
    }
}
