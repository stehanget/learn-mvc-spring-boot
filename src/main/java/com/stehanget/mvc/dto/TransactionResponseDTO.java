package com.stehanget.mvc.dto;

import com.stehanget.mvc.domain.Company;
import com.stehanget.mvc.domain.Item;
import lombok.Data;

@Data
public class TransactionResponseDTO {

    private Long id;

    private Long companyId;

    private Long itemId;

    private String company;

    private String item;

    private Long price;

    private int unit;

    private Long total;

    private int remainingStock;

    private String createdAt;

    private String updatedAt;
}
