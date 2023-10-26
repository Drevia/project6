package com.project.paymybuddy.service;

import com.project.paymybuddy.model.AppUser;
import com.project.paymybuddy.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final static Logger LOG = LoggerFactory.getLogger(UserService.class);

    /**
     *
     * @return the current user
     */
    public AppUser getUser() {
        LOG.info("Searching for user to connect");
        String currentUser = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        return userRepository.findByEmail(currentUser);
    }
}
