package com.ufn.ProductsManagement.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufn.ProductsManagement.DTO.AuthenticationDTO;
import com.ufn.ProductsManagement.DTO.LoginResponseDTO;
import com.ufn.ProductsManagement.DTO.RegisterDTO;
import com.ufn.ProductsManagement.models.User;
import com.ufn.ProductsManagement.repository.UserRepository;
import com.ufn.ProductsManagement.security.TokenService;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserRepository repository;
	@Autowired
	private TokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
	    var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
	    try {
	        var auth = this.authenticationManager.authenticate(usernamePassword);
	        var token = tokenService.generateToken((User) auth.getPrincipal());
	        return ResponseEntity.ok(new LoginResponseDTO(token));
	    } catch (BadCredentialsException ex) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	    }
	}

	@PostMapping("/register")
	public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
		if (this.repository.findByLogin(data.login()) != null)
			return ResponseEntity.badRequest().build();

		String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
		User newUser = new User(data.login(), encryptedPassword, data.role());

		this.repository.save(newUser);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/logout")
	public ResponseEntity logout(@RequestHeader("Authorization") String token) {
		token = token.replace("Bearer ", "");

		tokenService.addToBlacklist(token);

		return ResponseEntity.ok("Logout bem-sucedido");
	}
}
