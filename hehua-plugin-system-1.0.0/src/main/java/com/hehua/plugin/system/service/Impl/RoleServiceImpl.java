package com.hehua.plugin.system.service.Impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hehua.plugin.system.mapper.RoleMapper;
import com.hehua.plugin.system.model.Plat_Role;
import com.hehua.plugin.system.model.Plat_User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/6.
 * IntelliJ IDEA 2018 of gzcss
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Plat_Role> {
    public String findByMenu(String name) {
        this.baseMapper.findByAccount(name);
        return null;
    }

    public List<Plat_User> getRoleUsersView(Page<Plat_User> page, Map<String, Object> condition){
       return this.baseMapper.getRoleUsersView(page, condition);
    }
    public int addUsersToRole(Map<String, Object> condition){
       return this.baseMapper.addUsersToRole(condition);
    }

    public int removeUsersFromRole(Map<String, Object> condition){
       return this.baseMapper.removeUsersFromRole(condition);
    }
}
