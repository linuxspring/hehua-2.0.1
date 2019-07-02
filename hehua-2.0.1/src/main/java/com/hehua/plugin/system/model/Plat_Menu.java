package com.hehua.plugin.system.model;

/**
 * Created by Administrator on 2017/7/21.
 * IntelliJ IDEA 2017 of gzcss
 */

import com.baomidou.mybatisplus.annotations.*;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;


@TableName("PLAT_MENU")
@SuppressWarnings("serial")
@KeySequence("SEQ_MENU_ID")
public class Plat_Menu extends SuperEntity<Plat_Menu>{

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.INPUT)
    private int id;
    private int pid;
    @TableField("NAME")
    private String name;

    private String link;

    private String menukey;



    @TableField("SORT_INDEX")
    public int sortIndex;

    @TableField("TYPE")
    private int type;
    private Date createtime;

    @TableField("ICONCLS")
    private String iconCls="fa fa-rocket";


    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMenukey() {
        return menukey;
    }

    public void setMenukey(String menukey) {
        this.menukey = menukey;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Version
    public Integer version;

}
