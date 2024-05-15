package com.account.account.dto.converter;

import com.account.account.dto.TransactionDTO;
import com.account.account.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionDTOConverter {
    public TransactionDTO convert(Transaction from) {
        return new TransactionDTO(from.getId(), from.getTransactionType(), from.getAmount(), from.getTransactionDate());
    }
}
