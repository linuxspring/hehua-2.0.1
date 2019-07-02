package com.hehua.plugin.role.user.mapper;

import com.hehua.plugin.role.user.entity.User;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/4/23.
 * IntelliJ IDEA 2019 of gzcss
 */
public interface UserMapper{

    @Select("select * from user where id=#{0}")
    User selectById(Serializable id);
}
