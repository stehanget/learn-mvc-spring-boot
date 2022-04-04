package com.stehanget.mvc.dto;

import lombok.Data;

@Data
public class ItemResponseDTO {

    private Long id;

    private String name;

    private Long price;

    private int stock;

    private String description;

    private String createdAt;

    private String updatedAt;
}
