package com.hehua.plugin.system.model;

/**
 * Created by Administrator on 2017/7/21.
 * IntelliJ IDEA 2017 of gzcss
 */

import com.baomidou.mybatisplus.annotations.*;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;


@TableName("PLAT_DEPT")
@SuppressWarnings("serial")
@KeySequence("SEQ_DEPT_ID")
public class Plat_Dept extends SuperEntity<Plat_Dept>{

    private static final long serialVersionUID = 1L;

    @TableId(value="DEPT_ID",type= IdType.INPUT)
    private Long id;
    private Long pid;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @TableField("DEPT_NAME")
    private String deptName;

    public String getAutoid() {
        return autoid;
    }

    public void setAutoid(String autoid) {
        this.autoid = autoid;
    }

    public String autoid;

    private String manager;
    private String description;
    private int dept_type;
    @TableField("isdeleted")
    public int isDeleted;

    private String tel;
    //@TableField("TYPE")
    //private int type;

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public int getDept_type() {
        return dept_type;
    }

    public void setDept_type(int dept_type) {
        this.dept_type = dept_type;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }


    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    private Date createtime=new Date();

    @Version
    public Integer version;
}
