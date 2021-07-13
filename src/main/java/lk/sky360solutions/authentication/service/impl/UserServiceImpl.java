package lk.sky360solutions.authentication.service.impl;

import lk.sky360solutions.authentication.config.MyUserDetails;
import lk.sky360solutions.authentication.model.persitent.User;
import lk.sky360solutions.authentication.repository.UserRepository;
import lk.sky360solutions.authentication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.getByUsername(username);

    if (user == null) {
      throw new UsernameNotFoundException("Could not find user");
    }

    return new MyUserDetails(user);
  }

  public User findByUsername(String username) {
    return userRepository.getByUsername(username);
  }
}
