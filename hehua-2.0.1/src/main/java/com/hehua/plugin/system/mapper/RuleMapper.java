package com.hehua.plugin.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hehua.plugin.system.model.Plat_Rule;
import com.hehua.plugin.system.model.Plat_User;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface RuleMapper extends BaseMapper<Plat_Rule> {
	
	int countUser();

	@Select("getList")
	String getList(int index, int size);
	
	Plat_User findByAccount(String account);
	
	Plat_User findUserWithRoles(String account);
	
	
	void updateUser(Plat_User user);
	
	void delete(Object id);
	
	Integer insert(Plat_User user);
	List<Plat_User> getRoleUsersView(Page<Plat_User> page, Map<String, Object> condition);

	int addUsersToRole(Map<String, Object> condition);
	int removeUsersFromRole(Map<String, Object> condition);
}
