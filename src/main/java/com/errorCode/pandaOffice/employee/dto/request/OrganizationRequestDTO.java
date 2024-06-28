package com.errorCode.pandaOffice.employee.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrganizationRequestDTO {

    private String name;
    private String selfIntroduction;
    private List<String> hobby;
}
