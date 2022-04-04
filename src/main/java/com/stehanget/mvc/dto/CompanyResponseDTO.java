package com.stehanget.mvc.dto;

import lombok.Data;

@Data
public class CompanyResponseDTO {

    private Long id;

    private String code;

    private String name;

    private String phone;

    private String email;

    private String address;

    private String createdAt;

    private String updatedAt;
}
