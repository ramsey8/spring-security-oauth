package com.baeldung.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class TokenController {

    @Autowired
    private ConsumerTokenServices tokenServices;

    @RequestMapping("/oauth/revoke")
    public String revokeToken(Principal principal) {

        if (principal instanceof OAuth2Authentication) {
            String accessToken = ((OAuth2AuthenticationDetails) (((OAuth2Authentication) principal).getDetails())).getTokenValue();
            tokenServices.revokeToken(accessToken);
            return "success";
        }

        return "failure";
    }
}

