package br.edu.ifba.jogomemoria.infrastructure.controller;

import br.edu.ifba.jogomemoria.card.model.Card;
import br.edu.ifba.jogomemoria.infrastructure.security.session.UserSession;
import br.edu.ifba.jogomemoria.infrastructure.service.Facade;
import br.edu.ifba.jogomemoria.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private Facade facade;

    @Autowired
    private UserSession userSession;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView index () {
        ModelAndView mv = new ModelAndView("/index");
        mv.addObject("user", userSession.getUser());
        return mv;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login () {
        ModelAndView mv = new ModelAndView("/login");

        if (userSession.isAuthenticated()) {
            return panel();
        }

        return mv;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void logout() {
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
    }

    @RequestMapping(value = "/painel", method=RequestMethod.GET)
    public ModelAndView panel () {
        ModelAndView mv = new ModelAndView("/panel");
        mv.addObject("user", userSession.getUser());
        return mv;
    }

    @RequestMapping(value = "/painel_cartas", method = RequestMethod.GET)
    public ModelAndView panelCards () {
        ModelAndView mv = new ModelAndView("/panel_cards");
        Iterable<Card> cards = facade.findAllCards();
        mv.addObject("user", userSession.getUser());
        mv.addObject("cards", cards);
        return mv;
    }

    @RequestMapping(value = "/painel_usuarios", method = RequestMethod.GET)
    public ModelAndView panelUsers () {
        ModelAndView mv = new ModelAndView("/panel_users");
        Iterable<User> users = facade.findAllUsers();
        mv.addObject("user", userSession.getUser());
        mv.addObject("users", users);
        return mv;
    }
}