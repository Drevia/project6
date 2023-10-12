package com.project.paymybuddy.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConf {

    @Autowired
    private DataSource dataSource;
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
                                .permitAll()

                ).authorizeHttpRequests().requestMatchers("/transfer").fullyAuthenticated()
                .requestMatchers("/css/login-styles.css").permitAll()
                .requestMatchers("/css/transfer-styles.css").permitAll();

        return http.build();
    }

    @Autowired
    void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select email,mot_de_passe, 1 "
                + "from app_user where email = ?")
                .authoritiesByUsernameQuery("SELECT null, null, null from app_user WHERE email=?")
                .passwordEncoder(new BCryptPasswordEncoder());
        // configurer l'authentification, et bien penser à
        //modifier la query utilisée pour requêter le user
    }

}

