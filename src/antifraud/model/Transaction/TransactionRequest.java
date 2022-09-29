package antifraud.model.Transaction;

import antifraud.validation.ValidIp;
import lombok.*;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    private Long amount;
    @NotBlank
    @ValidIp
    private String ip;
    @NotBlank
    @CreditCardNumber
    private String number;
}
