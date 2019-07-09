package com.baeldung.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Component
public class DecidePermissionService {

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    public boolean decide(HttpServletRequest request, Authentication authentication) {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Boolean flag = false;

        String requestURI = request.getRequestURI().replace(request.getContextPath(), "");
        if (authorities != null) {
            for (GrantedAuthority authority : authorities) {
                if (pathMatcher.match(authority.getAuthority(), requestURI)) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }
}
