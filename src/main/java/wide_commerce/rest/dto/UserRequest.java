package wide_commerce.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    @NotBlank
    @Size(max = 100)
    private String username;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 100)
    private String password;
}
