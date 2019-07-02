package com.hehua.plugin.system.service.Impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hehua.plugin.system.mapper.JdbcMapper;
import com.hehua.plugin.system.mapper.UserMapper;
import com.hehua.plugin.system.model.*;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.AjaxJson;
import util.PropertiesUtil;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/11. IntelliJ IDEA 2017 of gzcss
 */
@Service("jdbcService")
public class JdbcServiceImpl extends ServiceImpl<JdbcMapper, Plat_Jdbc> {

    // @Resource
    // @Autowired(required=true)
    // private UserMapper mapper;

//    @Resource(name = "sqlSessionFactoryBean")
//    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private UserMapper dao;
    
    @Autowired
    private JdbcMapper jdbcdao;

    //SqlSession sqlSession = null;
    // @Resource
    // private SqlSessionFactory sqlSessionFactory2;
    //
    // @Resource
    // private SqlSessionFactory sqlSessionFactory3;
    // @Autowired
    // protected BaseMapper baseMapper;

//    private SqlSession getSession() {
//        try {
//            if (sqlSession == null) {
//                sqlSession = sqlSessionFactory.openSession();
//            }
//            Connection connection = null;
//            try {
//                connection = sqlSession.getConnection();
//            } catch (Exception e) {
//                e.printStackTrace();
//                sqlSession.close();
//                sqlSession = sqlSessionFactory.openSession();
//                return sqlSession;
//            }
//            if (connection.isClosed()) {
//                sqlSession = sqlSessionFactory.openSession();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            sqlSession = sqlSessionFactory.openSession();
//        }
//        return sqlSession;
//    }



    public String getPwdByAccount(String account) {
        String user = "";
        if ("030500".equals(PropertiesUtil.getProperty("config.cityCode"))) {
            user = dao.getPwdByUser(account);
        }else if("030300".equals(PropertiesUtil.getProperty("config.cityCode"))){
        	user =dao.getPwd(account);
        } 
        else {
            //chose by pgj 170114 修改判断用户名后缀查询
            String schema = account + PropertiesUtil.getProperty("config.user.schema");
            //		SqlSession sqlSession = sqlSessionFactory.openSession();
            user = dao.getPwdByAccount(account, schema);
            //String user = sqlSession.selectOne("com.gzcss.weixin.mapper.UserMapper.getPwdByAccount", account, schema);// this.baseMapper.findByAccount(account);
        }
        return user;
    }

    public String getAcountByMapUserName(String username) {
        //SqlSession sqlSession = sqlSessionFactory.openSession();
        String user = this.sqlSessionBatch().selectOne("com.gzcss.weixin.mapper.UserMapper.getAcountByMapUserName", username);// this.baseMapper.findByAccount(account);
        return user;
    }

    public String getQixinAcountByUserName(String username) {
        //SqlSession sqlSession = sqlSessionFactory.openSession();
        String user = this.sqlSessionBatch().selectOne("com.gzcss.weixin.mapper.UserMapper.getQixinAcountByUserName", username);// this.baseMapper.findByAccount(account);
        return user;
    }

    public List<Map<String, String>> getQixinAcountByUserNames(List<String> usernames) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("list", usernames);
        List<Map<String, String>> users = this.sqlSessionBatch().selectList("com.gzcss.weixin.mapper.UserMapper.getQixinAcountByUserNames", params);// this.baseMapper.findByAccount(account);
        return users;
    }

    public String getItsmUserNameByTel(String tel) {
        //SqlSession sqlSession = sqlSessionFactory.openSession();
        String user=null;
        if ("030500".equals(PropertiesUtil.getProperty("config.cityCode"))) {
            user = this.sqlSessionBatch().selectOne("com.gzcss.weixin.mapper.UserMapper.getZngkUserNameByName", tel);
        }else{
            user = this.sqlSessionBatch().selectOne("com.gzcss.weixin.mapper.UserMapper.getItsmUserNameByTel", tel);// this.baseMapper.findByAccount(account);
        }
        return user;
    }

    public int addUserNameToQixin(String itsm_username, String qixinUserId) {
        //SqlSession sqlSession = sqlSessionFactory.openSession();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("itsm_username", itsm_username);
        params.put("qixin_userid", qixinUserId);
        int user = this.sqlSessionBatch().insert("com.gzcss.weixin.mapper.UserMapper.addUserNameToQixin", params);// this.baseMapper.findByAccount(account);
        return user;
    }

    public AjaxJson getEventOrderStatus(String orderNo, String username) {
        AjaxJson ajaxJson = new AjaxJson();
        //SqlSession sqlSession = sqlSessionFactory.openSession();
        Map<String, String> params = new HashMap<String, String>();
        params.put("orderNo", orderNo);
        params.put("username", username);
        List<Map<String, String>> user = this.sqlSessionBatch().selectList("com.gzcss.weixin.mapper.UserMapper.getEventOrderStatus",
                params);// this.baseMapper.findByAccount(account);
        // oper.setIsAccept(rs.getString("ISJIESHOU"));
        // oper.setIsAssignment(rs.getString("ISFENPA"));
        // oper.setCss_status(rs.getString("CSS_STATUS"));

        if (user.size() > 0) {
            Map<String, String> map = new HashMap<String, String>();
            map = user.get(0);
            EventOrder eo = new EventOrder();
            eo.setIsAccept(map.get("ISJIESHOU"));
            eo.setIsAssignment(map.get("ISFENPA"));
            eo.setCss_status(map.get("CSS_STATUS"));
            ajaxJson.setObj(eo);

        } else {

        }
        return ajaxJson;
    }

    public Operator getOrderOwnerInfo(String orderNo) {
        AjaxJson ajaxJson = new AjaxJson();
        //SqlSession sqlSession = sqlSessionFactory.openSession();
        Map<String, Object> user = this.sqlSessionBatch().selectOne("com.gzcss.weixin.mapper.UserMapper.getOrderOwnerInfo",
                orderNo);// this.baseMapper.findByAccount(account);
        // oper.setIsAccept(rs.getString("ISJIESHOU"));
        // oper.setIsAssignment(rs.getString("ISFENPA"));
        // oper.setCss_status(rs.getString("CSS_STATUS"));
        Operator oper = new Operator();

        if (user.size() > 0) {
            Map<String, Object> map = new HashMap<String, Object>();
            String username = (String) user.get("USERNAME");
            String fullname = (String) user.get("FULL_NAME");
            oper.setFullName(fullname);
            oper.setName(username);

        } else {

        }
        return oper;
    }

    public Map<String, Object> getOrderCurrentInfo(String orderNo) {
        AjaxJson ajaxJson = new AjaxJson();
        //SqlSession sqlSession = sqlSessionFactory.openSession();
        Map<String, Object> user = this.sqlSessionBatch().selectOne("com.gzcss.weixin.mapper.UserMapper.getOrderCurrentInfo", orderNo);// this.baseMapper.findByAccount(account);
//        oper.setIsAccept(rs.getString("ISJIESHOU"));
//        oper.setIsAssignment(rs.getString("ISFENPA"));
//        oper.setCss_status(rs.getString("CSS_STATUS"));
        Operator oper = new Operator();


        if (user.size() > 0) {
            Map<String, Object> map = new HashMap<String, Object>();
            String username = (String) user.get("USERNAME");
            String fullname = (String) user.get("FULL_NAME");
            oper.setFullName(fullname);
            oper.setName(username);


        } else {

        }
        return user;
    }

    public Map<String, Object> getPleaseOrderCurrentInfo(String orderNo, String username) {
        Map<String, Object> parms = new HashMap<String, Object>();
        parms.put("username", username);
        parms.put("orderNo", orderNo);
        Map<String, Object> user = this.sqlSessionBatch().selectOne("com.gzcss.weixin.mapper.UserMapper.getPleaseOrderCurrentInfo", parms);
        if (user == null) {
            user = new HashMap<String, Object>();
        }
        return user;
    }

    public int getocmqUpdatePleaseOrder(String orderNo, String value) {
        Map<String, Object> ajaxJson = new HashMap<String, Object>();
        ajaxJson.put("value", value);
        ajaxJson.put("number", orderNo);
        int user = this.sqlSessionBatch().update("com.gzcss.weixin.mapper.UserMapper.ocmqUpdate", ajaxJson);
        return user;
    }

    public String accept(String usernameId, String number, String status) {
        AjaxJson ajaxJson = new AjaxJson();
        //SqlSession sqlSession = sqlSessionFactory.openSession();
        Map<String, Object> user = this.sqlSessionBatch().selectOne("com.gzcss.weixin.mapper.UserMapper.accept", number);
        // oper.setIsAccept(rs.getString("ISJIESHOU"));
        // oper.setIsAssignment(rs.getString("ISFENPA"));
        // oper.setCss_status(rs.getString("CSS_STATUS"));
        return number;
    }
    

    public String getList(int page, int size) {

        //SqlSession sqlSession = sqlSessionFactory.openSession();

        List<Plat_User> result = this.sqlSessionBatch().selectList("com.gzcss.weixin.mapper.UserMapper.getList", Plat_User.class);

        int count;
        try {
            count = this.sqlSessionBatch().selectOne("com.gzcss.weixin.mapper.UserMapper.countUser");
            System.out.println("count = " + count);
        } finally {
            // sqlSession.close();
        }

        // Pagination<User> pagination = new Pagination<User>(page, size, count,
        // result);

        return "OK";

    }

    public void updateUser(Plat_User user) {
        // this.baseMapper.updateUser(user);
    }

    public void insertUser(Plat_User user) {
        // this.baseMapper.insert(user);
    }

    public void saveUser(String json) {
        Plat_User user = new Plat_User();

    }

    public int doorToDoor(String number, String datetime) {
        //SqlSession sqlSession = sqlSessionFactory.openSession();
        Map<String, String> params = new HashMap<String, String>();
        params.put("orderNo", number);
        params.put("fromDate", datetime);
        int result = this.sqlSessionBatch().update("com.gzcss.weixin.mapper.UserMapper.doorToDoor", params);
        return result;
    }

    public AjaxJson getItsmWechatUser(String username, int agentid) {
        return null;
    }



    public AjaxJson getSurveyHead(String incident_id) {
        // String sql="SELECT
        // INCIDENT_ID,TITLE,CALLBACK_CONTACT_FULLNAME,CSS_RESOLVE_EFFICIENCY,CSS_SERVE_ATTITUDE,CSS_SKILL,CSS_RETURNIDEA,RESULT
        // FROM INCIDENTSM1 WHERE INCIDENT_ID=:INCIDENT_ID";
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("INCIDENT_ID", incident_id);
        //SqlSession sqlSession = sqlSessionFactory.openSession();
        Map<String, Object> list = this.sqlSessionBatch().selectOne("com.gzcss.weixin.mapper.UserMapper.getSurveyHead", paramMap);
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> attr = new HashMap<String, Object>();
        if (list.size() > 0) {
            String reString = "";
            reString = (String) list.get("CSS_RETURNIDEA");
//			Clob clob = (Clob) list.get("CSS_RETURNIDEA");
//			StringBuffer sb = null;
//			try {
//				Reader is = clob.getCharacterStream();// 得到流
//				BufferedReader br = new BufferedReader(is);
//				String s = br.readLine();
//				sb = new StringBuffer();
//				while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
//					sb.append(s);
//					s = br.readLine();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
            //reString = sb.toString();
            list.put("CSS_RETURNIDEA", reString);
            if ("已反馈".equals(list.get("RESULT"))) {
                ajaxJson.setSuccess(true);
                ajaxJson.setObj(list);
                attr.put("result", true);
                ajaxJson.setAttributes(attr);
                ajaxJson.setMsg("温馨提示 : 您已经提交过该调查单");

            } else {
                ajaxJson.setSuccess(true);
                ajaxJson.setObj(list);
                attr.put("result", false);
                ajaxJson.setAttributes(attr);
            }
        } else {
            ajaxJson.setMsg("温馨提示：该交互单不存在");
            ajaxJson.setSuccess(false);
            ajaxJson.setObj(list);
            attr.put("result", false);
        }
        return ajaxJson;
    }

    public List<Plat_User> getRoleUsersView(Page<Plat_User> page, Map<String, Object> condition) {
        return this.baseMapper.getRoleUsersView(page, condition);
    }

    public List<Plat_User> getUserByFullName(String fullname) {

        return this.baseMapper.getUserByFullName(fullname);
    }
    public String getUserByrealFullName(String fullname) {

        return this.baseMapper.getUserByrealFullName(fullname);
    }

    public List<Plat_Role> getRolesByUserId(Long userid) {
        return this.baseMapper.getRolesByUserId(userid);
    }

    public List<Plat_Rule> getRulesByRoleids(Map<String, Object> roleids) {
        return this.baseMapper.getRulesByRoleids(roleids);
    }
    public List<Plat_Menu> getMenusByIds(Map<String, Object> ids) {
        return this.baseMapper.getMenusByIds(ids);
    }

}