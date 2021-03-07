package br.edu.ifba.jogomemoria.card.controller;

import br.edu.ifba.jogomemoria.card.model.Card;
import br.edu.ifba.jogomemoria.infrastructure.service.Facade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CardController {

    @Autowired
    private Facade facade;

    @RequestMapping(value = "/card/findAll", method = RequestMethod.GET)
    public ModelAndView findAll() {
        ModelAndView mv = new ModelAndView("/fragments/card :: list-cards");
        Iterable<Card> cards = facade.findAllCards();
        mv.addObject("cards", cards);
        return mv;
    }

    @RequestMapping(value = "/card/findAllToGame", method = RequestMethod.GET)
    public ResponseEntity findAllToGame () {
        return ResponseEntity.status(HttpStatus.OK).body(facade.findAllCards());
    }

    @RequestMapping(value = "/card/save", method = RequestMethod.POST)
    public ResponseEntity save (Card card) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.save(card));
    }

    @RequestMapping(value = "/card/delete", method = RequestMethod.DELETE)
    public ResponseEntity delete (@RequestParam Long id) {
        Card card = facade.findCardById(id);
        facade.delete(card);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}