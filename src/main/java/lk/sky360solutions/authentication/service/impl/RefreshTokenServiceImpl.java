package lk.sky360solutions.authentication.service.impl;

import lk.sky360solutions.authentication.exception.InvalidRefreshTokenException;
import lk.sky360solutions.authentication.exception.RefreshTokenExpiredException;
import lk.sky360solutions.authentication.exception.UserNotFoundException;
import lk.sky360solutions.authentication.model.persitent.RefreshToken;
import lk.sky360solutions.authentication.model.persitent.User;
import lk.sky360solutions.authentication.repository.RefreshTokenRepository;
import lk.sky360solutions.authentication.repository.UserRepository;
import lk.sky360solutions.authentication.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

  private final RefreshTokenRepository refreshTokenRepository;
  private final UserRepository userRepository;

  @Override
  public RefreshToken findByRefreshToken(String refreshToken) throws InvalidRefreshTokenException {
    return refreshTokenRepository.findByToken(refreshToken)
      .orElseThrow(() -> new InvalidRefreshTokenException("The token you provided [{" + refreshToken +
        "}] is invalid"));
  }

  @Override
  public RefreshToken create(Long userId) throws UserNotFoundException {

    User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("No user found for id [{"
      + userId + "}]"));

    RefreshToken refreshToken = new RefreshToken();

    refreshToken.setUser(user);
    refreshToken.setToken(UUID.randomUUID().toString());
    refreshToken.setExpire(new Date(System.currentTimeMillis() + (1000 * 60)));

    return refreshTokenRepository.save(refreshToken);
  }

  @Override
  public Boolean verifyExpiration(RefreshToken refreshToken) throws RefreshTokenExpiredException {

    if (refreshToken.getExpire().compareTo(new Date(System.currentTimeMillis())) < 0) {
      refreshTokenRepository.delete(refreshToken);
      throw new RefreshTokenExpiredException("The refresh token you provided is expired, please log in");
    }
    return false;
  }
}
