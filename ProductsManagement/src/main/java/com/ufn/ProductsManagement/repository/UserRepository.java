package com.ufn.ProductsManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.ufn.ProductsManagement.models.User;

public interface UserRepository extends JpaRepository<User, String> {
    UserDetails findByLogin(String login);
}

