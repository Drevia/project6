package com.project.paymybuddy.configuration;

import com.mysql.cj.protocol.AuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConf {

    @Autowired
    DataSource dataSource;

    /*@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .formLogin((formLogin) ->
                        formLogin
                                .loginPage("/login")
                                .passwordParameter("password")
                                .usernameParameter("email")
                                .defaultSuccessUrl("/transfer", true)

                ).authorizeHttpRequests().anyRequest().permitAll();

        return http.build();
    }

    @Autowired
    void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println(dataSource);
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select email,mot_de_passe, 1 "
                + "from app_user where email = ?")
                .authoritiesByUsernameQuery("SELECT null, null, null from app_user WHERE email=?")
                .passwordEncoder(new BCryptPasswordEncoder());
        // configurer l'authentification, et bien penser à
        //modifier la query utilisée pour requêter le user
    }

}

