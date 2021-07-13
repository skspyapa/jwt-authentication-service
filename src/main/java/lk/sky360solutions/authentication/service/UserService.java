package lk.sky360solutions.authentication.service;

import lk.sky360solutions.authentication.model.persitent.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  User findByUsername(String username);
}
