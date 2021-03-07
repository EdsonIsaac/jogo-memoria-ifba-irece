package br.edu.ifba.jogomemoria.user.controller;

import br.edu.ifba.jogomemoria.infrastructure.security.session.UserSession;
import br.edu.ifba.jogomemoria.infrastructure.service.Facade;
import br.edu.ifba.jogomemoria.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @Autowired
    private Facade facade;

    @Autowired
    private UserSession userSession;

    @RequestMapping(value = "/user/findAll", method = RequestMethod.GET)
    public ModelAndView findAll () {
        ModelAndView mv = new ModelAndView("/fragments/user :: list-users");
        Iterable<User> users = facade.findAllUsers();
        mv.addObject("user", userSession.getUser());
        mv.addObject("users", users);
        return mv;
    }

    @RequestMapping(value = "/user/save", method = RequestMethod.POST)
    public ResponseEntity save (User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.save(user));
    }

    @RequestMapping(value = "/user/delete", method = RequestMethod.DELETE)
    public ResponseEntity delete (@RequestParam Long id) {
        User user = facade.findUserById(id);
        facade.delete(user);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}