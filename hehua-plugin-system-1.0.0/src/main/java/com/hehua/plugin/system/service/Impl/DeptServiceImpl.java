package com.hehua.plugin.system.service.Impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hehua.plugin.system.mapper.DeptMapper;
import com.hehua.plugin.system.model.Plat_Dept;
import com.hehua.plugin.system.model.Plat_User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/6.
 * IntelliJ IDEA 2018 of gzcss
 */
@Service("deptService")
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Plat_Dept> {
    public String findByMenu(String name) {
        this.baseMapper.findByAccount(name);
        return null;
    }

    public List<Plat_User> getDeptUsersView(Page<Plat_User> page, Map<String, Object> condition){
        return this.baseMapper.getDeptUsersView(page, condition);
    }
    public int addUsersToDept(Map<String, Object> condition){
        return this.baseMapper.addUsersToDept(condition);
    }

    public int removeUsersFromDept(Map<String, Object> condition){
        return this.baseMapper.removeUsersFromDept(condition);
    }
}
