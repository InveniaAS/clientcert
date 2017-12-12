package com.invenia.keychain.clientcert;

import java.security.Principal;
import java.security.cert.X509Certificate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @RequestMapping(value = "/user")
    public String user(Model model, Principal principal) {
        if (principal != null) {
            UserDetails currentUser = (UserDetails) ((Authentication) principal).getPrincipal();
            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("validto", ((X509Certificate) ((Authentication) principal).getCredentials()).getNotAfter().toString());
        }
        return "user";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest httpServletRequest) {
        try {
            httpServletRequest.logout();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return "index";
    }
}
