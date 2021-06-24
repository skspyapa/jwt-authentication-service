package lk.sky360solutions.authentication.service;

import java.util.Map;

public interface TokenService {

  String getUsernameByToken(String token);

  Map<String, Object> getAllClaimsByToken(String token);

  String crate(String username, Map<String, Object> customParams);

  boolean isTokenExpired(String token);
}
