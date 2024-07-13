package com.errorCode.pandaOffice.employee.dto.request;

import com.errorCode.pandaOffice.employee.domain.entity.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class EmployeeDTO {
    private Employee employee;
    private List<FamilyMember> familyMember;
    private List<CareerHistory> careerHistory;
    private List<EducationHistory> educationHistory;
    private List<License> licenses;
    private String photoName;
    private String photoPath;
 private Account account;

}
