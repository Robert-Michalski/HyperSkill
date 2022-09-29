package antifraud.service;

import antifraud.model.Transaction.TransactionRequest;
import antifraud.model.Transaction.TransactionResponse;
import antifraud.model.Transaction.TransactionResponseValues;
import antifraud.repository.StolenCardRepository;
import antifraud.repository.SuspiciousIpRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TransactionService {
    private final StolenCardRepository cardRepository;
    private final SuspiciousIpRepository ipRepository;

    public TransactionService(StolenCardRepository cardRepository, SuspiciousIpRepository ipRepository) {
        this.cardRepository = cardRepository;
        this.ipRepository = ipRepository;
    }

    TransactionResponse response = new TransactionResponse();

    public TransactionResponse processTransaction(TransactionRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        response.setInfo("");
        //appending
        Long amount = request.getAmount();
        if (amount <= 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (amount > 0 && amount <= 200) {
            response.setResult(TransactionResponseValues.ALLOWED);
            response.setInfo(stringBuilder.append("none").toString());
        } else if (amount > 200 && amount <= 1500) {
            response.setResult(TransactionResponseValues.MANUAL_PROCESSING);
            if (response.getInfo().isEmpty()) {
                response.setInfo(stringBuilder.append("amount").toString());
            } else {
                response.setInfo(stringBuilder.append(", amount").toString());
            }
        } else if (amount > 1500) {
            response.setResult(TransactionResponseValues.PROHIBITED);
            if (response.getInfo().isEmpty()) {
                response.setInfo(stringBuilder.append("amount").toString());
            } else {
                response.setInfo(stringBuilder.append(", amount").toString());
            }
        }
        if (cardRepository.existsByNumber(request.getNumber())) {
            response.setResult(TransactionResponseValues.PROHIBITED);
            if (response.getInfo().isEmpty()) {
                response.setInfo(stringBuilder.append("card").toString());
            } else {
                response.setInfo(stringBuilder.append(", card").toString());
            }
        }
        if (ipRepository.existsByIp(request.getIp())) {
            response.setResult(TransactionResponseValues.PROHIBITED);
            if (response.getInfo().isEmpty()) {
                response.setInfo(stringBuilder.append("ip").toString());
            } else {
                response.setInfo(stringBuilder.append(", ip").toString());
            }
        }
        return response;
    }
}
