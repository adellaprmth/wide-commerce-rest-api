package wide_commerce.rest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    private String id;

    private String username;

    private String name;

    private String password;

    private String token;

    @Column(name = "token_expired_at")
    private Timestamp tokenExpiredAt;

    @OneToMany(mappedBy = "user")
    private List<Cart> carts;
}
