package com.errorCode.pandaOffice.payroll.presectation;

import com.errorCode.pandaOffice.payroll.dto.request.PayrollRequest;
import com.errorCode.pandaOffice.payroll.dto.response.DeductionCategoryResponse;
import com.errorCode.pandaOffice.payroll.dto.response.EarningCategoryResponse;
import com.errorCode.pandaOffice.payroll.dto.response.EmplPayrollResponse;
import com.errorCode.pandaOffice.payroll.service.PayrollService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/payroll")
@RequiredArgsConstructor
public class PayrollController {

    private final PayrollService payrollService;

    /* 전체조회 */
    @GetMapping("/all-emplpayroll")
    public ResponseEntity<List<EmplPayrollResponse>> getAllEmployeesPayroll() {
        List<EmplPayrollResponse> employees = payrollService.getAllEmployeesPayroll();
        return ResponseEntity.ok(employees);
    }

    /* 지급 항목 조회 */
    @GetMapping("/earning-category")
    public ResponseEntity<List<EarningCategoryResponse>> getAllEarningCategory() {
        List<EarningCategoryResponse> earningCategories = payrollService.getAllEarningCategory();
        return ResponseEntity.ok(earningCategories);
    }

    /* 공제 항목 조회 */
    @GetMapping("/deduction-category")
    public ResponseEntity<List<DeductionCategoryResponse>> getAllDeductionCategory() {
        List<DeductionCategoryResponse> deductionCategories = payrollService.getAllDeductionCategory();
        return ResponseEntity.ok(deductionCategories);
    }

    /* 개인조회 */
    @GetMapping("/mypay/{employeeId}")
    public ResponseEntity<EmplPayrollResponse> getEmplPayrollByEmployeeId(@PathVariable int employeeId) {
        EmplPayrollResponse response = payrollService.getEmplPayrollByEmployeeId(employeeId);
        return ResponseEntity.ok(response);
    }

    /* 사원 급여 등록 */
    @PostMapping("/save-emplpay")
    public ResponseEntity<Void> save(@RequestBody @Valid final PayrollRequest payrollRequest) {
        Integer id = payrollService.saveEmplPay(payrollRequest); // Use Integer instead of int

        if (id != null) {
            URI location = URI.create("/payroll/allemplpayroll/" + id);
            return ResponseEntity.created(location).build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
