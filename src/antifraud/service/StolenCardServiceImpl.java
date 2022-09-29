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
        if(!isValidCardNumber(card.getNumber())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if(cardRepository.existsByNumber(card.getNumber())){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        return cardRepository.save(card);
    }

    @Override
    @Transactional
    public DeleteResponse deleteCardByNumber(String number) {
        if(!isValidCardNumber(number)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if(!cardRepository.existsByNumber(number)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        System.out.println(cardRepository.deleteStolenCardByNumber(number));
        return new DeleteResponse("Card " + number + " successfully removed!");
    }

    @Override
    public List<StolenCard> showCards() {
        List<StolenCard> allCards = new ArrayList<>();
        cardRepository.findAll().forEach(allCards::add);
        return allCards;
    }
    private boolean isValidCardNumber(String number) {
        int len = number.length();
        int[] ints = number.chars().map(c -> c - '0').toArray();
        int sum = 0;
        for (int i = 0; i < len; i++) {
            if (i % 2 == 0) {
                ints[i] *= 2;
                if (ints[i] > 9) {
                    ints[i] -= 9;
                }
            }
            sum += ints[i];
        }
        return sum % 10 == 0;
    }
}
