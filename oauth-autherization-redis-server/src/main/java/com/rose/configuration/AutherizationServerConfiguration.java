package com.rose.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.swing.plaf.SeparatorUI;
import java.util.HashMap;

/**
 * 登录及权限鉴定配置
 */
@Configuration
@EnableAuthorizationServer
public class AutherizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private TokenStore redisTokenStore;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()");
        security.checkTokenAccess("isAuthenticated()");
        /**************filter链添加ClientCredentialsTokenEndpointFilter处理器**************/
        security.allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        super.configure(clients);
        clients.inMemory().withClient("admin").authorizedGrantTypes("password").secret(passwordEncoder.encode("123456")).accessTokenValiditySeconds(60);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints.tokenServices(tokenService()).tokenEnhancer((accessToken, authentication) -> {
            HashMap<String, Object> additionlaMap = new HashMap<>();
            additionlaMap.put("ext", "rose");
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionlaMap);
            return accessToken;
        }).authenticationManager(authenticationManager);
    }

    @Bean("roseTokenServices")
    public DefaultTokenServices tokenService() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(redisTokenStore);
        return tokenServices;
    }

    @Bean
    @Primary
    public TokenStore redisTokenStore(RedisConnectionFactory factory) {

        return new RedisTokenStore(factory);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
