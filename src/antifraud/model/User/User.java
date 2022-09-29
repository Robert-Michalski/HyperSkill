package antifraud.model.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "custom_user")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    @Column(name = "user_name")
    private String name;
    @NotBlank
    @Column(name = "full_username")
    private String username;
    @NotBlank
    private String password;
    private UserRoles role;
    @JsonIgnore
    private boolean locked;
}
