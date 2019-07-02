package com.hehua.plugin.system.service.Impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hehua.plugin.system.mapper.RuleMapper;
import com.hehua.plugin.system.model.Plat_Rule;
import com.hehua.plugin.system.model.Plat_User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/6.
 * IntelliJ IDEA 2018 of gzcss
 */
@Service("ruleService")
public class RuleServiceImpl extends ServiceImpl<RuleMapper, Plat_Rule> {
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
