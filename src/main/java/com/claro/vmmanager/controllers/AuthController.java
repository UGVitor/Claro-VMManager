package com.claro.vmmanager.controllers;

import com.claro.vmmanager.dto.LoginRequestDTO;
import com.claro.vmmanager.dto.RegisterRequestDTO;
import com.claro.vmmanager.dto.UserResponseDTO;
import com.claro.vmmanager.infra.security.TokenService;
import com.claro.vmmanager.models.User;
import com.claro.vmmanager.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Tag(name = "Login/Register", description = "Endpoints for managing login and register")
public class AuthController {

     private final UserRepository userRepository;
     private final PasswordEncoder passwordEncoder;
     private final TokenService tokenService;

     public AuthController(
             UserRepository userRepository,
             PasswordEncoder passwordEncoder,
             TokenService tokenService
     ) {
          this.userRepository = userRepository;
          this.passwordEncoder = passwordEncoder;
          this.tokenService = tokenService;
     }
     @PostMapping("/v1/login")
     @Operation(summary = "User login", description = "Authenticates the user and returns a JWT token"
     )
     @ApiResponses(value = {
             @ApiResponse(responseCode = "200", description = "Login successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
             @ApiResponse(responseCode = "400", description = "Invalid password", content = @Content),
             @ApiResponse(responseCode = "404", description = "User not found",content = @Content)
     })
     public ResponseEntity<UserResponseDTO> login(@RequestBody LoginRequestDTO body) {
          User user = this.userRepository
                  .findByEmail(body.email())
                  .orElseThrow(() -> new RuntimeException("User not found"));
          if (passwordEncoder.matches(body.password(), user.getPassword())) {
               String token = this.tokenService.generateToken(user);
               return ResponseEntity.ok(new UserResponseDTO(user.getName(), token));
          }
          return ResponseEntity.badRequest().build();
     }

     @PostMapping("/v1/register")
     @Operation(summary = "User registration", description = "Registers a new user and returns a JWT token")
     @ApiResponses(value = {
             @ApiResponse(responseCode = "200", description = "User successfully registered", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
             @ApiResponse(responseCode = "400", description = "User already exists", content = @Content)})
     public ResponseEntity register(@RequestBody RegisterRequestDTO body){
          Optional<User> user = this.userRepository.findByEmail(body.email());

          if(user.isEmpty()) {
               User newUser = new User();
               newUser.setPassword(passwordEncoder.encode(body.password()));
               newUser.setEmail(body.email());
               newUser.setName(body.name());
               this.userRepository.save(newUser);

               String token = this.tokenService.generateToken(newUser);
               return ResponseEntity.ok(new UserResponseDTO(newUser.getName(), token));
          }
          return ResponseEntity.badRequest().build();
     }
}

