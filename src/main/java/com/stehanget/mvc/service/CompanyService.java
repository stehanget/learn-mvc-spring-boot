package com.stehanget.mvc.service;

import com.stehanget.mvc.domain.Company;
import com.stehanget.mvc.dto.CompanyRequestDTO;
import com.stehanget.mvc.dto.CompanyResponseDTO;

import java.util.List;

public interface CompanyService {

    List<Company> findMany();

    CompanyResponseDTO findOne(Long companyId);

    Company store(CompanyRequestDTO dto);

    Company update(Long companyId, CompanyRequestDTO dto);

    void destroy(Long companyId);
}
