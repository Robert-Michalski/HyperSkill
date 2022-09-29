package antifraud.service;

import antifraud.model.Transaction.TransactionRequest;
import antifraud.model.Transaction.TransactionResponse;
import antifraud.model.Transaction.TransactionResponseValues;
import antifraud.repository.StolenCardRepository;
import antifraud.repository.SusIpRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TransactionService {
    private StolenCardRepository cardRepository;
    private SusIpRepo ipRepository;

    public TransactionService(StolenCardRepository cardRepository, SusIpRepo ipRepository) {
        this.cardRepository = cardRepository;
        this.ipRepository = ipRepository;
    }
    public TransactionResponse processTransaction(TransactionRequest request) {
        if(ipRepository.existsByIp(request.getIp())){
            return new TransactionResponse(TransactionResponseValues.PROHIBITED, "ip");
        }
        if(cardRepository.existsByNumber(request.getNumber())){
            return new TransactionResponse(TransactionResponseValues.PROHIBITED, "card");
        }
        Long amount = request.getAmount();
        if (amount > 0 && amount <= 200) {
            return new TransactionResponse(TransactionResponseValues.ALLOWED, "none");
        } else if (amount > 200 && amount <= 1500) {
            return new TransactionResponse(TransactionResponseValues.MANUAL_PROCESSING, "amount");
        } else if (amount > 1500) {
            return new TransactionResponse(TransactionResponseValues.PROHIBITED, "amount");
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
