package br.edu.ifba.jogomemoria.infrastructure.service;

import br.edu.ifba.jogomemoria.card.model.Card;
import br.edu.ifba.jogomemoria.card.service.CardService;
import br.edu.ifba.jogomemoria.user.model.User;
import br.edu.ifba.jogomemoria.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Facade {

    @Autowired
    private CardService cardService;

    @Autowired
    private UserService userService;

    /************************************ CARD ************************************/

    public Card findCardById (Long id) {
        return cardService.findById(id);
    }

    public List<Card> findAllCards() {
        return cardService.findAll();
    }

    public Card save (Card card) {
        return cardService.save(card);
    }

    public void delete (Card card) {
        cardService.delete(card);
    }

    /************************************ USER ************************************/

    public User findUserById (Long id) {
        return userService.findById(id);
    }

    public User findUserByUsername (String username) {
        return userService.findByUsername(username);
    }

    public List<User> findAllUsers() {
        return userService.findAll();
    }

    public User save (User user) {
        return userService.save(user);
    }

    public void delete (User user) {
        userService.delete(user);
    }
}