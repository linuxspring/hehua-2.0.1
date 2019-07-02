package com.hehua.plugin.system.view;


import com.hehua.plugin.system.model.Plat_Menu;
import com.hehua.plugin.system.model.Plat_User;

import java.util.List;

/**
 * Created by Administrator on 2018/5/29.
 * IntelliJ IDEA 2018 of gzcss
 */
public class UserLoginInfo {
    public Plat_User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(Plat_User userInfo) {
        this.userInfo = userInfo;
    }

    private Plat_User userInfo;

    public List<Plat_Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Plat_Menu> menus) {
        this.menus = menus;
    }

    private List<Plat_Menu> menus;
   // public List<MZDict> dict = new ArrayList<>();
    public boolean isAdmin;
    public boolean isRoot;
}
