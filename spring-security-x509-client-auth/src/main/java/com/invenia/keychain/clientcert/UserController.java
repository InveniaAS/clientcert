package com.invenia.keychain.clientcert;

import java.lang.reflect.Method;
import java.security.Principal;
import java.security.cert.X509Certificate;

import org.springframework.http.HttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.security.x509.X509CertImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @RequestMapping(value = "/user")
    public String user(Model model, Principal principal) {
        UserDetails currentUser = (UserDetails) ((Authentication) principal).getPrincipal();
        model.addAttribute("username", currentUser.getUsername());
        model.addAttribute("validto", ((X509Certificate)((Authentication) principal).getCredentials()).getNotAfter().toString());
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
