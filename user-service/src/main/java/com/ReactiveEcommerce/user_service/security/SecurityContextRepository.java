package com.ReactiveEcommerce.user_service.security;


import com.ReactiveEcommerce.user_service.service.Impl.JWTAuthenticationManager;
import com.ReactiveEcommerce.user_service.service.Impl.JWTAuthenticationToken;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {

    private final JWTAuthenticationManager authenticationManager;

    public SecurityContextRepository(JWTAuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        throw new UnsupportedOperationException("Save is not supported.");
    }

//    @Override
//    public Mono<SecurityContext> load(ServerWebExchange exchange) {
//        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//        if (token != null && token.startsWith("Bearer ")) {
//            token = token.substring(7);
//            return authenticationManager.authenticate(new JWTAuthenticationToken(token))
//                    .map(SecurityContextImpl::new);
//        }
//        return Mono.empty();
@Override
public Mono<SecurityContext> load(ServerWebExchange exchange) {
    return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("Authorization"))
            .filter(authHeader -> authHeader.startsWith("Bearer "))
            .map(authHeader -> authHeader.substring(7)) // Extract the token
            .flatMap(token -> {
                JWTAuthenticationToken authToken = new JWTAuthenticationToken(token);
                return authenticationManager.authenticate(authToken)
                        .map(SecurityContextImpl::new);
            });
            }


    }



