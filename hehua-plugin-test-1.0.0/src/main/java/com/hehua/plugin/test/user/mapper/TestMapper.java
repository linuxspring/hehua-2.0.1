package com.hehua.plugin.test.user.mapper;

import com.hehua.plugin.test.user.entity.User;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/4/23.
 * IntelliJ IDEA 2019 of gzcss
 */
public interface TestMapper {

    @Select("select * from user where id=#{0}")
    User selectById(Serializable id);
}
