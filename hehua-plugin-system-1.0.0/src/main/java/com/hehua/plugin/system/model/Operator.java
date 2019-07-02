package com.hehua.plugin.system.model;

import util.TokenBean;

import java.io.Serializable;
import java.util.Date;

/**
 * @Project: 肇庆微信公众号
 * @author pengguojin
 * @desc 用户实体类
 * @createTime 2017-09-01
 *
 */
public class Operator implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 账号ID.
	 */
	private String name;
	/**
	 * 用户名称.
	 */
	private String fullName;
	/**
	 * 联系人ID.
	 */
	private String contactName;
	/**
	 * 用户所属组.
	 */
	private String assignmentGroups;
	/**
	 * 密码.
	 */
	private String password;
	/**
	 * 用户角色_三线支持.
	 */
	private String userRole;

	private String deptId;

	private String dept;

	private String deptStructure;

	private String title;

	private String loginRevoked;

	private String cssStatus;

	private String cLoginRevoked;

	private Date cLoginRevokedTime;

	private String telPhone;

	private String positionSwitch;

	private TokenBean token;

	private String cityCode;

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getcLoginRevoked() {
		return cLoginRevoked;
	}

	public void setcLoginRevoked(String cLoginRevoked) {
		this.cLoginRevoked = cLoginRevoked;
	}

	public Date getcLoginRevokedTime() {
		return cLoginRevokedTime;
	}

	public void setcLoginRevokedTime(Date cLoginRevokedTime) {
		this.cLoginRevokedTime = cLoginRevokedTime;
	}

	public String getCssStatus() {
		return cssStatus;
	}

	public void setCssStatus(String cssStatus) {
		this.cssStatus = cssStatus;
	}

	public final String getUserRole() {
		return userRole;
	}

	public final void setUserRole(final String userRole) {
		this.userRole = userRole;
	}

	public final String getName() {
		return name;
	}

	public final void setName(final String name) {
		this.name = name;
	}

	public final String getPassword() {
		if (password == null) {
			return "";
		} else {
			return password;
		}
	}

	public final void setPassword(final String password) {
		this.password = password;
	}

	public final String getFullName() {
		return fullName;
	}

	public final void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	public final String getAssignmentGroups() {
		return assignmentGroups;
	}

	public final void setAssignmentGroups(final String assignmentGroups) {
		this.assignmentGroups = assignmentGroups;
	}

	public final String getContactName() {
		return contactName;
	}

	public final void setContactName(final String contactName) {
		this.contactName = contactName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getDeptStructure() {
		return deptStructure;
	}

	public void setDeptStructure(String deptStructure) {
		this.deptStructure = deptStructure;
	}

	public String getTitle() {
		if (title == null)
			return "";
		else
			return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLoginRevoked() {
		return loginRevoked;
	}

	public void setLoginRevoked(String loginREvoked) {
		this.loginRevoked = loginREvoked;
	}

	public String getPositionSwitch() {
		return positionSwitch;
	}

	public void setPositionSwitch(String positionSwitch) {
		this.positionSwitch = positionSwitch;
	}

	public TokenBean getToken() {
		return token;
	}

	public void setToken(TokenBean token) {
		this.token = token;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

}
