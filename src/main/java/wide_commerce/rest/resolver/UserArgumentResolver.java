package wide_commerce.rest.resolver;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.server.ResponseStatusException;
import wide_commerce.rest.entity.User;
import wide_commerce.rest.repository.UserRepository;

@Slf4j
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return User.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        var token = getToken(webRequest);

        var user = userRepository.findFirstByToken(token).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Token"));
        
        if (user.getTokenExpiredAt().getTime() < System.currentTimeMillis()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token expired");
        }

        return user;
    }

    private static String getToken(NativeWebRequest webRequest) {
        HttpServletRequest servletRequest = (HttpServletRequest) webRequest.getNativeRequest();

        var authHeader = servletRequest.getHeader("Authorization");
        if (authHeader == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No Authorization header found");
        }

        var ss = authHeader.split(" ");
        if (ss.length < 2) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Authorization header format");
        }

        return ss[1];
    }
}
