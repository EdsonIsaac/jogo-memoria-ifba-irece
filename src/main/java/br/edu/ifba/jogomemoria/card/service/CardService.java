package br.edu.ifba.jogomemoria.card.service;

import br.edu.ifba.jogomemoria.card.model.Card;
import br.edu.ifba.jogomemoria.card.repository.CardRepository;
import br.edu.ifba.jogomemoria.infrastructure.exception.ObjectNotFoundException;
import br.edu.ifba.jogomemoria.infrastructure.exception.ValidationException;
import br.edu.ifba.jogomemoria.infrastructure.suport.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public Card findById (Long id) {

        Optional<Card> card = cardRepository.findById(id);

        if (!card.isPresent()) {
            throw new ObjectNotFoundException("Carta não encontrada!");
        }

        return card.get();
    }

    public List<Card> findAll () {
        return cardRepository.findAll();
    }

    public Card save (Card card) {

        if (card == null) {
            throw new ValidationException("Carta nula!");
        }

        if (validate(card)) {
            cardRepository.save(card);
        }

        return card;
    }

    public void delete (Card card) {

        if (card == null) {
            throw new ValidationException("Carta nula!");
        }

        if (cardRepository.findById(card.getId()) == null) {
            throw new ObjectNotFoundException("Carta não existe!");
        }

        cardRepository.delete(card);
    }

    private boolean validate (Card card) {

        StringUtil strUtil = new StringUtil();

        if (strUtil.isNullOrEmpty(card.getCardOne())) {
            throw new ValidationException("Carta um inválida!");
        }

        if (strUtil.isNullOrEmpty(card.getCardTwo())) {
            throw new ValidationException("Carta dois inválida!");
        }

        return true;
    }
}