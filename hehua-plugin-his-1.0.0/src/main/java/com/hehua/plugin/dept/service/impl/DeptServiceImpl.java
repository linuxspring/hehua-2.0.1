package com.hehua.plugin.dept.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hehua.plugin.dept.entity.Plat_Dept;
import com.hehua.plugin.dept.mapper.DeptMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author admin
 * @desc
 * @createtime 2019年04月02日 19:40
 * @project AlarmInfoServiceImpl
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
