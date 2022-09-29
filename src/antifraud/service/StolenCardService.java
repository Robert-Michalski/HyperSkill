package antifraud.service;

import antifraud.model.DeleteResponse;
import antifraud.model.StolenCard;

import java.util.List;
public interface StolenCardService {

    StolenCard saveCard(StolenCard card);

    DeleteResponse deleteCardByNumber(String number);

    List<StolenCard> showCards();
}
