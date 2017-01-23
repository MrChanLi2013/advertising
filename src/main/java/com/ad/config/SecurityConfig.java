package com.ad.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

@EnableWebMvcSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/admin/login", "/ad/**",
                        "/admin/logout","/ad/error",
                        "/js/**", "/images/**", "/fonts/**", "/css/**").permitAll()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .anyRequest().fullyAuthenticated()
                .and()
                .formLogin()
                .loginPage("/admin/login")
                .defaultSuccessUrl("/admin/index")
                .failureUrl("/ad/error")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .permitAll()
                .and()
                .sessionManagement()
                .maximumSessions(1)
                .expiredUrl("/?error=1")
                .sessionRegistry(sessionRegistry())
                .maxSessionsPreventsLogin(false);

        http.csrf().requireCsrfProtectionMatcher(csrfMatcher());
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    private RequestMatcher csrfMatcher() {
        return new RequestMatcher() {
            private AntPathRequestMatcher[] requestMatcher = {
                    new AntPathRequestMatcher("/admin/login"),
                    new AntPathRequestMatcher("/ad/**"),
            };

            @Override
            public boolean matches(HttpServletRequest request) {
                if (request.getMethod().matches("^GET$") ||
                        request.getMethod().matches("^POST")) {
                    return false;
                }
                for (AntPathRequestMatcher rm : requestMatcher) {
                    if (rm.matches(request)) {
                        return false;
                    }
                }
                return true;
            }
        };
    }

}
