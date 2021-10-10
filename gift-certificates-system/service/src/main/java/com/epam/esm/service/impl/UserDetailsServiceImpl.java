package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.JwtUser;
import com.epam.esm.entity.Status;
import com.epam.esm.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserDao userDao;

    @Autowired
    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userDao.findByEmail(email);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("user doesn't exists"));
        JwtUser jwtUser = new JwtUser(user.getEmail(), user.getPassword(), user.getRole().getAuthorities(), user.getStatus() == Status.ACTIVE);
        return jwtUser;
    }
}