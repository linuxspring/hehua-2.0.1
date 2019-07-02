package com.hehua.plugin.system.service.Impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hehua.plugin.system.mapper.MenuMapper;
import com.hehua.plugin.system.model.Plat_Menu;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/4/6.
 * IntelliJ IDEA 2018 of gzcss
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Plat_Menu> {
    public String findByMenu(String name) {
        this.baseMapper.findByAccount(name);
        return null;
    }
}
