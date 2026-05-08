package com.smnk107.APIGateway;

import io.jsonwebtoken.JwtException;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component

public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final JwtService jwtService;

    public AuthenticationFilter(JwtService jwtService)
    {
        super(Config.class);
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {

        GatewayFilter gatewayFilter = new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

                String authHeader = exchange.
                        getRequest().
                        getHeaders().
                        getFirst("Authorization");

                if(authHeader==null)
                {
                    System.out.println("Authheader is null !!");
                }

                System.out.println("1token :"+authHeader);
                String jwtToken = authHeader.split("Bearer ")[1];

                String userId = jwtService.getUserIdFromToken(jwtToken).toString();

//                ServerWebExchange modifiedExchange = exchange
//                                                        .getRequest()
//                                                        .mutate()
//                        .header("X-userId",userId).build();

                try {
                    ServerWebExchange modifiedExchange = exchange
                            .mutate()
                            .request(r -> r.header("X-userId", userId))
                            .build();
                    return chain.filter(modifiedExchange);
                }catch (JwtException jwtException)
                {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    exchange.getResponse().setComplete();

                    System.out.println("EXCEPTION "+jwtException);
                }

                return null;
            }
        };

        return gatewayFilter;
    }

    public static class Config
    {

    }
}

