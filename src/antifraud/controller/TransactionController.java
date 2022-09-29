package antifraud.controller;

import antifraud.model.ErrorDTO;
import antifraud.model.Transaction.TransactionRequest;
import antifraud.model.Transaction.TransactionResponse;
import antifraud.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/antifraud/transaction")
public class TransactionController {

    TransactionService transactionService;
    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping()
    public TransactionResponse processTransaction(@RequestBody TransactionRequest request) {
        return transactionService.processTransaction(request);
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorDTO> handleNullPointer(NullPointerException e){
        return ResponseEntity.badRequest().build();
    }
}
