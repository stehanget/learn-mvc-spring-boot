package com.stehanget.mvc.web;

import com.stehanget.mvc.domain.Transaction;
import com.stehanget.mvc.dto.TransactionRequestDTO;
import com.stehanget.mvc.dto.TransactionResponseDTO;
import com.stehanget.mvc.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionResource {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transaction")
    public ResponseEntity<List<TransactionResponseDTO>> index() {
        List<TransactionResponseDTO> transactions = transactionService.findMany();

        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<TransactionResponseDTO> show(@PathVariable("transactionId") Long transactionId) {
        TransactionResponseDTO transaction = transactionService.findOne(transactionId);

        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/transaction")
    public ResponseEntity<TransactionResponseDTO> store(@RequestBody TransactionRequestDTO dto) throws URISyntaxException {
        TransactionResponseDTO transaction = transactionService.store(dto);

        return ResponseEntity.created(new URI("/api/transaction")).body(transaction);
    }

    @PutMapping("/transaction/{transactionId}")
    public ResponseEntity<TransactionResponseDTO> update(@PathVariable("transactionId") Long transactionId, @RequestBody TransactionRequestDTO dto) {
        TransactionResponseDTO transaction = transactionService.update(transactionId, dto);

        return ResponseEntity.ok(transaction);
    }

    @DeleteMapping("/transaction/{transactionId}")
    public ResponseEntity<Void> destroy(@PathVariable("transactionId") Long transactionId) {
        transactionService.destroy(transactionId);

        return ResponseEntity.ok().build();
    }
}
