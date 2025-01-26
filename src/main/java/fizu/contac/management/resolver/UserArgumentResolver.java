package fizu.contac.management.resolver;

import fizu.contac.management.entity.User;
import fizu.contac.management.repository.UserRepository;
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

@Slf4j
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String token = request.getHeader("X-API-TOKEN");
        if(token == null){
            throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED, "belum Login");
        }

        User user = userRepository.findByToken(token).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unaotized"));
        if(user.getTokenExpiredAt() < System.currentTimeMillis()){
            throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED, "token anda sudah habis");
        }

        return user ;
    }
}
