package br.edu.ifba.jogomemoria.infrastructure.security.session;

import br.edu.ifba.jogomemoria.infrastructure.security.model.UserSecurity;
import br.edu.ifba.jogomemoria.infrastructure.service.Facade;
import br.edu.ifba.jogomemoria.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserSession {

    @Autowired
    private Facade facade;

    public User getUser () {

        if (isAuthenticated()) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return facade.findUserById(((UserSecurity) principal).getId());
        }

        return null;
    }

    public boolean isAuthenticated(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}