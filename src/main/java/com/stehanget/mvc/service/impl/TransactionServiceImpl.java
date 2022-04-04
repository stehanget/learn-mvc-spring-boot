package com.stehanget.mvc.service.impl;

import com.stehanget.mvc.domain.Company;
import com.stehanget.mvc.domain.Item;
import com.stehanget.mvc.domain.Transaction;
import com.stehanget.mvc.dto.TransactionRequestDTO;
import com.stehanget.mvc.dto.TransactionResponseDTO;
import com.stehanget.mvc.repository.CompanyRepository;
import com.stehanget.mvc.repository.ItemRepository;
import com.stehanget.mvc.repository.TransactionRepository;
import com.stehanget.mvc.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public List<TransactionResponseDTO> findMany() {
        return transactionRepository.findAll().stream().map(this::entityToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public TransactionResponseDTO findOne(Long transactionId) {
        try {
            Transaction res = transactionRepository.getById(transactionId);

            return entityToResponseDTO(res);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public TransactionResponseDTO store(TransactionRequestDTO req) {
        Transaction transaction = new Transaction();
        Company company = companyRepository.getById(req.getCompanyId());
        Item item = itemRepository.getById(req.getItemId());

        transaction.setCompany(company);
        transaction.setItem(item);
        transaction.setUnit(req.getUnit());
        transaction.setTotal(item.getPrice() * req.getUnit());
        transaction.setRemainingStock(item.getStock() - req.getUnit());
        transaction.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'").format(new Date()));
        transaction.setUpdatedAt(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'").format(new Date()));

        item.setStock(item.getStock() - req.getUnit());
        itemRepository.save(item);

        Transaction res = transactionRepository.save(transaction);

        return entityToResponseDTO(res);
    }

    @Override
    public TransactionResponseDTO update(Long transactionId, TransactionRequestDTO req) {
        try {
            Transaction transaction = transactionRepository.getById(transactionId);
            Company company = companyRepository.getById(req.getCompanyId());
            Item item = itemRepository.getById(req.getItemId());

            transaction.setCompany(company);
            transaction.setItem(item);
            transaction.setUnit(req.getUnit());
            transaction.setTotal(item.getPrice() * req.getUnit());
            transaction.setRemainingStock(item.getStock() - req.getUnit());
            transaction.setUpdatedAt(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'").format(new Date()));

            item.setStock(item.getStock() - req.getUnit());
            itemRepository.save(item);

            Transaction res = transactionRepository.save(transaction);

            return entityToResponseDTO(res);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void destroy(Long transactionId) {
        try {
            transactionRepository.deleteById(transactionId);
        } catch (Exception ignored) {

        }
    }

    private TransactionResponseDTO entityToResponseDTO(Transaction transaction) {
        TransactionResponseDTO dto = new TransactionResponseDTO();

        dto.setId(transaction.getId());
        dto.setCompanyId(transaction.getCompany().getId());
        dto.setItemId(transaction.getItem().getId());
        dto.setCompany(transaction.getCompany().getName());
        dto.setItem(transaction.getItem().getName());
        dto.setPrice(transaction.getItem().getPrice());
        dto.setUnit(transaction.getUnit());
        dto.setTotal(transaction.getTotal());
        dto.setRemainingStock(transaction.getRemainingStock());
        dto.setCreatedAt(transaction.getCreatedAt());
        dto.setUpdatedAt(transaction.getUpdatedAt());

        return dto;
    }
}
