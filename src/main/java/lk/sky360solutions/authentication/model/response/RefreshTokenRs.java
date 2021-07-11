package lk.sky360solutions.authentication.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshTokenRs {

  private final String token;

  private final String refreshToken;

}
