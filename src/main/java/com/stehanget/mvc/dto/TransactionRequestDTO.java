package com.stehanget.mvc.dto;

import com.stehanget.mvc.domain.Company;
import com.stehanget.mvc.domain.Item;
import lombok.Data;

@Data
public class TransactionRequestDTO {

    private Long companyId;

    private Long itemId;

    private int unit;

}
