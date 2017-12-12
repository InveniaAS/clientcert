package com.invenia.keychain.clientcert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@SpringBootApplication
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class X509AuthenticationServer extends WebSecurityConfigurerAdapter {
    public static void main(String[] args) {
        SpringApplication.run(X509AuthenticationServer.class, args);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/index/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/index").permitAll();
        http.authorizeRequests()
            .antMatchers("/signUp").authenticated().and()
            .x509()
            .userDetailsService(userDetailsService());
        http.authorizeRequests().anyRequest().permitAll();
        http.logout().logoutUrl("/logout")
            .logoutSuccessUrl("/index").invalidateHttpSession(true).and();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return new User(username,
                                "",
                                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
            }
        };
    }
}
