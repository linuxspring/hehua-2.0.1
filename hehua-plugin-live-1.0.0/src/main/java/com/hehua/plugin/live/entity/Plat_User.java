package com.hehua.plugin.user.entity;

import com.baomidou.mybatisplus.annotations.*;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;


@TableName("PLAT_USER")
@KeySequence("SEQ_USER_ID")
public class Plat_User extends SuperEntity<Plat_User> {

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @TableId(value = "USER_ID", type = IdType.INPUT)
    public Long id;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;

    @TableField("PASSWORD")
    private String password;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    private String fullname;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String email;

    public String address;
    public String postcode;
    public String fax;
    public String description;
    @TableField("USER_STATE")
    public int userState;

    @TableField("USER_KEY")
    public String userKey;
    @TableField("isdeleted")
    public int isDeleted;
    public int gender;
    public int comfirmMethod;
    public String idcardNo;
    public Date regTime;
    public String officeTel;
    public Date blockUpTime;
    public int isStartUp;
    public long loginnum;
    public Date createtime;
    public String ulevel;
    @TableField("TYPE")
    public int type;
    public String tel;
    public String tel2;
    public String sign;
    public Date lastupdate;
    public String cnname;
    public String qq;
    @TableField("POSITION")
    public String position;
    public String link;
    public int age;
    public String autoid;
    public long comid;
    public String wx;

//    private transient TokenBean token;
//
//    public TokenBean getToken() {
//        return token;
//    }
//
//    public void setToken(TokenBean token) {
//        this.token = token;
//    }




    @Version
    public Integer version;

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
