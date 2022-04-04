package com.stehanget.mvc.dto;

import lombok.Data;

@Data
public class CompanyRequestDTO {

    private String code;

    private String name;

    private String phone;

    private String email;

    private String address;
}
