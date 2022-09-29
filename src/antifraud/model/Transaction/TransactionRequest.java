package antifraud.model.Transaction;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    private Long amount;
    @NotBlank
    private String ip;
    @NotBlank
    private String number;
}
