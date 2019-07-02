package com.hehua.plugin.user.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hehua.plugin.user.entity.Plat_User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/4/23.
 * IntelliJ IDEA 2019 of gzcss @Select("select * from user where id=#{0}")
 */
public interface UserMapper extends BaseMapper<Plat_User> {

    int countUser();

    // @Select("getList")
    String getList(int index, int size);

    @Select("getUserByName")
    Plat_User findByAccount(String account);
    @Select("getUserByrealFullName")
    String getUserByrealFullName(String account);

    Plat_User findUserWithRoles(String account);

    void updateUser(Plat_User user);

    void delete(Object id);

    Integer insert(Plat_User user);

    String getPwdByAccount(@Param("account") String account, @Param("schema") String schema);

    String getAcountByMapUserName(String username);

    String getQixinAcountByUserName(String username);

    AjaxJson getEventOrderStatus(String orderNo, String username);

    Map<String, Object> getOrderOwnerInfo(String orderNo);

    String accept(String usernameId, String number, String status);

    List<Plat_User> getRoleUsersView(Page<Plat_User> page, Map<String, Object> condition);

    List<Plat_User> getUserByFullName(@Param("keyword") String keyword);

    String getPwdByUser(@Param("account")String account);

    List<Plat_Role> getRolesByUserId(Long userid);

    List<Plat_Rule> getRulesByRoleids(Map<String, Object> roleids);

    List<Plat_Menu> getMenusByIds(Map<String, Object> ids);

    String getPwd(@Param("userName") String userName);



}