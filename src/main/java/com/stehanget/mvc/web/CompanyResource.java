package com.stehanget.mvc.web;

import com.stehanget.mvc.domain.Company;
import com.stehanget.mvc.dto.CompanyRequestDTO;
import com.stehanget.mvc.dto.CompanyResponseDTO;
import com.stehanget.mvc.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CompanyResource {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/company")
    public ResponseEntity<List<Company>> index() {
        List<Company> companies = companyService.findMany();

        return ResponseEntity.ok(companies);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<CompanyResponseDTO> show(@PathVariable("companyId") Long companyId) {
        CompanyResponseDTO company = companyService.findOne(companyId);

        return ResponseEntity.ok(company);
    }

    @PostMapping("/company")
    public ResponseEntity<Company> store(@RequestBody CompanyRequestDTO dto) throws URISyntaxException {
        Company company = companyService.store(dto);

        return ResponseEntity.created(new URI("/api/company")).body(company);
    }

    @PutMapping("/company/{companyId}")
    public ResponseEntity<Company> update(@PathVariable("companyId") Long companyId, @RequestBody CompanyRequestDTO dto) {
        Company company = companyService.update(companyId, dto);

        return ResponseEntity.ok(company);
    }

    @DeleteMapping("/company/{companyId}")
    public ResponseEntity<Void> destroy(@PathVariable("companyId") Long companyId) {
        companyService.destroy(companyId);

        return ResponseEntity.ok().build();
    }
}
