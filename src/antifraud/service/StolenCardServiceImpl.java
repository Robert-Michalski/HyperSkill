package antifraud.service;

import antifraud.model.DeleteResponse;
import antifraud.model.StolenCard;
import antifraud.repository.StolenCardRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class StolenCardServiceImpl implements StolenCardService {

    private StolenCardRepository cardRepository;

    public StolenCardServiceImpl(StolenCardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public StolenCard saveCard(StolenCard card) {
        if (cardRepository.existsByNumber(card.getNumber())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (!isValidCardNumber(card.getNumber())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return cardRepository.save(card);
    }

    @Override
    @Transactional
    public DeleteResponse deleteCardByNumber(String number) {
        if (!cardRepository.existsByNumber(number)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (!isValidCardNumber(number)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        cardRepository.deleteStolenCardByNumber(number);
        return new DeleteResponse("Card " + number + " successfully removed!");
    }

    @Override
    public List<StolenCard> showCards() {
        List<StolenCard> allCards = new ArrayList<>();
        cardRepository.findAll().forEach(allCards::add);
        return allCards;
    }

    private boolean isValidCardNumber(String number) {
        int digits = number.length();
        int sum = 0;
        for (int i = 0; i < digits; i++) {
            if (i % 2 == 0) {
                number.replace(number.charAt(i), (char) (number.charAt(i) * 2));
            }
            if (number.charAt(i) > 9) {
                number.replace(number.charAt(i), (char) (number.charAt(i) - 9));
            }
            sum += number.charAt(i);
        }
        return sum % 10 == 0;
    }
}
