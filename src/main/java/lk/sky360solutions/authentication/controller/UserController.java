package lk.sky360solutions.authentication.controller;

import lk.sky360solutions.authentication.model.request.LoginRq;
import lk.sky360solutions.authentication.model.response.TokenRs;
import lk.sky360solutions.authentication.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "users/")
public class UserController {

  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;
  private final TokenService tokenService;

  @ResponseStatus(HttpStatus.OK)
  @PostMapping(value = "login")
  public TokenRs login(@RequestBody LoginRq loginRq) throws Exception {

    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRq.getUsername(),
        loginRq.getPassword()));

      UserDetails userDetails = userDetailsService.loadUserByUsername(loginRq.getUsername());

      return TokenRs.builder()
        .token(tokenService.crate(userDetails.getUsername(), new HashMap<>()))
        .build();
    } catch (Exception exception){
      throw new Exception(exception);
    }
  }
}
