package com.hehua.plugin.system.vo;

public class SurveyVo {
	private String incident_id;
	private String resolve_efficiency;//解决效率5~1
	private String serve_attitude;//服务态度
	private String  skill;//解决结果
	private String contentment;//总体满意度
	private String returnidea;//反馈意见
	private String surveyid;
	
	public String getIncident_id() {
		return incident_id;
	}
	public void setIncident_id(String incident_id) {
		this.incident_id = incident_id;
	}
	public String getResolve_efficiency() {
		return resolve_efficiency;
	}
	public void setResolve_efficiency(String resolve_efficiency) {
		this.resolve_efficiency = resolve_efficiency;
	}
	public String getServe_attitude() {
		return serve_attitude;
	}
	public void setServe_attitude(String serve_attitude) {
		this.serve_attitude = serve_attitude;
	}
	public String getSkill() {
		return skill;
	}
	public void setSkill(String skill) {
		this.skill = skill;
	}
	public String getContentment() {
		return contentment;
	}
	public void setContentment(String contentment) {
		this.contentment = contentment;
	}
	public String getReturnidea() {
		return returnidea;
	}
	public void setReturnidea(String returnidea) {
		this.returnidea = returnidea;
	}
	public String getSurveyid() {
		return surveyid;
	}
	public void setSurveyid(String surveyid) {
		this.surveyid = surveyid;
	}
}
