package lk.sky360solutions.authentication.repository;

import lk.sky360solutions.authentication.model.persitent.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

  Optional<RefreshToken> findByUserId(Long id);

  Optional<RefreshToken> findByToken(String token);
}
