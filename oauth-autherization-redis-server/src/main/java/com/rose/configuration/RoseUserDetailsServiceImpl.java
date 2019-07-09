package com.rose.configuration;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class RoseUserDetailsServiceImpl implements UserDetailsService {

    public static ImmutableMap<String, UserInfo> map;

    static{

        ImmutableMap.Builder<String, UserInfo> builder = ImmutableMap.builder();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("/bars");
        builder.put("rose", new UserInfo().builder().username("rose").password(new BCryptPasswordEncoder().encode("123456")).authorities(Lists.newArrayList(authority)).build());
        map = builder.build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserInfo userInfo = map.get(username);
        if (userInfo != null) {

            return new User(userInfo.getUsername(), userInfo.getPassword(), userInfo.getAuthorities());
        }
        return null;
    }
}
