package com.hehua.plugin.role.alarm.entity;


import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * @author admin
 * @desc
 * @createtime 2019年04月02日 10:45
 * @project itmap
 */
@TableName("T_ALARM_INFO")
public class AlarmInfoEntity extends Model<AlarmInfoEntity> {

    /**告警编号*/
    private Long eventid;
    /**外键关联告警级别表*/
    private Integer levelId;
    /**外键关联告警状态表*/
    private Integer statusId;
    /**类型编号*/
    private Integer typeId;
    /**告警发生时间*/
    private java.util.Date creattime;
    /**相同内容的告警最后出现时间，默认和发生时间一致*/
    private java.util.Date endtime;
    /**告警标题*/
    private String title;
    /**告警内容*/
    private String content;
    /**翻译后内容*/
    private String translateContent;
    /**累积次数*/
    private Integer totalNumber;
    /**告警处理时间*/
    private java.util.Date treattime;
    /**告警处理人*/
    private String treatname;
    /**告警源IP*/
    private String sourceip;
    /**告警目的IP地址*/
    private String purposeip;
    /**配置项编码*/
    private String ciCode;
    /**业务系统编号*/
    private String busCiCode;
    /**设备类型编号*/
    private String ciTypeId;
    /**1.脆弱性；2.威胁；3.门禁；4温度；5湿度；6烟感；7浸水
     */
    private String bigTypename;
    /**告警小类名称*/
    private String smallTypename;
    /**告警处理意见*/
    private String advice;
    /**1软件告警；2硬件告警*/
    private String propertyType;
    /**告警对象名称，如端口名称、cpu、内存、信号灯等*/
    private String normName;
    /**告警对象是否可用：1.可用；2不可用；默认为1 告警对象如：端口、cpu、信号灯、业务系统等*/
    private String indicatorStatus;
    /**事件所属大类*/
    private String className;
    /**事件所属子类*/
    private String subClassName;
    /**告警发生端口*/
    private String port;
    /**1.惠普ovo；2soc；3.监控平台；4.惠普bac；5惠普nnm；6.机房监控系统；*/
    private String alarmFrom;
    /**相同内容的告警最后出现时间，默认和发生时间一致*/
    private java.util.Date insertTime;
    /**告警知识库ID*/
    private String knowledgeId;
    /**源告警级别表(告警升级或者降级之前的级别)*/
    private Integer sourceLevelId;
    /**更新时间*/
    @TableField("UPDATE_TIME")
    private java.util.Date lastupdate;
    /**isdeleted*/
    private Integer isdeleted;
    /**collecttime*/
    private java.util.Date collecttime;
    /**id*/
    @TableId(value = "ID", type = IdType.UUID)
    private String id;

    /**
     *方法: 取得java.lang.Integer
     *@return: java.lang.Integer  告警编号
     */
    public Long getEventid(){
        return this.eventid;
    }

    /**
     *方法: 设置java.lang.Integer
     *@param: java.lang.Integer  告警编号
     */
    public void setEventid(Long eventid){
        this.eventid = eventid;
    }
    /**
     *方法: 取得java.lang.Integer
     *@return: java.lang.Integer  外键关联告警级别表
     */
    public Integer getLevelId(){
        return this.levelId;
    }

    /**
     *方法: 设置java.lang.Integer
     *@param: java.lang.Integer  外键关联告警级别表
     */
    public void setLevelId(Integer levelId){
        this.levelId = levelId;
    }
    /**
     *方法: 取得java.lang.Integer
     *@return: java.lang.Integer  外键关联告警状态表
     */
    public Integer getStatusId(){
        return this.statusId;
    }

    /**
     *方法: 设置java.lang.Integer
     *@param: java.lang.Integer  外键关联告警状态表
     */
    public void setStatusId(Integer statusId){
        this.statusId = statusId;
    }
    /**
     *方法: 取得java.lang.Integer
     *@return: java.lang.Integer  类型编号
     */
    public Integer getTypeId(){
        return this.typeId;
    }

    /**
     *方法: 设置java.lang.Integer
     *@param: java.lang.Integer  类型编号
     */
    public void setTypeId(Integer typeId){
        this.typeId = typeId;
    }
    /**
     *方法: 取得java.util.Date
     *@return: java.util.Date  告警发生时间
     */
    public java.util.Date getCreattime(){
        return this.creattime;
    }

    /**
     *方法: 设置java.util.Date
     *@param: java.util.Date  告警发生时间
     */
    public void setCreattime(java.util.Date creattime){
        this.creattime = creattime;
    }
    /**
     *方法: 取得java.util.Date
     *@return: java.util.Date  相同内容的告警最后出现时间，默认和发生时间一致
     */
    public java.util.Date getEndtime(){
        return this.endtime;
    }

    /**
     *方法: 设置java.util.Date
     *@param: java.util.Date  相同内容的告警最后出现时间，默认和发生时间一致
     */
    public void setEndtime(java.util.Date endtime){
        this.endtime = endtime;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  告警标题
     */
    public String getTitle(){
        return this.title;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  告警标题
     */
    public void setTitle(String title){
        this.title = title;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  告警内容
     */
    public String getContent(){
        return this.content;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  告警内容
     */
    public void setContent(String content){
        this.content = content;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  翻译后内容
     */
    public String getTranslateContent(){
        return this.translateContent;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  翻译后内容
     */
    public void setTranslateContent(String translateContent){
        this.translateContent = translateContent;
    }
    /**
     *方法: 取得java.lang.Integer
     *@return: java.lang.Integer  累积次数
     */
    public Integer getTotalNumber(){
        return this.totalNumber;
    }

    /**
     *方法: 设置java.lang.Integer
     *@param: java.lang.Integer  累积次数
     */
    public void setTotalNumber(Integer totalNumber){
        this.totalNumber = totalNumber;
    }
    /**
     *方法: 取得java.util.Date
     *@return: java.util.Date  告警处理时间
     */
    public java.util.Date getTreattime(){
        return this.treattime;
    }

    /**
     *方法: 设置java.util.Date
     *@param: java.util.Date  告警处理时间
     */
    public void setTreattime(java.util.Date treattime){
        this.treattime = treattime;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  告警处理人
     */
    public String getTreatname(){
        return this.treatname;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  告警处理人
     */
    public void setTreatname(String treatname){
        this.treatname = treatname;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  告警源IP
     */
    public String getSourceip(){
        return this.sourceip;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  告警源IP
     */
    public void setSourceip(String sourceip){
        this.sourceip = sourceip;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  告警目的IP地址
     */
    public String getPurposeip(){
        return this.purposeip;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  告警目的IP地址
     */
    public void setPurposeip(String purposeip){
        this.purposeip = purposeip;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  配置项编码
     */
    public String getCiCode(){
        return this.ciCode;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  配置项编码
     */
    public void setCiCode(String ciCode){
        this.ciCode = ciCode;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  业务系统编号
     */
    public String getBusCiCode(){
        return this.busCiCode;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  业务系统编号
     */
    public void setBusCiCode(String busCiCode){
        this.busCiCode = busCiCode;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  设备类型编号
     */
    public String getCiTypeId(){
        return this.ciTypeId;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  设备类型编号
     */
    public void setCiTypeId(String ciTypeId){
        this.ciTypeId = ciTypeId;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  1.脆弱性；2.威胁；3.门禁；4温度；5湿度；6烟感；7浸水

     */
    public String getBigTypename(){
        return this.bigTypename;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  1.脆弱性；2.威胁；3.门禁；4温度；5湿度；6烟感；7浸水

     */
    public void setBigTypename(String bigTypename){
        this.bigTypename = bigTypename;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  告警小类名称
     */
    public String getSmallTypename(){
        return this.smallTypename;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  告警小类名称
     */
    public void setSmallTypename(String smallTypename){
        this.smallTypename = smallTypename;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  告警处理意见
     */
    public String getAdvice(){
        return this.advice;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  告警处理意见
     */
    public void setAdvice(String advice){
        this.advice = advice;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  1软件告警；2硬件告警
     */
    public String getPropertyType(){
        return this.propertyType;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  1软件告警；2硬件告警
     */
    public void setPropertyType(String propertyType){
        this.propertyType = propertyType;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  告警对象名称，如端口名称、cpu、内存、信号灯等
     */
    public String getNormName(){
        return this.normName;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  告警对象名称，如端口名称、cpu、内存、信号灯等
     */
    public void setNormName(String normName){
        this.normName = normName;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  告警对象是否可用：1.可用；2不可用；默认为1
    告警对象如：端口、cpu、信号灯、业务系统等
     */
    public String getIndicatorStatus(){
        return this.indicatorStatus;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  告警对象是否可用：1.可用；2不可用；默认为1
    告警对象如：端口、cpu、信号灯、业务系统等
     */
    public void setIndicatorStatus(String indicatorStatus){
        this.indicatorStatus = indicatorStatus;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  事件所属大类
     */
    public String getClassName(){
        return this.className;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  事件所属大类
     */
    public void setClassName(String className){
        this.className = className;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  事件所属子类
     */
    public String getSubClassName(){
        return this.subClassName;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  事件所属子类
     */
    public void setSubClassName(String subClassName){
        this.subClassName = subClassName;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  告警发生端口
     */
    public String getPort(){
        return this.port;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  告警发生端口
     */
    public void setPort(String port){
        this.port = port;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  1.惠普ovo；2soc；3.监控平台；4.惠普bac；5惠普nnm；6.机房监控系统；
     */
    public String getAlarmFrom(){
        return this.alarmFrom;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  1.惠普ovo；2soc；3.监控平台；4.惠普bac；5惠普nnm；6.机房监控系统；
     */
    public void setAlarmFrom(String alarmFrom){
        this.alarmFrom = alarmFrom;
    }
    /**
     *方法: 取得java.util.Date
     *@return: java.util.Date  相同内容的告警最后出现时间，默认和发生时间一致
     */
    public java.util.Date getInsertTime(){
        return this.insertTime;
    }

    /**
     *方法: 设置java.util.Date
     *@param: java.util.Date  相同内容的告警最后出现时间，默认和发生时间一致
     */
    public void setInsertTime(java.util.Date insertTime){
        this.insertTime = insertTime;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  告警知识库ID
     */
    public String getKnowledgeId(){
        return this.knowledgeId;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  告警知识库ID
     */
    public void setKnowledgeId(String knowledgeId){
        this.knowledgeId = knowledgeId;
    }
    /**
     *方法: 取得java.lang.Integer
     *@return: java.lang.Integer  源告警级别表(告警升级或者降级之前的级别)
     */
    public Integer getSourceLevelId(){
        return this.sourceLevelId;
    }

    /**
     *方法: 设置java.lang.Integer
     *@param: java.lang.Integer  源告警级别表(告警升级或者降级之前的级别)
     */
    public void setSourceLevelId(Integer sourceLevelId){
        this.sourceLevelId = sourceLevelId;
    }
    /**
     *方法: 取得java.util.Date
     *@return: java.util.Date  更新时间
     */
    public java.util.Date getLastupdate(){
        return this.lastupdate;
    }

    /**
     *方法: 设置java.util.Date
     *@param: java.util.Date  更新时间
     */
    public void setLastupdate(java.util.Date lastupdate){
        this.lastupdate = lastupdate;
    }
    /**
     *方法: 取得java.lang.Integer
     *@return: java.lang.Integer  isdeleted
     */
    public Integer getIsdeleted(){
        return this.isdeleted;
    }

    /**
     *方法: 设置java.lang.Integer
     *@param: java.lang.Integer  isdeleted
     */
    public void setIsdeleted(Integer isdeleted){
        this.isdeleted = isdeleted;
    }
    /**
     *方法: 取得java.util.Date
     *@return: java.util.Date  collecttime
     */
    public java.util.Date getCollecttime(){
        return this.collecttime;
    }

    /**
     *方法: 设置java.util.Date
     *@param: java.util.Date  collecttime
     */
    public void setCollecttime(java.util.Date collecttime){
        this.collecttime = collecttime;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  id
     */
    public String getId(){
        return this.id;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  id
     */
    public void setId(String id){
        this.id = id;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
