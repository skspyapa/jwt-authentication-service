package lk.sky360solutions.authentication.service;

import lk.sky360solutions.authentication.exception.InvalidRefreshTokenException;
import lk.sky360solutions.authentication.exception.RefreshTokenExpiredException;
import lk.sky360solutions.authentication.exception.UserNotFoundException;
import lk.sky360solutions.authentication.model.persitent.RefreshToken;

public interface RefreshTokenService {

  RefreshToken findByRefreshToken(String refreshToken) throws InvalidRefreshTokenException;

  RefreshToken create(Long userId) throws UserNotFoundException;

  Boolean verifyExpiration(RefreshToken refreshToken) throws RefreshTokenExpiredException;
}
