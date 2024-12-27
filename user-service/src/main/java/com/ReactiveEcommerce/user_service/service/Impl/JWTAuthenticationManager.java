package com.ReactiveEcommerce.user_service.service.Impl;
import com.ReactiveEcommerce.user_service.security.JWTUtil;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
@Component
public class JWTAuthenticationManager implements ReactiveAuthenticationManager {

    private final JWTUtil jwtUtil;
    private final TestService userService;

    public JWTAuthenticationManager(JWTUtil jwtUtil, TestService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

//    @Override
//    public Mono<Authentication> authenticate(Authentication authentication) throws AuthenticationException {
//        String token = authentication.getCredentials().toString();
//        String username = jwtUtil.extractUsername(token);
//
//        return userService.findByUsername(username)
//                .handle((userDetails, sink) -> {
//                    if (jwtUtil.validateToken(token, userDetails.getUsername())) {
//                        sink.next(new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities()));
//                    } else {
//                        sink.error(new AuthenticationException("Invalid JWT token") {});
//                    }
//                });
@Override
public Mono<Authentication> authenticate(Authentication authentication) {
    String token = authentication.getCredentials().toString();
    String username = jwtUtil.extractUsername(token);

    return userService.findByUsername(username)
            .switchIfEmpty(Mono.error(new RuntimeException("User not found")))
            .map(userDetails -> {
                if (jwtUtil.validateToken(token, userDetails.getUsername())) {
                    return new UsernamePasswordAuthenticationToken(
                            userDetails.getUsername(),
                            token,
                            userDetails.getAuthorities()
                    );
                } else {
                    throw new RuntimeException("Invalid JWT token");
                }
            });
}
    }

