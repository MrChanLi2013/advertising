package com.ad.config;

import com.ad.service.AdminUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    @Autowired
    private AdminUserDetailService adminUserDetailService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken auth) throws AuthenticationException {
        String password = (String) auth.getCredentials();
        UserDetails userDetails = adminUserDetailService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException("未找到name为" + username + "的用户。");
        }
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(userDetails.getPassword())) {
            throw new BadCredentialsException("用户名或密码不正确。");
        }
        return userDetails;
    }
}
