package lk.sky360solutions.authentication.model.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class TokenRs implements Serializable {

  private final String token;
}
