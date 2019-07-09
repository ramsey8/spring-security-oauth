//package org.baeldung.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
//
////@Configuration
////@EnableResourceServer
//public class GatewayConfiguration extends ResourceServerConfigurerAdapter {
//
//    @Autowired
//    private TokenStore redisTokenStore;
//
//    @Override
//    public void configure(final HttpSecurity http) throws Exception {
//        http.authorizeRequests().
//            antMatchers("/oauth/**").
//            permitAll().
//            antMatchers("/**").
//            authenticated();
//    }
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.tokenStore(redisTokenStore);
//    }
//
//
//    @Bean
//    @Primary
//    public TokenStore redisTokenStore(RedisConnectionFactory factory) {
//
//        return new RedisTokenStore(factory);
//    }
//
//}
