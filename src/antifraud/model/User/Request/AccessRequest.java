package antifraud.model.User.Request;

import antifraud.model.User.Operations;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessRequest {
    private String username;
    private Operations operation;
}
