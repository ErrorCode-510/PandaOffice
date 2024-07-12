package com.errorCode.pandaOffice.payroll.service;

import com.errorCode.pandaOffice.employee.domain.entity.Employee;
import com.errorCode.pandaOffice.employee.domain.repository.EmployeeRepository;
import com.errorCode.pandaOffice.payroll.domain.entity.*;
import com.errorCode.pandaOffice.payroll.domain.repository.DeductionCategoryRepository;
import com.errorCode.pandaOffice.payroll.domain.repository.EarningCategoryRepository;
import com.errorCode.pandaOffice.payroll.domain.repository.PayrollRepository;
import com.errorCode.pandaOffice.payroll.dto.request.PayrollRequest;
import com.errorCode.pandaOffice.payroll.dto.response.DeductionCategoryResponse;
import com.errorCode.pandaOffice.payroll.dto.response.EarningCategoryResponse;
import com.errorCode.pandaOffice.payroll.dto.response.EmplPayrollResponse;
import com.errorCode.pandaOffice.payroll.dto.response.PayrollResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

    /* 사원 급여 조회 */
    @Transactional(readOnly = true)
    public PayrollResponse getEmployeePayroll(int employeeId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isEmpty()) {
            throw new IllegalArgumentException("Employee with ID " + employeeId + " not found");
        }

        Employee employee = employeeOptional.get();
        PayrollRecord payrollRecord = payrollRepository.findByEmployee(employee);
        if (payrollRecord == null) {
            throw new IllegalArgumentException("Payroll record not found for employee with ID " + employeeId);
        }

        return PayrollResponse.from(payrollRecord);
    }


    /* 사원 급여 등록 */
    /* payrollRequest: 프론트에서 입력받은 정보가 들어있음 (누가? 얼마를? 공제금액은?
     *  엔티티 타입으로 생성자(of: new 연산자 대신 사용 가능한 간결?한 생성자)를 만들어서 payrollRequest에 있는 정보를 엔티티에 삽입
     *  사원 급여를 등록하는 데 필요한 필드(?) 사원아이디, 급여*/
    @Transactional
    public Integer saveEmplPay(List<PayrollRequest> payrollRequests) {
        Objects.requireNonNull(payrollRequests, "payrollRequests must not be null");

        List<PayrollRecord> savedRecords = new ArrayList<>();

        for (PayrollRequest payrollRequest : payrollRequests) {
            Employee employeeEntity = employeeRepository.findById(payrollRequest.getEmployeeId())
                    .orElseThrow(() -> new EntityNotFoundException("해당 ID로 직원을 찾을 수 없습니다: " + payrollRequest.getEmployeeId()));

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

            PayrollRecord newRecord = new PayrollRecord(employeeEntity,
                    payrollRequest.getPayrollDate(),
                    payrollRequest.getPayStubPath(),
                    earningRecordEntityList,
                    deductionRecordEntityList);

            PayrollRecord savedRecord = payrollRepository.save(newRecord);
            savedRecords.add(savedRecord);
        }

        // 간단히 첫 번째 저장된 레코드의 ID를 반환하는 것으로 가정
        return savedRecords.isEmpty() ? null : savedRecords.get(0).getId();
    }
}
