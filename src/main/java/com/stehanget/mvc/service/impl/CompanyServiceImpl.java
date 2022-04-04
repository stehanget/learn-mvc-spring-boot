package com.stehanget.mvc.service.impl;

import com.stehanget.mvc.domain.Company;
import com.stehanget.mvc.dto.CompanyRequestDTO;
import com.stehanget.mvc.dto.CompanyResponseDTO;
import com.stehanget.mvc.repository.CompanyRepository;
import com.stehanget.mvc.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public List<Company> findMany() {
        return companyRepository.findAll();
    }

    @Override
    public CompanyResponseDTO findOne(Long companyId) {
        try {
            CompanyResponseDTO dto = new CompanyResponseDTO();
            Company res = companyRepository.getById(companyId);

            dto.setId(res.getId());
            dto.setCode(res.getCode());
            dto.setName(res.getName());
            dto.setPhone(res.getPhone());
            dto.setEmail(res.getEmail());
            dto.setAddress(res.getAddress());
            dto.setCreatedAt(res.getCreatedAt());
            dto.setUpdatedAt(res.getUpdatedAt());

            return dto;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Company store(CompanyRequestDTO dto) {
        Company company = new Company();

        company.setCode(dto.getCode());
        company.setName(dto.getName());
        company.setPhone(dto.getPhone());
        company.setEmail(dto.getEmail());
        company.setAddress(dto.getAddress());
        company.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'").format(new Date()));
        company.setUpdatedAt(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'").format(new Date()));

        return companyRepository.save(company);
    }

    @Override
    public Company update(Long companyId, CompanyRequestDTO dto) {
        try {
            Company company = companyRepository.getById(companyId);

            company.setCode(dto.getCode());
            company.setName(dto.getName());
            company.setPhone(dto.getPhone());
            company.setEmail(dto.getEmail());
            company.setAddress(dto.getAddress());
            company.setUpdatedAt(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'").format(new Date()));

            return companyRepository.save(company);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void destroy(Long companyId) {
        try {
            companyRepository.deleteById(companyId);
        } catch (Exception ignored) {

        }
    }
}
