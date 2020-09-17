package com.springboot.jwt.mapper;

import com.springboot.jwt.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {

    @Select("select * from ums_admin where username=#{username}")
    User findByUserName(@Param("username") String username);
}
