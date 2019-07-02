package com.hehua.plugin.system.model;

import com.baomidou.mybatisplus.annotations.*;
import com.baomidou.mybatisplus.enums.IdType;
@TableName("PLAT_RULE")
@KeySequence("SEQ_RULE_ID")
public class Plat_Rule extends SuperEntity<Plat_Rule>{

	private static final long serialVersionUID = 1L;

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

	@TableField("RULENAME")
	private String ruleName;

	@TableField("roleid")
	private Long roleid;
	@TableField("FUNIDS")
	private String funIds;
	@TableField("MENUIDS")
	private String menuIds;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDictIds() {
		return dictIds;
	}

	public void setDictIds(String dictIds) {
		this.dictIds = dictIds;
	}

	public String getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}

	public String getFunIds() {
		return funIds;
	}

	public void setFunIds(String funIds) {
		this.funIds = funIds;
	}

	public Long getRoleid() {
		return roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	@TableField("DICTIDS")
	private String dictIds;
	private String description;

	@Version
	public Integer version;

	@TableField("isdeleted")
	public int isDeleted;
	public String autoid;
}
