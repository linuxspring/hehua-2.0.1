package com.hehua.plugin.system.model;

import com.baomidou.mybatisplus.annotations.*;
import com.baomidou.mybatisplus.enums.IdType;
import util.TokenBean;

import java.io.Serializable;


@TableName("PLAT_JDBC")
@KeySequence("SEQ_USER_ID")
public class Plat_Jdbc extends SuperEntity<Plat_Jdbc> {

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

  

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



  
    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	private String url;
    private String driver;
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

  
    public String getAutoid() {
        return autoid;
    }

    public void setAutoid(String autoid) {
        this.autoid = autoid;
    }

    public String autoid;
    
	private transient TokenBean token;

	public TokenBean getToken() {
		return token;
	}

	public void setToken(TokenBean token) {
		this.token = token;
	}




    @Version
    public Integer version;

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
