package com.springboot.jwt.service;

import com.springboot.jwt.entity.User;
import com.springboot.jwt.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义一个UserDetailService ，来被Spring Security发现并使用，通过Spring IOC 容器
 */
@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中读取该用户
        User user = userMapper.findByUserName(username);

        // 用户不存在
        if(user == null){
            throw new UsernameNotFoundException("用户不存在");
        }

        // 将数据库形式的roles 解析为UserDetails 的权限集
        // AuthorityUtils.commaSeparatedStringToAuthorityList 为Spring Security提供
        // 用于将逗号隔开的权限字符串切割成可用的权限对象列表
        user.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles()));

       // return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),user.getAuthorities());
        return user;
    }

    private List<GrantedAuthority> generateAuthorities(String roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        String[] roleArray = roles.split(";");
        if (roles != null && !"".equals(roles)) {
            for (String role : roleArray) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
        }

        return authorities;
    }
}
