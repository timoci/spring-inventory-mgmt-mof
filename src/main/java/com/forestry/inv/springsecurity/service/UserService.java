package com.forestry.inv.springsecurity.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.forestry.inv.springsecurity.model.Users;
import com.forestry.inv.springsecurity.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService {

    Users findByEmail(String email);

    Users save(UserRegistrationDto registration);

	
}
