package com.stehanget.mvc.service;

import com.stehanget.mvc.domain.Transaction;
import com.stehanget.mvc.dto.TransactionRequestDTO;
import com.stehanget.mvc.dto.TransactionResponseDTO;

import java.util.List;

public interface TransactionService {
    List<TransactionResponseDTO> findMany();

    TransactionResponseDTO findOne(Long transactionId);

    TransactionResponseDTO store(TransactionRequestDTO req);

    TransactionResponseDTO update(Long transactionId, TransactionRequestDTO req);

    void destroy(Long transactionId);
}
