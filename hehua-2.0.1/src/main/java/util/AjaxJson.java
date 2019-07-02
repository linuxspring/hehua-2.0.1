package util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import java.util.Map;

/**
 * $.ajax后需要接受的JSON
 * 
 * @author
 * 
 */
public class AjaxJson {

	private boolean success = true;// 是否成功
	private String msg = "操作成功";// 提示信息
	private Object obj = null;// 其他信息
	private Map<String, Object> attributes;// 其他参数

	/**
	 * cairuoyu 20161009 增加构造函数
	 * 
	 * @param msg
	 */
	public AjaxJson(String msg) {
		this.msg = msg;
	}

	public AjaxJson(boolean success, String msg) {
		this.success = success;
		this.msg = msg;
	}

	public AjaxJson() {
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getJsonStr() {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor());
		JSONObject obj = new JSONObject();
		obj.put("success", this.isSuccess());
		obj.put("msg", this.getMsg());
		if (this.obj != null) {
			obj.put("obj", JSONArray.fromObject(this.obj, jsonConfig));
		} else {
			obj.put("obj", "");
		}
		obj.put("attributes", this.attributes);
		return obj.toString();
	}

	public String toJson() {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor());
		jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new DateJsonValueProcessor());
		String json = "";
		if (this.obj != null && (this.obj instanceof Iterable || this.obj.getClass().isArray())) {
			JSONArray obj = JSONArray.fromObject(this.obj, jsonConfig);
			json = obj.toString();
		} else {
			JSONObject obj = JSONObject.fromObject(this.obj, jsonConfig);
			json = obj.toString();
		}
		return json;
	}

	public String toJson(Object o) {
		PageResult pr = (PageResult) o;
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor());
		jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new DateJsonValueProcessor());
		String json = "";
		if (this.obj != null && (this.obj instanceof Iterable || this.obj.getClass().isArray())) {
			JSONArray obj = JSONArray.fromObject(pr.getRows(), jsonConfig);
			json = obj.toString();
		} else {
			JSONArray obj = JSONArray.fromObject(pr.getRows(), jsonConfig);
			json = obj.toString();
		}

		return json;
	}
}
