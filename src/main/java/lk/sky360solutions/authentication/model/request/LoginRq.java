package lk.sky360solutions.authentication.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRq {

  private String username;

  private String password;

}
