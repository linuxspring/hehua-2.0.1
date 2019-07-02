package com.hehua.plugin.system.model;

import com.baomidou.mybatisplus.annotations.*;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("PLAT_ROLE")
@KeySequence("SEQ_ROLE_ID")
public class Plat_Role extends SuperEntity<Plat_Role>{

	private static final long serialVersionUID = 1L;
	public String autoid;
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@TableId(value = "ID", type = IdType.INPUT)
	private Long id;

	@TableField("ROLENAME")
	private String roleName;
	
	private String title;
	
	private String description;

	@TableField("ISADMIN")
	private boolean isAdmin;
	@TableField("ISROOT")
	private boolean isRoot;

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean admin) {
		isAdmin = admin;
	}

	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean root) {
		isRoot = root;
	}

	@Version
	public Integer version;
	
//	@JsonIgnore
//	private Collection<User> users;
//
//	private Collection<Permission> pmss;
	
	

	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
//	public Collection<User> getUsers() {
//		return users;
//	}
//	public void setUsers(Collection<User> users) {
//		this.users = users;
//	}
//	public Collection<Permission> getPmss() {
//		return pmss;
//	}
//	public void setPmss(Collection<Permission> pmss) {
//		this.pmss = pmss;
//	}
}
