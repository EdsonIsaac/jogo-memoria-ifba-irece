package br.edu.ifba.jogomemoria.infrastructure.controller;

import br.edu.ifba.jogomemoria.infrastructure.security.session.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @Autowired
    private UserSession userSession;

    @RequestMapping(value = "/error")
    public ModelAndView handleError(HttpServletRequest request) {

        ModelAndView mv;
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
                mv = new ModelAndView("/error/401");
            }

            else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                mv = new ModelAndView("/error/403");
            }

            else if (statusCode == HttpStatus.NOT_FOUND.value()) {
                mv = new ModelAndView("/error/404");
            }

            else if (statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()) {
                mv = new ModelAndView("/error/405");
            }

            else {
                mv = new ModelAndView("/error/error");
            }
        } else {
            mv = new ModelAndView("/error/error");
        }

        mv.addObject("user", userSession.getUser());

        return mv;
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}