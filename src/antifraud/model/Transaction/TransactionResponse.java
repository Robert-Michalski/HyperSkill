package antifraud.model.Transaction;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionResponse {
    private TransactionResponseValues result;
    private String info;

    public TransactionResponse(TransactionResponseValues result, String info){
        this.result = result;
        this.info = info;
    }
}
