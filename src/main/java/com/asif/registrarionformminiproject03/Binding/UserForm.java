package com.asif.registrarionformminiproject03.Binding;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserForm {

    private String userName;
    private String email;
    private Long phoneNumber;
    private LocalDate dob;
    private String gender;
    private Integer countryId;
    private Integer stateId;
    private Integer cityId;
}
