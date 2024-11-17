package wide_commerce.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wide_commerce.rest.dto.TokenResponse;
import wide_commerce.rest.dto.UserRequest;
import wide_commerce.rest.dto.UserResponse;
import wide_commerce.rest.dto.WebResponse;
import wide_commerce.rest.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(
            path = "api/auth/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> register(@RequestBody UserRequest request){
        userService.register(request);
        return WebResponse.<String>builder().data("Succesfully Registered").build();
    }

    @PostMapping(
            path = "api/auth/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<TokenResponse> login(@RequestBody UserRequest request){
        UserResponse userResponse = userService.login(request);
        return WebResponse
                .<TokenResponse>builder()
                .data(
                        TokenResponse
                                .builder()
                                .token(userResponse.getToken())
                                .tokenExpiredAt(userResponse.getTokenExpiredAt().getTime())
                                .build()
                )
                .build();
    }
}
