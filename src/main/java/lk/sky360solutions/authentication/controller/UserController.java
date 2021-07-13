package lk.sky360solutions.authentication.controller;

import lk.sky360solutions.authentication.exception.InvalidRefreshTokenException;
import lk.sky360solutions.authentication.exception.RefreshTokenExpiredException;
import lk.sky360solutions.authentication.exception.UserNotFoundException;
import lk.sky360solutions.authentication.model.persitent.RefreshToken;
import lk.sky360solutions.authentication.model.request.LoginRq;
import lk.sky360solutions.authentication.model.request.RefreshTokenRq;
import lk.sky360solutions.authentication.model.response.RefreshTokenRs;
import lk.sky360solutions.authentication.model.response.TokenRs;
import lk.sky360solutions.authentication.service.RefreshTokenService;
import lk.sky360solutions.authentication.service.TokenService;
import lk.sky360solutions.authentication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "users/")
public class UserController {

  private final AuthenticationManager authenticationManager;
  private final UserService userService;
  private final TokenService tokenService;
  private final RefreshTokenService refreshTokenService;

  @ResponseStatus(HttpStatus.OK)
  @PostMapping(value = "login")
  public TokenRs login(@RequestBody LoginRq loginRq) throws Exception {

    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRq.getUsername(),
        loginRq.getPassword()));

      UserDetails userDetails = userService.loadUserByUsername(loginRq.getUsername());

      return TokenRs.builder()
        .token(tokenService.crate(userDetails.getUsername(), new HashMap<>()))
        .refreshToken(refreshTokenService.create(userService.findByUsername(loginRq.getUsername()).getId()).getToken())
        .build();
    } catch (Exception exception){
      throw new Exception(exception);
    }
  }

  @ResponseStatus(HttpStatus.OK)
  @PostMapping(value = "refresh-token")
  public RefreshTokenRs refreshToken(@RequestBody RefreshTokenRq refreshTokenRq)
    throws InvalidRefreshTokenException,
    RefreshTokenExpiredException, UserNotFoundException {

    RefreshToken refreshToken = refreshTokenService.findByRefreshToken(refreshTokenRq.getRefreshToken());

    refreshTokenService.verifyExpiration(refreshToken);

    return RefreshTokenRs.builder()
      .refreshToken(refreshTokenService.create(refreshToken.getUser().getId()).getToken())
      .token(tokenService.crate(refreshToken.getUser().getUsername(), new HashMap<>()))
      .build();
  }
}
