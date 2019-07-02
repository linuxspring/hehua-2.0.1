//package com.hehua.plugin.system.controller;
//
//
//import com.hehua.plugin.system.service.Impl.ClientServiceImpl;
//import com.hehua.plugin.system.service.Impl.OAuthServiceImpl;
//import com.hehua.plugin.system.service.Impl.UserServiceImpl;
//import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.apache.shiro.subject.SimplePrincipalCollection;
//import org.apache.shiro.subject.Subject;
//import org.apache.shiro.util.ThreadContext;
//import org.apache.shiro.web.subject.WebSubject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Description;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.annotation.Resource;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.validation.Validator;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.UnsupportedEncodingException;
//import java.net.URISyntaxException;
//import java.net.URLEncoder;
//import java.util.*;
//import java.util.logging.Logger;
//
///**
// * Created by Administrator on 2017/10/25.
// * IntelliJ IDEA 2017 of gzcss
// */
//@Controller
//@RequestMapping("qxmsg")
//@SuppressWarnings("all")
//public class QiXinMessageController extends BaseController {
//
//    private static final Logger logger = Logger.getLogger(QiXinMessageController.class.getName());
//
//    @Autowired
//    private OAuthServiceImpl oAuthService;
//    @Autowired
//    private ClientServiceImpl clientService;
//
//    @Autowired
//    private ZQTodoListServiceI zqTodoListServiceI;
//
//    @Resource
//    private UserServiceImpl userService;
//
//    public UserServiceImpl getUserService() {
//        return userService;
//    }
//
//
//    @Autowired
//	private ZQOperatorServiceI operService;
//    @Autowired
//	private ZQOcmqServiceI ocmqService;
//
//    private static String _adminUser = "";
//    private static String _oauthUrl = "";
//    private static String _qxRedirectUrl = "";
//    private static int _isSend = 0;
//
//    static {
//        Thread t = new Thread(new Qixin_TokenThread());
//        //t.start();
//        _adminUser = PropertiesUtils.getProperty("APP_ID");
//        _isSend = Integer.parseInt(PropertiesUtils.getProperty("ISSEND"));
//        _oauthUrl = PropertiesUtils.getProperty("OAUTHURL");
//        String qxUrl = PropertiesUtils.getProperty("QXREDIRECT");
//        int len=qxUrl.lastIndexOf("redirect_uri");
//        if ( len> 0) {
//            len=qxUrl.lastIndexOf("=");
//            String a=qxUrl.substring(0,len+1);
//            String b = qxUrl.substring(len + 1).trim();
//            try {
//                String url = URLEncoder.encode(b, "UTF-8");
//                _qxRedirectUrl=a+url;
//                System.out.println("_qxRedirectUrl = " + _qxRedirectUrl);
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//        }
//        Constants.oauthUrl = PropertiesUtils.getProperty("OAUTHURL");
//    }
//
//    //@Autowired
//    //private SystemService systemService;
//
//    @Autowired
//    private Validator validator;
//
//    @RequestMapping(value = "evt.data", method = RequestMethod.GET)
//    @ResponseBody
//    public String evnetOrder(String number) {
//        AjaxJson ajaxJson = null;//alarmSearchService.getAlarmByCicode(cicode);
//        String message = null;
//        if (ajaxJson.isSuccess()) {
//            message = "取得告警列表信息成功";
//            //systemService.addLog(message, Globals.Log_Type_OTHER,Globals.Log_Leavel_INFO);
//        } else {
//            message = "取得告警列表信息失败";
//            //systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_ERROR);
//        }
//        String text = ajaxJson.getJsonStr();
//        return text;
//    }
//
//    @RequestMapping(value = "getUserInfoByCode.data", method = RequestMethod.POST)
//    @ResponseBody
//    public String getUserInfoByCode(Model model, String code, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        AjaxJson ajaxJsonAll = new AjaxJson();
//        Map<String, String> map = new HashMap<String, String>();
//        System.out.println("code = " + code);
//        String authCode = code;// request.getParameter("code");
//        AuthCodeGetInfoResponse userInfo = com.comtop.esp.api.client.SendMsgUtils.userInfo(authCode);
//        if (userInfo == null) {
//            logger.error("userInfo = 为空" );
//        }
//        logger.info("code换算企信用户成功" );
//        userInfo.getGender();//性别
//        userInfo.getOpenId();//openid
//        //userInfo.getUserName();//姓名
//        String qixinUserNameCn=userInfo.getName();
//        String qixinUserId=userInfo.getUserId();
//        userInfo.getAccount();//帐号
//        String tel=userInfo.getPhone();//手机号
//        logger.info("获取企信用户信息成功" );
//        String itsm_username = userService.getAcountByMapUserName(qixinUserId);
//
//        logger.info("匹配的用户为:"+itsm_username);
//
//        if ("030500".equals(PropertiesUtil.getProperty("config.cityCode"))) {
//            //智能管控用用户名匹配
//            tel = qixinUserNameCn;
//            logger.info("获取企信用户名字为："+tel );
//
//        }
//
//        if (StringUtils.isEmpty(itsm_username) && !StringUtils.isEmpty(tel)) {
//            itsm_username = userService.getItsmUserNameByTel(tel);
//            logger.info("获取登陆用户为："+itsm_username );
//            if (StringUtils.isEmpty(itsm_username)) {
//                if ("030500".equals(PropertiesUtil.getProperty("config.cityCode"))) {
//                    SendMsgUtils.sendTextByUserId("您在企信上填写的用户名"+tel+"与智能管控填写的用户名不一致", qixinUserId);
//                    logger.info("在智能管控未找到企信上填写的用户名："+tel);
//                }else{
//                    SendMsgUtils.sendTextByUserId("您在企信上填写的手机号"+tel+"与ITSM填写的手机号不一致", qixinUserId);
//                }
//            }else{
//                userService.addUserNameToQixin(itsm_username, qixinUserId);
//            }
//        }else{
//            logger.info("出错itsm_username："+itsm_username +",tel:"+tel );
//        }
//        map.put("username", itsm_username);
//        Object o = null;
//        try {
//            ajaxJsonAll.setMsg("login is ok");
//            o = loginByName(null, itsm_username, request, response);
//        } catch (OAuthSystemException e) {
//            e.printStackTrace();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//            ajaxJsonAll.setSuccess(false);
//            ajaxJsonAll.setMsg("login fail");
//        }
//        String url=request.getQueryString();
//        System.out.println("url = " + url);
//        String type = request.getParameter("getType");
//        String meurl = request.getParameter("meurl");
//        if (StringUtils.isEmpty(type) && StringUtils.isEmpty(meurl)) {
//            type="5";
//        }
//        String finalUrl=Constants.oauthUrl;
//        String orderNo =null;
//        if(Integer.valueOf(type)<4){
//            orderNo =request.getParameter("number");
//            Map<String,Object> orderCurrentInfo=userService.getOrderCurrentInfo(orderNo);
//            String currentFlowUserName=(String) orderCurrentInfo.get("UNAME");
//            if(!itsm_username.equals(currentFlowUserName)){
//                //SendMsgUtils.sendTextByUserId("工单已归属："+ownerInfo.getFullName()+"，可自行联系", qixinUserId);
//                meurl="";
//                type = "5";
//            }
//        }
//        switch ((int) Integer.valueOf(type)) {
//            case 0:
//                String number = request.getParameter("number");
//                String status = request.getParameter("status");
//                String usernameId = request.getParameter("usernameId");
//                //Operator oper = userService.getOperator(itsm_username);
//                //usernameId = oper.getFullName();
//                //String recieveUrl = Constants.recieveUrl.replace("NUMBER", number).replace("USERNAMEID", usernameId).replace("STATUS", status);
//
//                AjaxJson aj = userService.getEventOrderStatus(number, itsm_username);
//                EventOrder eo = (EventOrder) aj.getObj();
//                String cssAcceptFlag = eo.getIsAccept();
//
//                if ("挂起申请中".equals(eo.getCss_status()) || "挂起中".equals(eo.getCss_status())
//                        || "已解决".equals(eo.getCss_status()) || "已废弃".equals(eo.getCss_status())
//                        || "结束".equals(eo.getCss_status())||"二线处理中".equals(eo.getCss_status())) {
//                    meurl = meurl.replace("recicase.recicase", "casedetail.casedetail");
//                    finalUrl = finalUrl + "#" + meurl;
//                } else {
//                    if (Boolean.valueOf(cssAcceptFlag)) {
//                        // String isAccept = "该工单已经接受过，无须再接受";
//                        // WeixinUtil.sendWechartMsg(accessToken,
//                        // isAccept);
//                        meurl = meurl.replace("recicase.recicase", "caseinfo.caseinfo");
//                        finalUrl = finalUrl + "#" + meurl;
//                    } else {//工单未接受状态的处理0：只是接受、1：接受并上门服务
//                        //String accStr = userService.accept(usernameId, number, status);
//                        AjaxJson acceptJson=zqTodoListServiceI.accept(itsm_username, number);
//                        net.biz.plugins.utils.AjaxJson ajaxJson = new net.biz.plugins.utils.AjaxJson();
//                        //JSONObject jsonObject = JSONObject.fromObject(accStr);
//                        if (null != acceptJson) {
//                            if (acceptJson.isSuccess()) {
//                                String reciAnd = request.getParameter("reciAnd");
//                                if (StringUtils.isEmpty(reciAnd)) {
//                                    meurl = meurl.replace("recicase.recicase", "doortodoor.doortodoor");
//                                } else if ("1".equals(reciAnd)) {
//                                    meurl = meurl.replace("recicase.recicase", "doormodel.doormodel");
//                                } else {
//                                    //meurl = meurl.replace("recicase.recicase", "caseinfo.caseinfo");
//                                }
//                                finalUrl = finalUrl + "#" + meurl;
//                            } else {
//                                meurl = meurl.replace("recicase.recicase", "caseinfo.caseinfo");
//                                System.out.println("事件接受错误，发送消息");
//                            }
//                        }
//                    }
//                }
//                break;
//            case 1:
//                orderNo =request.getParameter("number");
//                AjaxJson evtAjaxJson = userService.getEventOrderStatus(orderNo,itsm_username);
//                EventOrder eventOrder = (EventOrder) evtAjaxJson.getObj();
//                String cssAcceptFlag1 = eventOrder.getIsAccept();
//                String cssStatus = eventOrder.getCss_status();
//                if ("挂起申请中".equals(cssStatus) || "挂起中".equals(cssStatus) || "已解决".equals(cssStatus)
//                        || "已废弃".equals(cssStatus) || "结束".equals(cssStatus)||"二线处理中".equals(cssStatus)) {
//                    meurl = meurl.replace("recicase.recicase", "casedetail.casedetail");
//                } else {
//                    if (Boolean.valueOf(cssAcceptFlag1)) {
//                        meurl = meurl.replace("recicase.recicase", "casedetail.casedetail");
//                    } else {
//                        meurl = meurl.replace("recicase.recicase", "caseinfo.caseinfo");
//                    }
//                }
//                finalUrl=finalUrl+ "#"+meurl;
//                break;
//            case 2:
//                String orderNo2 = request.getParameter("number");
//                AjaxJson evtJson = userService.getEventOrderStatus(orderNo2,itsm_username);
//                EventOrder evtOrder = (EventOrder) evtJson.getObj();
//                String cssAcceptFlag2 = evtOrder.getIsAccept();
//                String cssAcceptFlag3 = evtOrder.getIsAssignment();
//                String cssStatus2 = evtOrder.getCss_status();
//                if ("挂起申请中".equals(cssStatus2) || "挂起中".equals(cssStatus2) || "已解决".equals(cssStatus2)
//                        || "已废弃".equals(cssStatus2) || "结束".equals(cssStatus2)) {
//                    if (!Boolean.valueOf(cssAcceptFlag2)) {
//                        meurl = meurl.replace("caseinfo.turntosend", "casedetail.casedetail");
//                    } else {
//                        meurl = meurl.replace("recicase.turntosend", "casedetail.casedetail");
//                    }
//                } else {
//                    if (Boolean.valueOf(cssAcceptFlag3)) {
//                        //meurl = meurl.replace("recicase.turntosend", "recicase.recicase");
//                    } else {
//                        //String isAccept = "该工单已经转派过，无须再转派";
//                        //WeixinUtil.sendWechartMsgToUsers(accessToken, isAccept,wx_userid);
//                        if (!Boolean.valueOf(cssAcceptFlag2)) {
//                            meurl = meurl.replace("caseinfo.turntosend", "casedetail.casedetail");
//                        } else {
//                            meurl = meurl.replace("recicase.turntosend", "casedetail.casedetail");
//                        }
//                    }
//                }
//
//                finalUrl = finalUrl + "#" + meurl;
//                break;
//            case 4:
//                finalUrl = finalUrl + "#" + meurl;
//                break;
//            case 5:
//                //String sb = "&cssAcceptFlag=" + _map.get("cssFlag")[0] + "&userIdList=" + itsm_username + schema + "&status=" + _map.get("status")[0];
//                //ydywUrl = ydywUrl.substring(0, ydywUrl.lastIndexOf("/") + 1) + meurl + sb;
//                break;
//            case 6:
//
//                //recicase.recicase:{"number":"PM-031200-00339352","cssAcceptFlag":"false"}
//                orderNo =request.getParameter("number");
//                String isagreet = request.getParameter("reciAnd");
//                //修改这里
//                //Map<String,Object> rmap=userService.getPleaseOrderCurrentInfo(orderNo,itsm_username);
//                //String currentFlowUser=(String) rmap.get("APPR_ID");
//                Operator oper = operService.getOperatorById(itsm_username);
//    			String name = oper.getFullName();
//
//    			Map<String, Object> rmap = ocmqService.OcmqInfo(orderNo, name);
//    			//System.out.println("点击同意/不同意按钮/"+rmap);
//    			if(rmap==null) {
//    				System.out.println("工单归他人，非自己流程");
//                    meurl=meurl.replace("requestDetail.requestDetail", "requestUnit.requestUnit")+":{'isagree':-3,'orderNo':'"+orderNo+"'}";
//                    finalUrl = finalUrl + "#" + meurl;
//                    break;
//    			}
//    			String currentFlowUser = (String) rmap
//    					.get("CSS_APPR_NAME");
//
//    			System.out.println("currentFlowUser"+currentFlowUser);
//                if (StringUtils.isEmpty(currentFlowUser)) {
//                    //工单归他人，非自己流程
//                	System.out.println("工单归他人，非自己流程");
//                    meurl=meurl.replace("requestDetail.requestDetail", "requestUnit.requestUnit")+":{'isagree':-3,'orderNo':'"+orderNo+"'}";
//                    finalUrl = finalUrl + "#" + meurl;
//                    break;
//                }else{
//                	String currentFlowResult=(String) rmap.get("WECHAT_AGREEST");
//                    //String[] splitUser = currentFlowUser.replace("\n",",").split(",");
//                    //List<String> flowUsers = Arrays.asList(splitUser);
//                    String[] splitUser = currentFlowUser.split("\n");
//    				String strApprNum = (String) rmap
//    						.get("CSS_APPR_NUM");
//    				int cssApprNum = StringUtils.isEmpty(strApprNum) ? 0 : Integer
//    						.valueOf(strApprNum);
//    				String[] apprName = splitUser[cssApprNum].split(",");
//    				for (int i = 0; i < apprName.length; i++)
//    					apprName[i] = apprName[i].replace(",", "");
//    				boolean tag = false;
//    				for (int i = 0; i < apprName.length; i++) {
//    					if (name.equals(apprName[i])) {
//    						System.out.println(apprName[i] + "/" + name);
//    						tag = true;
//    						break;
//    					}
//
//    				}
//    				if (!tag) {
//    					     // 工单归他人，非自己流程
//    					    meurl=meurl.replace("requestDetail.requestDetail", "requestUnit.requestUnit")+":{'isagree':-3,'orderNo':'"+orderNo+"'}";
//    	                    finalUrl = finalUrl + "#" + meurl;
//    	                    break;
//    				}
//
//
//
//                  if (StringUtils.isEmpty(currentFlowResult)) {//同意时候
//                    //未处理，自己流程
//                    if (StringUtils.isEmpty(isagreet)) {
//                        meurl=meurl+":{'isagree':-1,'orderNo':'"+orderNo+"'}";
//                        finalUrl = finalUrl + "#" + meurl;
//                        break;
//                    }else{
//                        int res=-1;
//                        if ("0".equals(isagreet)) {
//                            meurl=meurl.replace("requestDetail.requestDetail", "resultPage.resultPage");
//                            meurl=meurl+":{'isagree':0,'orderNo':'"+orderNo+"'}";
//                            //res=userService.getocmqUpdatePleaseOrder(orderNo, "t");
//
//                            Boolean isSamePhase = true;
//        					if (cssApprNum + 1 == splitUser.length) {
//        						isSamePhase = false;
//        					}
//        					// 2.判断要不要跳阶段，和获取下一阶段的审批人或者操作人
//        					if (isSamePhase) {
//        						// 还是本阶段
//        						String[] splitUserName = ((String) rmap
//        								.get("APPR_ID")).split("\n");
//        						// 获取分配人Id和分配人名称
//        						String assigedId = splitUserName[cssApprNum + 1];
//        						String assigedName = splitUser[cssApprNum + 1];
//        						// 获取当前登录人英文
//        						boolean result = ocmqService
//        								.apprSamePhase(orderNo, type, "", itsm_username,
//        										assigedId, assigedName,
//        										(String) rmap
//        												.get("CURRENT_PHASE"),
//        										cssApprNum + 1, splitUser.length,
//        										splitUserName);
//        						res=1;
//        						// System.out.println("result"+result);
//
//        					} else {
//        						// 跳阶段
//        						if (ocmqService.judgeNew(orderNo) == 0) {
//        							if (zqTodoListServiceI.ocmqUpdate(orderNo, type, "", name)) {
//        								res=1;
//        								break;
//        							} else {
//        								break;
//        							}
//
//        						}
//
//        						OcmqPhaseLogEntity ocmqPhaseLogEntity = ocmqService
//        								.apprJumpPhase(orderNo, type, "", name,
//        										(String) rmap
//        												.get("CURRENT_PHASE"));
//        						// 看这个是什么阶段
//        						if (ocmqPhaseLogEntity != null) {
//        							String phaseName = ocmqPhaseLogEntity
//        									.getPhaseName();
//        							System.out.println("NextphaseName----" + phaseName);
//        							int phaseNum = ocmqPhaseLogEntity.getPhaseNum();
//        							if (!rmap.get("CURRENT_PHASE").equals(
//        									phaseName))
//        							// 获取审批人
//        							{
//        								ocmqService.getApprPerson(phaseName, phaseNum,
//        										orderNo, "", itsm_username,
//        										(String) rmap
//        												.get("CURRENT_PHASE"));
//        							}
//        						}
//        						res=1;
//
//        					}
//
//                        }else{
//                            meurl=meurl+":{'isagree':1,'orderNo':'"+orderNo+"'}";
//                            //res=userService.getocmqUpdatePleaseOrder(orderNo, "f");//这里不同意都不作处理，跳到页后有可能改变注意
//                            if (ocmqService.judgeNew(orderNo) == 0) {
//        						if (zqTodoListServiceI.ocmqUpdate(orderNo, type, "", name)) {
//        							res=1;
//
//        						}
//        						break;
//        					}
//
//        					ocmqService.rejectOcmq(orderNo,
//        							(String) rmap.get("CURRENT_PHASE"),
//        							"", name);
//                            res=1;
//
//                        }
//                        System.out.println("请求单是否同意：res = " + res);
//                        finalUrl = finalUrl + "#" + meurl;
//                    }
//                }else{
//                    //已处理，自己流程
//                    meurl=meurl.replace("requestDetail.requestDetail", "requestUnit.requestUnit")+":{'isagree':-2,'orderNo':'"+orderNo+"'}";
//                    finalUrl = finalUrl + "#" + meurl;
//                }
//
//        }
//        break;
//        }
//        System.out.println("meurl = " + meurl);
//        map.put("meurl", meurl);
//        try {
//            buildCookie(itsm_username,request,response);
//        } catch (Exception e) {
//            System.out.println("写入Cookie失败" );
//            e.printStackTrace();
//        }
//        ajaxJsonAll.setObj(map);
//        ajaxJsonAll.setSuccess(true);
//        ajaxJsonAll.setMsg("isOk");
//        //response.sendRedirect(finalUrl);
//        String text = ajaxJsonAll.getJsonStr();
//        return text;
//    }
//
//    @RequestMapping(value = "qxredirect.data", method = RequestMethod.GET)
//    @ResponseBody
//    public void getUserInfo(Model model, String code, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String authCode = code;// request.getParameter("code");
//        AuthCodeGetInfoResponse userInfo = com.comtop.esp.api.client.SendMsgUtils.userInfo(authCode);
//        userInfo.getGender();//性别
//        userInfo.getOpenId();//openid
//        //userInfo.getUserName();//姓名
//        String qixinUserNameCn=userInfo.getName();
//        String qixinUserId=userInfo.getUserId();
//        userInfo.getAccount();//帐号
//        String tel=userInfo.getPhone();//手机号
//
//        String itsm_username = userService.getAcountByMapUserName(qixinUserId);
//        if (StringUtils.isEmpty(itsm_username) && !StringUtils.isEmpty(tel)) {
//            itsm_username = userService.getItsmUserNameByTel(tel);
//            if (StringUtils.isEmpty(itsm_username)) {
//                SendMsgUtils.sendTextByUserId("您在企信上填写的手机号"+tel+"与ITSM填写的手机号不一致", qixinUserId);
//            }else{
//                userService.addUserNameToQixin(itsm_username, qixinUserId);
//            }
//        }
//        Object o = null;
//        try {
//            o = loginByName(null, itsm_username, request, response);
//        } catch (OAuthSystemException e) {
//            e.printStackTrace();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String url=request.getQueryString();
//        System.out.println("url = " + url);
//        String type = request.getParameter("getType");
//        String meurl = request.getParameter("meurl");
//        if (StringUtils.isEmpty(type) && StringUtils.isEmpty(meurl)) {
//            type="5";
//        }
//        String finalUrl=Constants.oauthUrl;
//        String orderNo =null;
//        if(Integer.valueOf(type)<4){
//            orderNo =request.getParameter("number");
//            Map<String,Object> orderCurrentInfo=userService.getOrderCurrentInfo(orderNo);
//            String currentFlowUserName=(String) orderCurrentInfo.get("UNAME");
//            if(!itsm_username.equals(currentFlowUserName)){
//                //SendMsgUtils.sendTextByUserId("工单已归属："+ownerInfo.getFullName()+"，可自行联系", qixinUserId);
//                meurl="";
//                type = "5";
//            }
//        }
//        switch ((int) Integer.valueOf(type)) {
//            case 0:
//                String number = request.getParameter("number");
//                String status = request.getParameter("status");
//                String usernameId = request.getParameter("usernameId");
//                //Operator oper = userService.getOperator(itsm_username);
//                //usernameId = oper.getFullName();
//                //String recieveUrl = Constants.recieveUrl.replace("NUMBER", number).replace("USERNAMEID", usernameId).replace("STATUS", status);
//
//                AjaxJson aj = userService.getEventOrderStatus(number, itsm_username);
//                EventOrder eo = (EventOrder) aj.getObj();
//                String cssAcceptFlag = eo.getIsAccept();
//
//                if ("挂起申请中".equals(eo.getCss_status()) || "挂起中".equals(eo.getCss_status())
//                        || "已解决".equals(eo.getCss_status()) || "已废弃".equals(eo.getCss_status())
//                        || "结束".equals(eo.getCss_status())||"二线处理中".equals(eo.getCss_status())) {
//                    meurl = meurl.replace("recicase.recicase", "casedetail.casedetail");
//                    finalUrl = finalUrl + "#" + meurl;
//                } else {
//                    if (Boolean.valueOf(cssAcceptFlag)) {
//                        // String isAccept = "该工单已经接受过，无须再接受";
//                        // WeixinUtil.sendWechartMsg(accessToken,
//                        // isAccept);
//                        meurl = meurl.replace("recicase.recicase", "caseinfo.caseinfo");
//                        finalUrl = finalUrl + "#" + meurl;
//                    } else {//工单未接受状态的处理0：只是接受、1：接受并上门服务
//                        //String accStr = userService.accept(usernameId, number, status);
//                        AjaxJson acceptJson=zqTodoListServiceI.accept(itsm_username, number);
//                        net.biz.plugins.utils.AjaxJson ajaxJson = new net.biz.plugins.utils.AjaxJson();
//                        //JSONObject jsonObject = JSONObject.fromObject(accStr);
//                        if (null != acceptJson) {
//                            if (acceptJson.isSuccess()) {
//                                String reciAnd = request.getParameter("reciAnd");
//                                if (StringUtils.isEmpty(reciAnd)) {
//                                    meurl = meurl.replace("recicase.recicase", "doortodoor.doortodoor");
//                                } else if ("1".equals(reciAnd)) {
//                                    meurl = meurl.replace("recicase.recicase", "doormodel.doormodel");
//                                } else {
//                                    //meurl = meurl.replace("recicase.recicase", "caseinfo.caseinfo");
//                                }
//                                finalUrl = finalUrl + "#" + meurl;
//                            } else {
//                                meurl = meurl.replace("recicase.recicase", "caseinfo.caseinfo");
//                                System.out.println("事件接受错误，发送消息");
//                            }
//                        }
//                    }
//                }
//                break;
//            case 1:
//                orderNo =request.getParameter("number");
//                AjaxJson evtAjaxJson = userService.getEventOrderStatus(orderNo,itsm_username);
//                EventOrder eventOrder = (EventOrder) evtAjaxJson.getObj();
//                String cssAcceptFlag1 = eventOrder.getIsAccept();
//                String cssStatus = eventOrder.getCss_status();
//                if ("挂起申请中".equals(cssStatus) || "挂起中".equals(cssStatus) || "已解决".equals(cssStatus)
//                        || "已废弃".equals(cssStatus) || "结束".equals(cssStatus)||"二线处理中".equals(cssStatus)) {
//                    meurl = meurl.replace("recicase.recicase", "casedetail.casedetail");
//                } else {
//                    if (Boolean.valueOf(cssAcceptFlag1)) {
//                        meurl = meurl.replace("recicase.recicase", "casedetail.casedetail");
//                    } else {
//                        meurl = meurl.replace("recicase.recicase", "caseinfo.caseinfo");
//                    }
//                }
//                finalUrl=finalUrl+ "#"+meurl;
//                break;
//            case 2:
//                String orderNo2 = request.getParameter("number");
//                AjaxJson evtJson = userService.getEventOrderStatus(orderNo2,itsm_username);
//                EventOrder evtOrder = (EventOrder) evtJson.getObj();
//                String cssAcceptFlag2 = evtOrder.getIsAccept();
//                String cssAcceptFlag3 = evtOrder.getIsAssignment();
//                String cssStatus2 = evtOrder.getCss_status();
//                if ("挂起申请中".equals(cssStatus2) || "挂起中".equals(cssStatus2) || "已解决".equals(cssStatus2)
//                        || "已废弃".equals(cssStatus2) || "结束".equals(cssStatus2)) {
//                    if (!Boolean.valueOf(cssAcceptFlag2)) {
//                        meurl = meurl.replace("caseinfo.turntosend", "casedetail.casedetail");
//                    } else {
//                        meurl = meurl.replace("recicase.turntosend", "casedetail.casedetail");
//                    }
//                } else {
//                    if (Boolean.valueOf(cssAcceptFlag3)) {
//                        //meurl = meurl.replace("recicase.turntosend", "recicase.recicase");
//                    } else {
//                        //String isAccept = "该工单已经转派过，无须再转派";
//                        //WeixinUtil.sendWechartMsgToUsers(accessToken, isAccept,wx_userid);
//                        if (!Boolean.valueOf(cssAcceptFlag2)) {
//                            meurl = meurl.replace("caseinfo.turntosend", "casedetail.casedetail");
//                        } else {
//                            meurl = meurl.replace("recicase.turntosend", "casedetail.casedetail");
//                        }
//                    }
//                }
//
//                finalUrl = finalUrl + "#" + meurl;
//                break;
//            case 4:
//                finalUrl = finalUrl + "#" + meurl;
//                break;
//            case 5:
//                //String sb = "&cssAcceptFlag=" + _map.get("cssFlag")[0] + "&userIdList=" + itsm_username + schema + "&status=" + _map.get("status")[0];
//                //ydywUrl = ydywUrl.substring(0, ydywUrl.lastIndexOf("/") + 1) + meurl + sb;
//                break;
//            case 6:
//                //recicase.recicase:{"number":"PM-031200-00339352","cssAcceptFlag":"false"}
//                //recicase.recicase:{"number":"PM-031200-00339352","cssAcceptFlag":"false"}
//                orderNo =request.getParameter("number");
//                String isagreet = request.getParameter("reciAnd");
//                Map<String,Object> rmap=userService.getPleaseOrderCurrentInfo(orderNo,itsm_username);
//                String currentFlowUser=(String) rmap.get("APPR_ID");
//                if (StringUtils.isEmpty(currentFlowUser)) {
//                    //工单归他人，非自己流程
//                    meurl=meurl.replace("requestDetail.requestDetail", "requestUnit.requestUnit");
//                    finalUrl = finalUrl + "#" + meurl;
//                    break;
//                }else{
//                    String[] splitUser = currentFlowUser.replace("\n",",").split(",");
//                    List<String> flowUsers = Arrays.asList(splitUser);
//                    if (!flowUsers.contains(itsm_username)) {
//                        //工单归他人，非自己流程
//                        meurl=meurl.replace("requestDetail.requestDetail", "requestUnit.requestUnit");
//                        finalUrl = finalUrl + "#" + meurl;
//                        break;
//                    }
//                }
//
//                String currentFlowResult=(String) rmap.get("WECHAT_AGREEST");
//                if (StringUtils.isEmpty(currentFlowResult)) {
//                    //未处理，自己流程
//                    if (StringUtils.isEmpty(isagreet)) {
//                        meurl=meurl+":{'isagree':-1}";
//                        finalUrl = finalUrl + "#" + meurl;
//                        break;
//                    }else{
//                        int res=-1;
//                        if ("0".equals(isagreet)) {
//                            meurl=meurl.replace("requestDetail.requestDetail", "resultPage.resultPage");
//                            meurl=meurl+":{'isagree':0}";
//                            res=userService.getocmqUpdatePleaseOrder(orderNo, "t");
//                        }else{
//                            meurl=meurl+":{'isagree':1}";
//                            res=userService.getocmqUpdatePleaseOrder(orderNo, "f");
//                        }
//                        System.out.println("请求单是否同意：res = " + res);
//                        finalUrl = finalUrl + "#" + meurl;
//                    }
//                }else{
//                    //已处理，自己流程
//                    meurl=meurl.replace("requestDetail.requestDetail", "requestUnit.requestUnit");
//                    finalUrl = finalUrl + "#" + meurl;
//                }
//                break;
//        }
//        System.out.println("finalUrl = " + finalUrl);
//        response.sendRedirect(finalUrl);
//
//        //return o;
//    }
//
//    @RequestMapping(value="/auth", method = RequestMethod.GET)
//    public void auth(HttpServletRequest request,HttpServletResponse response) {
//        String requestUrl = request.getRequestURL().toString();
//        //注意：如果重定向地址在eimconnectionconfig.properties配置文件中已修改定义好，则只需传入请求url参数即可，
//        //代码如下：
//
//        //注意：如果如要手动传入重定向地址，代码如下：
//        try {
//            String authotizeUrl = com.comtop.esp.api.util.EimUtils.getAuthotizeUrl(requestUrl);
//            //String authotizeUrl = com.comtop.esp.api.util.EimUtils.getAuthotizeUrl(requestUrl,"redirect_uri-授权后重定向的回调链接地址");
//            response.sendRedirect(authotizeUrl);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private Object loginByName(Model model, String mapUsername, HttpServletRequest request, HttpServletResponse response) throws OAuthSystemException, URISyntaxException, IOException {
//        Subject subject = SecurityUtils.getSubject();
//        //如果用户没有登录，跳转到登陆页面
//        String pwd = "123456";
//        if (!login(subject, mapUsername, pwd, request, response)) {//登录失败时跳转到登陆页面
//            //model.addAttribute("client", clientService.findByClientId(oauthRequest.getClientId()));
//            return "login";
//        }
//        return null;
//
//    }
//
//    private boolean login(Subject subject, String username, String password, HttpServletRequest request, HttpServletResponse response) {
////        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
////            return false;
////        }
////        String itsmUser = userService.getAcountByMapUserName(username);
////        if (StringUtils.isEmpty(itsmUser)) {
////            SendMsgUtils.sendTextByUserId("帐号：" + username + "没有在qixin_user表中做关联", _adminUser);
////        } else {
////        }
//        //password = userService.getPwdByAccount(username);
//       // UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//
//
//        try {
//            //subject.login(token);
//            if (subject.isAuthenticated()) {
//                System.out.println("subject.isAuthenticated() = " + subject.isAuthenticated());
//            }else{
//            }
//            //loginNoPwd(username, request, response);
//            return true;
//        } catch (Exception e) {
//            request.setAttribute("error", "登录失败:" + e.getClass().getName());
//            return false;
//        }
//    }
//
//    private boolean loginNoPwd(String username, HttpServletRequest request, HttpServletResponse response) {
//        boolean isSuccess = false;
//        try {
//            PrincipalCollection principals = new SimplePrincipalCollection(username, "MobileRealm");
//            WebSubject.Builder builder = new WebSubject.Builder(request, response);
//            builder.principals(principals);
//            builder.authenticated(true);
//            WebSubject subject2 = builder.buildWebSubject();
//            ThreadContext.bind(subject2);
//            isSuccess = true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return isSuccess;
//    }
//
//    @RequestMapping(value = "test.data", method = RequestMethod.GET)
//    public void sendMsg(String userids) {
//
//        String userid = "00015f0f78dfae17c";
//        //SendMsgUtils.sendTextByUserId("xiaotolove",userid);
//
//        String APPID = PropertiesUtils.getProperty("APP_ID");
//        String SECRET = PropertiesUtils.getProperty("APP_SECRET");
//        String ipPort = PropertiesUtils.getProperty("APP_IPPORT");
//        String oauthUrl = "http://ipPort/open/oauth/authorize?client_id=APPID&response_type=code&confirm_auth=true&redirect_uri=REDIRECT_URI";
//
//        String redirectUrl = "http://172.16.1.18/wx/qxmsg/qxredirect.data?userids=1&name=xiao";
//
//        System.out.println("oauthUrl = " + oauthUrl);
//
//
////        try {
////            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
////            oauthUrl=oauthUrl.replace("ipPort", ipPort).replace("APPID", APPID).replace("REDIRECT_URI", redirectUrl);
////        } catch (UnsupportedEncodingException e) {
////            e.printStackTrace();
////        }
////        List<ArticleEsp> articles = new ArrayList<ArticleEsp>();
////        String fid = uploadImage(new File("C:\\Users\\Administrator\\Desktop\\xiao2.jpg"));
////        articles.add(new ArticleEsp("title1", fid, "描述说明", redirectUrl));
////        SendMsgUtils.sendRichTextByUserId(articles, userid);
//
//        String text = "你好,\n[a href=" + redirectUrl + " text=[查看详情]]";
//        //SendMsgUtils.sendWebPageByUserId("title",type,img,webUrl,description,webmode,openId);
//
//        boolean isok = SendMsgUtils.sendTextByUserId(text, userid);
//        System.out.println("isok = " + isok);
//    }
//
//    @RequestMapping(value = "/eim_ps_auth.data", method = RequestMethod.GET)
//    public String eimPsAuth(HttpServletRequest request, HttpServletResponse response, Model model) {
//        String PS_TOKEN = "xiaotolove";
//        try {
//            String timestamp = request.getParameter("timestamp");
//            String nonce = request.getParameter("nonce");
//            String echostr = request.getParameter("echostr");
//            String signature = request.getParameter("signature");
//            String access = Sha1DigestUtils.access(timestamp, nonce, PS_TOKEN);
//            if (signature.equals(access)) {
//                response.getWriter().print(echostr);
//            } else {
//                response.getWriter().print("接入失败");
//            }
//            // out.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//    /**
//     * 事件超时消息推送
//     *
//     * @throws UnsupportedEncodingException
//     */
//    @RequestMapping(value = "over.do", method = RequestMethod.POST)
//    @ResponseBody
//    public String wxSendMsgEventOver(String userIdList, String jobId, String userList, String context, String cssAcceptFlag, String token, String key) throws UnsupportedEncodingException {
//        AjaxJson ajaxJson = new AjaxJson();
//        if (!AccessUtil.isOkWechat(token, key)) {
//            logger.info("[wxSendMsgEventOver]微信消息推送缺少参数");
//        } else {
//
//            String userId = userIdList;
//            String orderNo =jobId;
//            String orderTitle =null;// DESUtil.unescape(userList);
//            String orderDesc = context;
//
//            //userIdList = DESUtil.decryption(userIdList);
//            //String orderNo = DESUtil.decryption(jobId);
//            context = DESUtil.unescape(context);
//            context= context.replaceAll("\n","\\\\n");//StringEscapeUtils.unescapeJava(context);
//            JSONObject  myJson = JSONObject.fromObject(context);
//            JSONArray arrJson=myJson.getJSONArray("contextList");
//
//            switch (_isSend) {
//                case 2:
//                    AccessToken at = TokenThread.getAccessToken();
//                    if (null != at) {
//                        String wechatUserid = checkWechat(at, userIdList);
//                        if (StringUtils.isEmpty(wechatUserid)) {
//                            String msg = "帐号[" + userIdList + "]没有配置企业号映射信息";
//                            WeixinUtil.sendWechartMsgToUsers(at, msg, Constants.yuweiAdmin);
//                            return "{isOK:0,msg:201}";
//                        }
//                        TextMsg textMsg = new TextMsg();
//                        textMsg.touser = wechatUserid;// "xiaotolove|wangxuan|liujianlin|xiehongwei|caiqing";
//                        textMsg.msgtype = "text";
//                        textMsg.safe = 0;
//                        textMsg.agentid = Constants.AGENTID;
//
//                        // http://172.16.3.184:8080/wxtest/wxSendMsgEventOverAction.action?orderNo=ORDERNO&orderTitle=ORDERTITLE&orderDesc=ORDERDESC&userId=USERID
//                        Text text = new Text();
//                        text.content = context + MsgFormat.eventOrderPassMsgFormat(orderNo, orderTitle, context, cssAcceptFlag,
//                                Constants.oauthUrl);
//                        textMsg.text = text;
//
//                        String jsonMsg = JSONObject.fromObject(textMsg).toString();
//
//                        // 拼装创建菜单的url
//                        String url = wxurl.sendMsg_url.replace("ACCESS_TOKEN", at.getToken());
//                        String result = WeixinUtil.sendMsg(url, jsonMsg);
//                        System.out.println("result = " + result);
//                    }
//                    break;
//                case 1:
//                    //String userid = userService.getQixinAcountByUserName(userIdList);
//
//                    Map<String, String> idsText = new HashMap<String, String>();
//                    List<String> ids = new ArrayList<String>();
//                    for (int i = 0; i < arrJson.size(); i++) {
//                        JSONObject nextObj = arrJson.getJSONObject(i);
//                        String userIdListStr = nextObj.getString("userIdList");
//                        userIdList = DESUtil.decryption(userIdListStr);
//
//                        String subContext = nextObj.getString("context");
//                        orderTitle = nextObj.getString("userList");
//                        orderNo = DESUtil.decryption(nextObj.getString("jobId"));
//                        cssAcceptFlag = DESUtil.decryption(nextObj.getString("css_accept_flag"));
//
//                        String oauthUrl = subContext + QixinMsgFormat.eventOrderPassMsgFormat(orderNo, orderTitle, subContext, cssAcceptFlag, _qxRedirectUrl);
//                        idsText.put(userIdList, oauthUrl);
//                        ids.add(userIdList);
//                    }
//                    List<Map<String,String>> userids = userService.getQixinAcountByUserNames(ids);
//                    for (int i = 0; i < userids.size(); i++) {
//                        Map<String, String> idMap = userids.get(i);
//                        String userid = idMap.get("WECHAT_USERNAME");
//                        String tempItms_Username = idMap.get("ITSM_USERNAME");
//                        String oauthUrl=idsText.get(tempItms_Username);
//                        SendMsgUtils.sendTextByUserId(oauthUrl, userid);
//                    }
//
//
//                    //userid = "00015f0f78dfae17c";
//                    //SendMsgUtils.sendTextByUserId(oauthUrl, userid);
//                    break;
//            }
//        }
//        String text = ajaxJson.toJson();
//        return text;
//    }
//
//    /**
//     * 请求审批消息推送
//     *
//     * @throws UnsupportedEncodingException
//     */
//    @RequestMapping(value = "apply.do", method = RequestMethod.POST)
//    @ResponseBody
//    public String wxSendMsgApply(String userIdList, String jobId, String isShenPi, String userList, String context,  String token, String key) throws UnsupportedEncodingException {
//        AjaxJson ajaxJson = new AjaxJson();
//        if (!AccessUtil.isOkWechat(token, key)) {
//            logger.info("[wxSendMsgEventOver]微信消息推送缺少参数");
//        } else {
//
//            String userId = userIdList;
//            String orderNo =jobId;
//            String orderTitle =null;// DESUtil.unescape(userList);
//            String orderDesc = context;
//
//            //userIdList = DESUtil.decryption(userIdList);
//            //String orderNo = DESUtil.decryption(jobId);
//            context = DESUtil.unescape(context);
//            context= context.replaceAll("\n","\\\\n");//StringEscapeUtils.unescapeJava(context);
//            JSONObject  myJson = JSONObject.fromObject(context);
//            JSONArray arrJson=myJson.getJSONArray("contextList");
//
//            //Map m = myJson;
//            switch (_isSend) {
//                case 2:
//                    AccessToken at = TokenThread.getAccessToken();
//                    if (null != at) {
//                        String wechatUserid = checkWechat(at, userIdList);
//                        if (StringUtils.isEmpty(wechatUserid)) {
//                            String msg = "帐号[" + userIdList + "]没有配置企业号映射信息";
//                            WeixinUtil.sendWechartMsgToUsers(at, msg, Constants.yuweiAdmin);
//                            return "{isOK:0,msg:201}";
//                        }
//                        TextMsg textMsg = new TextMsg();
//                        textMsg.touser = wechatUserid;// "xiaotolove|wangxuan|liujianlin|xiehongwei|caiqing";
//                        textMsg.msgtype = "text";
//                        textMsg.safe = 0;
//                        textMsg.agentid = Constants.AGENTID;
//
//                        // http://172.16.3.184:8080/wxtest/wxSendMsgEventOverAction.action?orderNo=ORDERNO&orderTitle=ORDERTITLE&orderDesc=ORDERDESC&userId=USERID
//                        Text text = new Text();
//                        text.content = context + MsgFormat.eventOrderPassMsgFormat(orderNo, orderTitle, context, null,
//                                Constants.oauthUrl);
//                        textMsg.text = text;
//
//                        String jsonMsg = JSONObject.fromObject(textMsg).toString();
//
//                        // 拼装创建菜单的url
//                        String url = wxurl.sendMsg_url.replace("ACCESS_TOKEN", at.getToken());
//                        String result = WeixinUtil.sendMsg(url, jsonMsg);
//                        System.out.println("result = " + result);
//                    }
//                    break;
//                case 1:
//                    //userid = "00015f0f78dfae17c";
//                    Map<String, String> idsText = new HashMap<String, String>();
//                    List<String> ids = new ArrayList<String>();
//                    for (int i = 0; i < arrJson.size(); i++) {
//                        JSONObject nextObj = arrJson.getJSONObject(i);
//                        String userIdListStr = nextObj.getString("userIdList");
//                        userIdList = DESUtil.decryption(userIdListStr);
//
//                        String subContext = nextObj.getString("context");
//                        orderTitle = nextObj.getString("userList");
//
//                        boolean isShenPiPast = Boolean.valueOf(isShenPi);
//                        String oauthUrl = subContext + QixinMsgFormat.eventMsgYNFormat(isShenPiPast,orderNo, orderTitle, subContext, _qxRedirectUrl);
//                        idsText.put(userIdList, oauthUrl);
//                        ids.add(userIdList);
//                    }
//                    List<Map<String,String>> userids = userService.getQixinAcountByUserNames(ids);
//                    for (int i = 0; i < userids.size(); i++) {
//                        Map<String, String> idMap = userids.get(i);
//                        String userid = idMap.get("WECHAT_USERNAME");
//                        String tempItms_Username = idMap.get("ITSM_USERNAME");
//                        String oauthUrl=idsText.get(tempItms_Username);
//                        boolean isOk=SendMsgUtils.sendTextByUserId(oauthUrl, userid);
//                        System.out.println("Msg send isOk = " + isOk);
//                    }
//                    //SendMsgUtils.sendTextByUserIds()
//                    break;
//            }
//        }
//        String text = ajaxJson.toJson();
//        return text;
//    }
//
//    /**
//     * 事件流程节点消息护送
//     *
//     * @throws UnsupportedEncodingException
//     */
//    @RequestMapping(value = "eventflow.do", method = RequestMethod.GET)
//    @ResponseBody
//    public void eventFlowNodeMsg(String token, String key, String userIdList, String jobId, String usernameId, String context, String css_status, String cssAcceptFlag) throws UnsupportedEncodingException {
//
//        System.out.println("com here 进来了：token = " + token);
//        if (!AccessUtil.isOkWechat(token, key)) {
//            logger.info("[eventFlowNodeMsg]微信消息推送缺少参数");
//        } else {
//            context = DESUtil.unescape(context);
//            css_status = DESUtil.unescape(css_status);
//            css_status="1";
////            userIdList = DESUtil.decryption(userIdList);
////            jobId = DESUtil.decryption(jobId);
////            cssAcceptFlag = DESUtil.decryption(cssAcceptFlag);
////            usernameId = DESUtil.decryption(usernameId);
//            switch (_isSend) {
//
//                case 2:
//                    AccessToken at = TokenThread.getAccessToken();// WeixinUtil.getAccessToken(Constants.CORPID, Constants.SECRET);
//                    if (null != at) {
//                        String wechatUserid = checkWechat(at, userIdList);
//                        if (StringUtils.isEmpty(wechatUserid)) {
//                            String msg = "帐号[" + userIdList + "]没有配置企业号映射信息";
//                            WeixinUtil.sendWechartMsgToUsers(at, msg, Constants.yuweiAdmin);
//                            return;
//                        }
//                        TextMsg textMsg = new TextMsg();
//                        textMsg.touser = wechatUserid;// "xiaotolove|wangxuan|liujianlin|xiehongwei|caiqing";
//                        textMsg.msgtype = "text";
//                        textMsg.safe = 0;
//                        textMsg.agentid = Constants.AGENTID;
//
//                        // http://172.16.3.184:8080/wxtest/wxSendMsgEventOverAction.action?orderNo=ORDERNO&orderTitle=ORDERTITLE&orderDesc=ORDERDESC&userId=USERID
//                        Text text = new Text();
//                        text.content = context + MsgFormat.eventFlowNodeMsgFormat(jobId, usernameId, css_status, cssAcceptFlag,
//                                Constants.redirectUrl, Constants.oauthUrl);
//                        textMsg.text = text;
//
//                        String jsonMsg = JSONObject.fromObject(textMsg).toString();
//
//                        // 拼装创建菜单的url
//                        String url = wxurl.sendMsg_url.replace("ACCESS_TOKEN", at.getToken());
//                        String result = WeixinUtil.sendMsg(url, jsonMsg);
//                        System.out.println("result = " + result);
//                    }
//                    break;
//                case 1:
//                    System.out.println("userIdList = " + userIdList);
//                    String oauthUrl = context + QixinMsgFormat.eventFlowNodeMsgFormat(jobId, usernameId, css_status, cssAcceptFlag, Constants.redirectUrl, _qxRedirectUrl);//Constants.oauthUrl);
//                    String userid = userService.getQixinAcountByUserName(userIdList);
//                    //userid = "00015f0f78dfae17c";
//                    System.out.println("userid = " + userid);
//                    System.out.println("MsgText = " + oauthUrl);
//                    boolean isOkMsg=SendMsgUtils.sendTextByUserId(oauthUrl, userid);
//                    System.out.println("企信发送消息：isOkMsg = " + isOkMsg);
//                    //SendMsgUtils.sendTextByUserId("测试长度："+oauthUrl.length(), "000160fdd5df5311af");
//                    //SendMsgUtils.sendTextByUserId(oauthUrl, "000160fdd5df5311af");
//                    //SendMsgUtils.sendTextByUserId(oauthUrl, "0001610324c96d7f1c");
//                    break;
//            }
//        }
//    }
//
//    @RequestMapping(value = "please.do", method = RequestMethod.GET)
//    @ResponseBody
//    public void pleaseOrderFlowNodeMsg(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
//
//        String token = request.getParameter("token");
//        String key = request.getParameter("key");
//        if (!AccessUtil.isOkWechat(token, key)) {
//            logger.info("[pleaseOrderFlowNodeMsg]微信消息推送缺少参数");
//        } else {
//            String userId = request.getParameter("userIdList");
//            String orderNo = request.getParameter("jobId");
//            String orderTitle = request.getParameter("userList");
//            String orderDesc = request.getParameter("context");
//
//            orderDesc = DESUtil.unescape(orderDesc);
//            orderTitle = DESUtil.unescape(orderTitle);
////            userId = DESUtil.decryption(userId);
////            orderNo = DESUtil.decryption(orderNo);
////            orderTitle = DESUtil.decryption(orderTitle);
//
//            String levelName = "";
//            switch (_isSend) {
//                case 2:
//                    AccessToken at = TokenThread.getAccessToken();// WeixinUtil.getAccessToken(Constants.CORPID, Constants.SECRET);
//                    if (null != at) {
//                        String wechatUserid = checkWechat(at, userId);
//                        if (StringUtils.isEmpty(wechatUserid)) {
//                            String msg = "帐号[" + userId + "]没有配置企业号映射信息";
//                            WeixinUtil.sendWechartMsgToUsers(at, msg, Constants.yuweiAdmin);
//                            return;
//                        }
//                        TextMsg textMsg = new TextMsg();
//                        textMsg.touser = wechatUserid;// "xiaotolove|wangxuan|liujianlin|xiehongwei|caiqing";
//                        textMsg.msgtype = "text";
//                        textMsg.safe = 0;
//                        textMsg.agentid = Constants.AGENTID;
//
//                        // "xiaobo|zhangsan|lisi"
//
//                        // http://172.16.3.184:8080/wxtest/wxSendMsgEventOverAction.action?orderNo=ORDERNO&orderTitle=ORDERTITLE&orderDesc=ORDERDESC&userId=USERID
//                        Text text = new Text();
//                        text.content = orderDesc;// +
//                        // MsgFormat.pleaseOrderFlowNodeMsgFormat(orderNo,
//                        // orderTitle, orderDesc, levelName,
//                        // "","");
//                        textMsg.text = text;
//
//                        String jsonMsg = JSONObject.fromObject(textMsg).toString();
//
//                        // 拼装创建菜单的url
//                        String url = wxurl.sendMsg_url.replace("ACCESS_TOKEN", at.getToken());
//                        String result = WeixinUtil.sendMsg(url, jsonMsg);
//                        System.out.println("result = " + result);
//                    }
//                    break;
//                case 1:
//                    String oauthUrl = orderDesc;
//                    String userid_itsm = userService.getQixinAcountByUserName(userId);
//                    //userid_itsm = "00015f0f78dfae17c";
//                    SendMsgUtils.sendTextByUserId(oauthUrl, userid_itsm);
//                    break;
//            }
//        }
//    }
//
//    @RequestMapping(value = "doorToDoor.do", method = RequestMethod.GET)
//    @ResponseBody
//    public void doorToDoorServiceMsg(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
//
//        String token = request.getParameter("token");
//        String key = request.getParameter("key");
//        if (!AccessUtil.isOkWechat(token, key)) {
//            logger.info("[doorToDoorServiceMsg]微信消息推送缺少参数");
//        } else {
//            String userId = request.getParameter("userIdList");
//            String orderNo = request.getParameter("jobId");
//            String orderTitle = request.getParameter("userList");
//            String orderDesc = request.getParameter("context");
//
//            userId = DESUtil.decryption(userId);
//            orderNo = DESUtil.decryption(orderNo);
//            orderDesc = DESUtil.unescape(orderDesc);
//            orderTitle = DESUtil.decryption(orderTitle);
//
//            String appId = "wx9712284c60e6fdc3";
//            // 第三方用户唯一凭证密钥
//            String appSecret = "8-nT6zkxU5rCxT-p9Zg2O1U15HTinl4ETjW5O9c5JVAKU_MSxbZa7cCPz5_IrSWf";
//            // 调用接口获取access_token
//            AccessToken at = TokenThread.getAccessToken();// WeixinUtil.getAccessToken(Constants.CORPID, Constants.SECRET);
//            if (null != at) {
//                String wechatUserid = checkWechat(at, userId);
//                if (StringUtils.isEmpty(wechatUserid)) {
//                    String msg = "帐号[" + userId + "]没有配置企业号映射信息";
//                    WeixinUtil.sendWechartMsgToUsers(at, msg, Constants.yuweiAdmin);
//                    return;
//                }
//                TextMsg textMsg = new TextMsg();
//                textMsg.touser = wechatUserid;// "xiaotolove|wangxuan|liujianlin|xiehongwei|caiqing";
//                textMsg.msgtype = "text";
//                textMsg.safe = 0;
//                textMsg.agentid = Constants.AGENTID;
//
//                // "xiaobo|zhangsan|lisi"
//
//                // http://172.16.3.184:8080/wxtest/wxSendMsgEventOverAction.action?orderNo=ORDERNO&orderTitle=ORDERTITLE&orderDesc=ORDERDESC&userId=USERID
//                Text text = new Text();
//                text.content = orderDesc; // MsgFormat.doorToDoorServiceMsgFormat(orderNo,
//                // orderTitle, orderDesc, "", "");
//                textMsg.text = text;
//
//                String jsonMsg = JSONObject.fromObject(textMsg).toString();
//                String url = wxurl.sendMsg_url.replace("ACCESS_TOKEN", at.getToken());
//                String result = WeixinUtil.sendMsg(url, jsonMsg);
//                System.out.println("result = " + result);
//            }
//        }
//    }
//
//    @RequestMapping(value = "adddoor.do", method = RequestMethod.GET)
//    @ResponseBody
//    public String addDoorToDoorDatetime(HttpServletRequest request, HttpServletResponse response) throws IOException {
//
//        System.out.println("DoorToDoor:========= ");
//        // String username = "liyongjie";
//        // Operator oper = operatorService.getOperator(username + schema);
//        // String number="PM-031200-00114487";
//        // String datetime="2017-5-9 16:20";
//        String number = request.getParameter("number");
//        String datetime = request.getParameter("datetime");
//        String curUserName = request.getParameter("fullName");
//        curUserName = DESUtil.unescape(curUserName);
//        String tel = request.getParameter("tel");
//
//        int result = userService.doorToDoor(number, datetime);
//        System.out.println("result = " + result);
//        AjaxJson ajaxJson = new AjaxJson();
//        if (result == 1) {
//            ajaxJson.setSuccess(true);
//            ajaxJson.setMsg("上门预约时间成功");
//            Operator opt = userService.getOrderOwnerInfo(number);
//            String itsmUserid = opt.getName();
//            String fullName = opt.getFullName();
//            //String tel=opt.getTelPhone();
//            switch (_isSend) {
//                case 2:
//
//                    AccessToken at = TokenThread.getAccessToken();// WeixinUtil.getAccessToken(Constants.CORPID,
//                    // Constants.SECRET);
//                    if (null != at) {
//                        String wxUserid = checkWechat(at, itsmUserid);
//
//                        if (StringUtils.isEmpty(wxUserid)) {
//                            String msg = fullName + "的帐号[" + itsmUserid + "]没有配置企业号映射信息";
//                            if (StringUtils.isEmpty(tel)) {
//                                msg += "也没有在ITSM系统中配置[手机号码]信息,请找管理员添加";
//                            }
//                            WeixinUtil.sendWechartMsgToUsers(at, msg, Constants.yuweiAdmin);
//                            ajaxJson.setSuccess(false);
//                            ajaxJson.setMsg(msg);
//                        } else {
//                            if (StringUtils.isEmpty(tel)) {
//                                String msg = fullName + "的帐号[" + itsmUserid + "]没有在ITSM系统中配置[手机号码]信息,请找管理员添加";
//                                WeixinUtil.sendWechartMsgToUsers(at, msg, wxUserid);
//                            }
//                            TextMsg textMsg = new TextMsg();
//                            textMsg.touser = wxUserid;// "xiaotolove|wangxuan|liujianlin|xiehongwei|caiqing";
//                            textMsg.msgtype = "text";
//                            textMsg.safe = 0;
//                            textMsg.agentid = Constants.AGENTID;
//
//                            // "xiaobo|zhangsan|lisi"
//
//                            // http://172.16.3.184:8080/wxtest/wxSendMsgEventOverAction.action?orderNo=ORDERNO&orderTitle=ORDERTITLE&orderDesc=ORDERDESC&userId=USERID
//                            Text text = new Text();
//                            text.content = MsgFormat.doorToDoorServiceMsgFormat(number, curUserName, fullName, datetime) + "\n联系电话：" + tel;
//                            textMsg.text = text;
//
//                            String jsonMsg = JSONObject.fromObject(textMsg).toString();
//                            String url = wxurl.sendMsg_url.replace("ACCESS_TOKEN", at.getToken());
//                            String res = WeixinUtil.sendMsg(url, jsonMsg);
//                            System.out.println("result == " + res);
//                        }
//                    }
//                    break;
//                case 1:
//                    String oauthUrl = MsgFormat.doorToDoorServiceMsgFormat(number, curUserName, fullName, datetime) + "\n联系电话：" + tel;
//                    String userid_itsm = userService.getQixinAcountByUserName(itsmUserid);
//                    SendMsgUtils.sendTextByUserId(oauthUrl, userid_itsm);
//                    break;
//            }
//        } else {
//            ajaxJson.setSuccess(false);
//            ajaxJson.setMsg("上门预约时间失败");
//
//        }
//        return ajaxJson.getJsonStr();
//
//    }
//
//    @RequestMapping(value = "serveyWechat.do", method = RequestMethod.GET)
//    @ResponseBody
//    public void serveyWechat(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
//
//        String token = request.getParameter("token");
//        String key = request.getParameter("key");
//        if (!AccessUtil.isOkWechat(token, key)) {
//            logger.info("[serveyWechat]微信消息推送缺少参数");
//        } else {
//            String userId = request.getParameter("userIdList");
//            String orderNo = request.getParameter("jobId");
//            //String orderTitle = request.getParameter("userList");
//            String surveyid = request.getParameter("surveyid");
//            String orderDesc = request.getParameter("context");
//
//            //userId = DESUtil.decryption(userId);
//            //orderNo = DESUtil.decryption(orderNo);
//            orderDesc = DESUtil.unescape(orderDesc);
//            //orderTitle = DESUtil.decryption(orderTitle);
//            //surveyid = DESUtil.decryption(surveyid);
//            switch (_isSend) {
//                case 2:
//                    AccessToken at = TokenThread.getAccessToken();// WeixinUtil.getAccessToken(Constants.CORPID, Constants.SECRET);
//                    if (null != at) {
//                        String wechatUserid = checkWechat(at, userId);
//                        if (StringUtils.isEmpty(wechatUserid)) {
//                            String msg = "帐号[" + userId + "]没有配置企业号映射信息";
//                            WeixinUtil.sendWechartMsgToUsers(at, msg, Constants.yuweiAdmin);
//                            return;
//                        }
//                        TextMsg textMsg = new TextMsg();
//                        textMsg.touser = wechatUserid;// "xiaotolove|wangxuan|liujianlin|xiehongwei|caiqing";
//                        textMsg.msgtype = "text";
//                        textMsg.safe = 0;
//                        textMsg.agentid = Constants.AGENTID;
//
//                        Text text = new Text();
//                        text.content = orderDesc + MsgFormat.serveyWechatMsgFormat(orderNo, userId, surveyid, Constants.oauthUrl);
//                        textMsg.text = text;
//
//                        String jsonMsg = JSONObject.fromObject(textMsg).toString();
//                        String url = wxurl.sendMsg_url.replace("ACCESS_TOKEN", at.getToken());
//                        String result = WeixinUtil.sendMsg(url, jsonMsg);
//                        System.out.println("result = " + result);
//                    }
//                case 1:
//                    String url = "http://172.16.1.18/wx/qxmsg/qxredirect.data";
//                    String oauthUrl = orderDesc + QixinMsgFormat.serveyWechatMsgFormat(orderNo, userId, surveyid, _qxRedirectUrl);
//                    String userid = userService.getQixinAcountByUserName(userId);
//                    //userid = "00015f0f78dfae17c";
//                    SendMsgUtils.sendTextByUserId(oauthUrl, userid);
//                    break;
//            }
//        }
//    }
//
//    public String checkWechat(AccessToken at, String username) {
//        String wechatUserid = null;
//        AjaxJson weAjaxJson = userService.getItsmWechatUser(username, Constants.AGENTID);
//        Operator weOper = (Operator) weAjaxJson.getObj();
//        if (null == weOper) {
//            return null;
//        }
//        wechatUserid = weOper.getName();
//        return wechatUserid;
//
//    }
//
//    public void getWechatToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
//
//
//        String appid = request.getParameter("appid");
//        String secret = request.getParameter("secret");
//
//        response.setHeader("Cache-Control", "no-store");
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
//        response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
//        response.setHeader("X-Powered-By", "3.2.1");
//        response.setHeader("Server", "PHP");// 修改请求头信息
//        response.setContentType("application/json;charset=UTF-8");
//        PrintWriter pout = null;
//        pout = response.getWriter();
//
//        if (!StringUtils.isEmpty(appid) && "gzcss_itsm_client_wechat_msg".equals(appid)) {
//            TokenBean tokenBean = AccessUtil.create(request, "itsm_client");
//            JSONObject member = new JSONObject();
//            member.put("token", tokenBean.getAccessToken());
//            member.put("key", tokenBean.getKey());
//            member.put("express", tokenBean.getTimestamp());
//            member.put("errcode", 0);
//            pout.write(member.toString());
//        } else {
//            JSONObject member = new JSONObject();
//            member.put("errcode", 1);
//            member.put("user", "admin");
//            member.put("msg", "参数不正确");
//            pout.write(member.toString());
//        }
//    }
//
//    @Description("提交调查单")
//	@RequestMapping(value = "/saveSurvey")
//	@ResponseBody
//    public String saveSurvey(HttpServletRequest request, HttpServletResponse response,@RequestParam("params") String params) {
//    	String result="";
//        try {
//        	JSONObject obj=JSONObject.fromObject(params);
////            String accessToken = obj.getString("accessToken");
////            String key = obj.getString("key");
////            AccessUtil.isOk(request, response, accessToken, key);
//            result = userService.saveSurvey(obj);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    @Description("获取调查单头部信息")
//	@RequestMapping(value = "/getSurveyHead")
//	@ResponseBody
//    public String getSurveyHead(HttpServletRequest request, HttpServletResponse response) {
//        AjaxJson ajaxJson = new AjaxJson();
//        try {
//            request.setCharacterEncoding("UTF-8");
////            String accessToken = request.getParameter("accessToken");
////            String key = request.getParameter("key");
////            AccessUtil.isOk(request, response, accessToken, key);
//
//            String incident_id = request.getParameter("incident_id");
//            ajaxJson= userService.getSurveyHead(incident_id);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String text = ajaxJson.getJsonStr();
//        return text;
//    }
//
//    @RequestMapping(value = "sendQiXin.do", method = RequestMethod.GET)
//    @ResponseBody
//    public void sendQiXin(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
//
//        String token = request.getParameter("token");
//        String key = request.getParameter("key");
//        if (!AccessUtil.isOkWechat(token, key)) {
//            logger.info("[doorToDoor]企信消息推送缺少参数");
//        } else {
//
//            String obj = request.getParameter("obj");
//
//            obj = DESUtil.unescape(obj);
//            obj= obj.replaceAll("\n","\\\\n");//StringEscapeUtils.unescapeJava(context);
//            JSONObject  myJson = JSONObject.fromObject(obj);
//
//            JSONArray arrJson=myJson.getJSONArray("sendList");
//
//            for (int i = 0; i <arrJson.size() ; i++) {
//
//                String userid_itsm = userService.getQixinAcountByUserName(arrJson.getJSONObject(i).getString("userId"));
//                //userid_itsm = "00015f0f78dfae17c";
//                if(userid_itsm!=null && !userid_itsm.equals("")){
//
//                    SendMsgUtils.sendTextByUserId(arrJson.getJSONObject(i).getString("content"), userid_itsm);
//                }
//
//            }
//
//        }
//    }
//
//    @RequestMapping(value = "/mytest.data",method = RequestMethod.GET)
//    @ResponseBody
//    public String mytest(String name){
//        logger.info("测试代理服务器是否正常返回。传入参数："+name);
//        AjaxJson aj = new AjaxJson();
//        aj.setMsg("从代理服务器返回的内容是："+name);
//
//        return aj.getJsonStr();
//    }
//
//    public static void main(String[] args) {
////        Thread t = new Thread(new Qixin_TokenThread());
////        t.start();
////        AccessToken accessToken = Qixin_TokenThread.getAccessToken();
////        if (accessToken != null) {
////            System.out.println("accessToken.getToken() = " + accessToken.getToken());
////        }
////
////        boolean isOk = SendMsgUtils.sendTextByUserId("<a href=\"https://www.baidu.com\">查看详情</a>", "00015f0f78dfae17c");
////        if (isOk) {
////            System.out.println("isOk = " + isOk);
////        }
//
//        String dem = DESUtil.decryption("r9o8F06UUj0Ot9H2XDbTJg==");
//        System.out.println("dem = " + dem);
//        try {
//            String orgUrl="http://172.16.1.18/wxtest/main.html";
//            String url = URLEncoder.encode(orgUrl, "UTF-8");
//            System.out.println("url = " + url);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void buildCookie(String username,HttpServletRequest request, HttpServletResponse response) throws Exception {
//        String accessToken="gzcss";//(String) token.getCredentials();
//        String key ="gzcss_client";
//        String currentlogincookie = "{'username':'" + username + "','token':'"+accessToken+"','key':'"+key+"'}";
//        System.out.println(currentlogincookie + "****************");
//        String secretKey = "ovATL3QOQmKh0WiTqhkSbg==";
//        currentlogincookie= DESUtil.encryption(currentlogincookie, secretKey);
//        Cookie cookie = new Cookie("qixinlogincookie", URLEncoder.encode(currentlogincookie, "UTF-8"));
//        cookie.setMaxAge(1800);
//        response.addCookie(cookie);
//    }
//}
