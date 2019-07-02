package com.hehua.plugin.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hehua.plugin.system.model.Plat_Menu;
import com.hehua.plugin.system.model.Plat_User;
import org.apache.ibatis.annotations.Select;


public interface MenuMapper extends BaseMapper<Plat_Menu> {
	
	int countUser();

	@Select("getList")
	String getList(int index, int size);
	
	Plat_User findByAccount(String account);
	
	Plat_User findUserWithRoles(String account);
	
	
	void updateUser(Plat_User user);
	
	void delete(Object id);
	
	Integer insert(Plat_User user);
	
}
