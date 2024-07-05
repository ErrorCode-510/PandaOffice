package com.errorCode.pandaOffice.employee.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;



    @Getter
    @Setter
    public class FindIdRequest {
        private String name;
        private String email;
        private LocalDate birthDate;
    }
