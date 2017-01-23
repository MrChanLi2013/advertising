package com.ad.service;

import com.ad.dao.AdminUserDao;
import com.ad.entity.AdminUser;
import com.ad.entity.Role;
import com.ad.entity.RoleName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class AdminUserDetailService implements UserDetailsService {
    @Autowired
    private AdminUserDao adminUserDao;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        AdminUser adminUser = adminUserDao.findByUserName(name);
        if (adminUser == null) {
            throw new UsernameNotFoundException("未找到name为" + name + "的用户。");
        }
        return new User(adminUser.getUserName(),
                adminUser.getShaPassword(),
                true, true, true, true, getAuthorities(adminUser));
    }

    public List<GrantedAuthority> getAuthorities(AdminUser adminUser) {
        List<GrantedAuthority> authList = new ArrayList();
        Set<Role> roles = adminUser.getRoles();
        for (Role role : roles) {
            if (role.getRoleName().equals(RoleName.admin)) {
                authList.add(new SimpleGrantedAuthority("ADMIN"));
            }
        }
        return authList;
    }
}
