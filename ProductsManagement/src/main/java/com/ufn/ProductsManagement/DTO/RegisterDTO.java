package com.ufn.ProductsManagement.DTO;

import com.ufn.ProductsManagement.models.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
