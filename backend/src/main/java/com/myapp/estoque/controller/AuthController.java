package com.myapp.estoque.controller;

import com.myapp.estoque.dto.AuthDTO;
import com.myapp.estoque.dto.RegisterDTO;
import com.myapp.estoque.dto.TokenDTO;
import com.myapp.estoque.model.Usuario;
import com.myapp.estoque.repository.UsuarioRepository;
import com.myapp.estoque.security.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @Value("${api.security.token.secret}")
    private String secret;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthDTO data, HttpServletResponse response) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.getLogin(), data.getPassword());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            String token = tokenService.generateToken((Usuario) auth.getPrincipal());
            TokenDTO tokenDTO = new TokenDTO(token);

            //salva o token no cookie;
            ResponseCookie cookie = ResponseCookie.from("jwt", token)
                    .httpOnly(true)
                    .maxAge( 24 * 60 * 60)
                    .path("/")
                    .secure(false)
                    .sameSite(SameSiteCookies.STRICT.toString())
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return ResponseEntity.ok(auth);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO data) {
        if (usuarioRepository.findByLogin(data.getLogin()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        Usuario usuario = new Usuario(data.getLogin(), encryptedPassword, data.getRole());

        this.usuarioRepository.save(usuario);

        return ResponseEntity.ok(usuario);
    }
}
