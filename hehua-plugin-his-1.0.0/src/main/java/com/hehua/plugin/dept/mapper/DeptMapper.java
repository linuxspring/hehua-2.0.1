package com.hehua.plugin.dept.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hehua.plugin.dept.entity.Plat_Dept;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author admin
 * @desc
 * @createtime 2019年04月02日 19:20
 * @project bigdatacenter_cmdb
 */

public interface DeptMapper extends BaseMapper<Plat_Dept> {

    int countUser();

    @Select("getList")
    String getList(int index, int size);

    Plat_User findByAccount(String account);

    Plat_User findUserWithRoles(String account);


    void updateUser(Plat_User user);

    void delete(Object id);

    Integer insert(Plat_User user);

    List<Plat_User> getDeptUsersView(Page<Plat_User> page, Map<String, Object> condition);

    int addUsersToDept(Map<String, Object> condition);
    int removeUsersFromDept(Map<String, Object> condition);
}
