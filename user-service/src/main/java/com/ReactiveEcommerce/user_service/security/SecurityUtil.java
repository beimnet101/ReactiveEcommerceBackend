package com.ReactiveEcommerce.user_service.security;



        import org.springframework.security.core.Authentication;
        import org.springframework.security.core.context.ReactiveSecurityContextHolder;
        import org.springframework.stereotype.Component;
        import reactor.core.publisher.Mono;



       @Component
        public class SecurityUtil {

    /**
     * Retrieves the Bearer token from the SecurityContext.
     *
     * @return Mono<String> containing the Bearer token if present.
     */

    public static Mono<String> getBearerToken() {
        return ReactiveSecurityContextHolder.getContext()
                .flatMap(securityContext -> {
                    Authentication authentication = securityContext.getAuthentication();
                    if (authentication != null && authentication.getCredentials() != null) {
                        return Mono.just(authentication.getCredentials().toString());
                    }
                    return Mono.empty(); // Return empty if no token is found
                });
    }
}

