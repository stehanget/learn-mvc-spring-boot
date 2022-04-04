package com.stehanget.mvc.dto;

import lombok.Data;

@Data
public class ItemRequestDTO {

    private String name;

    private Long price;

    private int stock;

    private String description;
}
