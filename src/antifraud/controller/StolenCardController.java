package antifraud.controller;

import antifraud.model.DeleteResponse;
import antifraud.model.StolenCard;
import antifraud.service.StolenCardService;
import antifraud.validation.ValidIp;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/antifraud")
public class StolenCardController {

    private StolenCardService cardService;
    public StolenCardController(StolenCardService cardService){
        this.cardService = cardService;
    }

    @PostMapping("/stolencard")
    @ResponseStatus(HttpStatus.OK)
    public StolenCard saveStolenCard(@RequestBody StolenCard card){
        return cardService.saveCard(card);
    }

    @DeleteMapping("/stolencard/{number}")
    @ResponseStatus(HttpStatus.OK)
    public DeleteResponse deleteStolenCard(@PathVariable String number){
        return cardService.deleteCardByNumber(number);
    }

    @GetMapping("/stolencard")
    @ResponseStatus(HttpStatus.OK)
    public List<StolenCard> showStolenCards(){
        return cardService.showCards();
    }

}
