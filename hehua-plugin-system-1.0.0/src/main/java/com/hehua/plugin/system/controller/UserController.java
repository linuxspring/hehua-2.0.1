package com.hehua.plugin.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hehua.plugin.system.model.Plat_Menu;
import com.hehua.plugin.system.model.Plat_Role;
import com.hehua.plugin.system.model.Plat_Rule;
import com.hehua.plugin.system.model.Plat_User;
import com.hehua.plugin.system.service.Impl.UserServiceImpl;
import com.hehua.plugin.system.view.UserLoginInfo;
import util.*;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Administrator on 2017/10/11.
 * IntelliJ IDEA 2017 of gzcss
 */
//@CrossOrigin(origins = {"https://localhost:6443", "null"})
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @InitBinder
    public void initDataBinder(WebDataBinder binder) {

        //DatePropertyEditor propertyEditor = new DatePropertyEditor();
        // propertyEditor.setFormat("yyyy-MM-dd HH:mm:ss");
        //binder.registerCustomEditor(Date.class, propertyEditor);
    }

    @Resource
    @Autowired(required = true)
    private UserServiceImpl userServiceImpl;

 /*   @Autowired
    private DictServiceImpl dictServiceImpl;*/

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    @ResponseBody
    public Plat_User findByAccount(String account) {

        return userServiceImpl.findByAccount(account);
    }

    @RequestMapping(value = "/find2", method = RequestMethod.GET)
    @ResponseBody
    public Plat_User findUserWithRoles(String account) {

        return userServiceImpl.findUserWithRoles(account);
    }


    @RequestMapping(value = "/list.data", method = RequestMethod.GET)
    @ResponseBody
    public String getList(int page, int size) {

        return userServiceImpl.getList(page, size);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String updateUser() {

        Plat_User user = new Plat_User();
        user.setUsername("test111");
        user.setFullname("测试更新");
        userServiceImpl.updateUser(user);

        return "success";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    @ResponseBody
    public String insertUser() {
        Plat_User user = new Plat_User();
        //user.setUser_name("test_" + new Random(100L).nextInt());
        //user.setFullname("测试新增");

        userServiceImpl.insertUser(user);

        return "success";
    }

    @RequestMapping(value = "/pwd.data", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String setPwdUserById(Long id, String newPwd, String oldPwd) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            String pwd = new Md5Hash(newPwd, null, 0).toString();

            Plat_User user = this.userServiceImpl.selectById(id);
            String old = new Md5Hash(oldPwd, null, 0).toString();
            if (user.getPassword().equals(old)) {
                user.setPassword(pwd);
                boolean res = this.userServiceImpl.insertOrUpdate(user);
                ajaxJson.setMsg("密码重置成功");
                ajaxJson.setSuccess(true);
            } else {
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("提供旧密码不正确");
            }

        } catch (NumberFormatException e) {
            ajaxJson.setSuccess(false);
            e.printStackTrace();
            ajaxJson.setMsg("密码重置失败");
        }
        String text = JacksonUtil.toJson(ajaxJson);
        return text;
    }

    @RequestMapping(value = "/pwdreset.data", method = RequestMethod.GET)
    @ResponseBody
    public String setPwdReSetUsersByIds(String ids, int type) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            String pwd = new Md5Hash("mz@111111", null, 0).toString();
            StringTokenizer tokenizer = new StringTokenizer(ids, ",");
            while (tokenizer.hasMoreTokens()) {
                Long id = Long.valueOf(tokenizer.nextToken());
                Plat_User user = this.userServiceImpl.selectById(id);
                user.setPassword(pwd);
                this.userServiceImpl.insertOrUpdate(user);
            }
            ajaxJson.setMsg("密码重置成功");
            ajaxJson.setSuccess(true);

        } catch (NumberFormatException e) {
            ajaxJson.setSuccess(false);
            e.printStackTrace();
            ajaxJson.setMsg("密码重置失败");
        }
        String text = JacksonUtil.toJson(ajaxJson);
        return text;
    }

    @RequestMapping(value = "/active.data", method = RequestMethod.GET)
    @ResponseBody
    public String setActiveUsersByIds(String ids, int type) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            StringTokenizer tokenizer = new StringTokenizer(ids, ",");
            while (tokenizer.hasMoreTokens()) {
                Long id = Long.valueOf(tokenizer.nextToken());
                Plat_User user = this.userServiceImpl.selectById(id);
                if (type == 0) {
                    user.userState = type;
                } else {
                    user.userState = type;
                }
                this.userServiceImpl.insertOrUpdate(user);
            } 
			if (type == 0) {
				ajaxJson.setMsg("激活成功");
			} else {
				ajaxJson.setMsg("禁用成功");
			}
            ajaxJson.setSuccess(true);

        } catch (NumberFormatException e) {
        	
            ajaxJson.setSuccess(false);
            e.printStackTrace();
            
			if (type == 0) {
				ajaxJson.setMsg("激活失败");
			} else {
				ajaxJson.setMsg("禁用失败");
			}
        }
        String text = JacksonUtil.toJson(ajaxJson);
        return text;
    }

    @RequestMapping(value = "/del.data", method = RequestMethod.GET)
    @ResponseBody
    public String delUsersByIds(String ids, int type) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            StringTokenizer tokenizer = new StringTokenizer(ids, ",");
            while (tokenizer.hasMoreTokens()) {
                Long id = Long.valueOf(tokenizer.nextToken());
                if (type == 0) {
                    Plat_User user = this.userServiceImpl.selectById(id);
                    user.isDeleted = 1;
                    this.userServiceImpl.insertOrUpdate(user);
                } else {
                    this.userServiceImpl.deleteById(id);
                }
            }
            ajaxJson.setMsg("删除成功");
            ajaxJson.setSuccess(true);

        } catch (NumberFormatException e) {
            ajaxJson.setSuccess(false);
            e.printStackTrace();
            ajaxJson.setMsg("删除失败");
        }
        String text = JacksonUtil.toJson(ajaxJson);
        return text;
    }

    @RequestMapping(value = "/detail.data", method = RequestMethod.GET)
    @ResponseBody
    public String getUsersById(Long id) {
        AjaxJson ajaxJson = new AjaxJson();
        try {

            Plat_User user = this.userServiceImpl.selectById(id);
            if (user != null) {
                ajaxJson.setMsg("详细信息成功");
                ajaxJson.setSuccess(true);
                ajaxJson.setObj(user);
            }

        } catch (NumberFormatException e) {
            ajaxJson.setSuccess(false);
            e.printStackTrace();
            ajaxJson.setMsg("详细信息失败");
        }
        String text = JacksonUtil.toJson(ajaxJson);
        return text;
    }

    @RequestMapping(value = "/save.data", method = RequestMethod.GET)
    @ResponseBody
    public String saveUser(String json) {
        AjaxJson ajaxJson = new AjaxJson();
        Plat_User user = JacksonUtil.fromJson(json, Plat_User.class);
        boolean res = false;
        if (user.getId() != null) {
            Plat_User oldUser = userServiceImpl.selectById(user.getId());
            oldUser.ulevel = user.ulevel;
            oldUser.setUsername(user.getUsername());
            oldUser.setFullname(user.getFullname());
            oldUser.setEmail(user.getEmail());
            oldUser.gender = user.gender;
            oldUser.address = user.address;
            oldUser.fax = user.fax;
            oldUser.comfirmMethod = user.comfirmMethod;
            oldUser.idcardNo = user.idcardNo;
            oldUser.description = user.description;
            oldUser.age = user.age;
            oldUser.link = user.link;
            oldUser.tel = user.tel;
            oldUser.wx = user.wx;
            oldUser.blockUpTime = user.blockUpTime;
            oldUser.type = user.type;
            oldUser.lastupdate = new Date();
            res = userServiceImpl.insertOrUpdate(oldUser);
        } else {
            String pwd = new Md5Hash("123456", null, 2).toString();
            String pwd2 = new Md5Hash("mz@111111", null, 0).toString();
            user.setPassword(pwd2);
            user.createtime = new Date();
            user.userKey = UUID.randomUUID().toString();
            try {
                res = userServiceImpl.insert(user);
                user.autoid = utils.toAutoid("U", 6, user.getId());
                res = userServiceImpl.insertOrUpdate(user);
            } catch (Exception e) {
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("新增失败," + "原因" + e.getMessage());
                return JacksonUtil.toJson(ajaxJson);
            }
        }

        if (res) {
            ajaxJson.setMsg("新增成功");
            ajaxJson.setSuccess(true);
        } else {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("新增失败");
        }
        //userServiceImpl.saveUser(json);

        String text = JacksonUtil.toJson(ajaxJson);
        return text;
    }

    @RequestMapping(value = "/view.data", method = RequestMethod.GET)
    @ResponseBody
    public String getView(String keyword, int type, int index, int size) {
        AjaxJson ajaxJson = new AjaxJson();

        Page<Plat_User> page = new Page<Plat_User>(index, size);
        EntityWrapper<Plat_User> ew = new EntityWrapper<Plat_User>();
        ew.ge("isdeleted", 0);
        if (!StringUtils.isEmpty(keyword)) {
            ew.like("fullname", keyword);
            ew.orNew().like("username", keyword).orNew().like("tel", keyword).orNew().like("autoid", keyword);
        }
        ew.orderBy("user_id", false);
        page = userServiceImpl.selectPage(page, ew);
        List<Plat_User> list = userServiceImpl.selectList(ew);
        int total = userServiceImpl.selectCount(ew);
        if (total != -1) {
            ajaxJson.setMsg("新增成功");
            ajaxJson.setSuccess(true);
        } else {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("新增失败");
        }
        PageResult pr = new PageResult(total, size, index, list);
        String text = JacksonUtil.toJson(pr);
        return text;
    }

    @RequestMapping(value = "/roleUsersView.data", method = RequestMethod.GET)
    @ResponseBody
    public String getRoleUsersView(String keyword, int type, int index, int size, Long roleid) {
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> condition = new HashMap<>();
        Page<Plat_User> page = new Page<Plat_User>(index, size);
        condition.put("roleid", roleid);
        if (!StringUtils.isEmpty(keyword)) {
            condition.put("roleName", keyword);
        }
        List<Plat_User> mList = userServiceImpl.getRoleUsersView(page, condition);

        int total = (int) page.getTotal();
        if (total != -1) {
            ajaxJson.setSuccess(true);
            ajaxJson.setMsg("新增成功");
        } else {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("新增失败");
        }
        PageResult pr = new PageResult(total, size, index, mList);
        String text = JacksonUtil.toJson(pr);
        return text;
    }

    @RequestMapping(value = "/getInfo.data", method = RequestMethod.GET)
    @ResponseBody
    public String getUserInfoByName(String username) {
        System.out.println("username = " + username);
        String secretKey = "ovATL3QOQmKh0WiTqhkSbg==";
        String v = DESUtil.decryption(username);//.encryption(currentlogincookie, secretKey);
        Map<String, String> m = JacksonUtil.fromJsonToMap(v);
        EntityWrapper<Plat_User> ew = new EntityWrapper<Plat_User>();
        //ew.setEntity(user);
        ew.eq("username", m.get("username"));//.eq("user_id", 4);
        String username2 = m.get("username");
        Plat_User user = this.userServiceImpl.selectOne(ew);
        //User user = users.get(0);
        user.setPassword(null);

        List<Long> roleids = new ArrayList<>();
        List<Plat_Role> roles = new ArrayList<>();
        roles = userServiceImpl.getRolesByUserId(user.getId());
        List<Plat_Rule> rules = new ArrayList<>();
        if (roles.size() > 0) {
            for (int i = 0; i < roles.size(); i++) {
                Plat_Role plat_role = roles.get(i);
                roleids.add(plat_role.getId());
            }

            Map<String, Object> parms = new HashMap<>();
            parms.put("list", roleids);
            rules = userServiceImpl.getRulesByRoleids(parms);
        }


        List<Plat_Menu> menus = new ArrayList<>();
        if (rules.size() > 0) {
            roleids.clear();
            for (int i = 0; i < rules.size(); i++) {
                Plat_Rule plat_rule = rules.get(i);
                String menuIds = plat_rule.getMenuIds();
                if (StringUtils.isEmpty(menuIds)) {
                    continue;
                }
                String[] mids = menuIds.split(",");
                for (int j = 0; j < mids.length; j++) {
                    String mid = mids[j];
                    roleids.add(Long.valueOf(mid));
                }
            }
            Map<String, Object> condition = new HashMap<>();
            condition.put("list", roleids);
            menus = userServiceImpl.getMenusByIds(condition);
        }

       // EntityWrapper<MZDict> ewDict = new EntityWrapper<>();
        //ewDict.eq("sysid", 1);
        //List<MZDict> mzDicts = dictServiceImpl.selectList(ewDict);

        UserLoginInfo userLoginInfo = new UserLoginInfo();
        userLoginInfo.setUserInfo(user);
        userLoginInfo.setMenus(menus);
        userLoginInfo.isAdmin = true;
        userLoginInfo.isRoot = false;
       // userLoginInfo.dict = mzDicts;
        String text = JacksonUtil.toJson(userLoginInfo);
        return text;
    }

    
    @Params({@Param(nameCN = "数据", name = "fullname", desc = "数据"),})
    @RequestMapping(value = "/findbyfullname.data", method = RequestMethod.POST)
    @ResponseBody
    public List<Plat_User> findByFullname(@RequestParam("fullname") String keyword) {
        EntityWrapper<Plat_User> ew = new EntityWrapper<Plat_User>();
        ew.ge("isdeleted", 0);
        if (!StringUtils.isEmpty(keyword)) {
            ew.like("fullname", keyword);

        }
        ew.orderBy("user_id", false);
        List<Plat_User> list = userServiceImpl.selectList(ew);
        return list;
    }
    @Description("梅州任务-用户姓名-查找1")
    @Params({@Param(nameCN = "数据", name = "fullname", desc = "数据"),})
    @RequestMapping(value = "/findbyfullnameOne.data", method = RequestMethod.POST)
    @ResponseBody
    public String findByFullnameOne(@RequestParam("fullname") String keyword) {
       
        return userServiceImpl.getUserByrealFullName(keyword);
    }
    
    @RequestMapping(value = "/getliteInfo.data", method = RequestMethod.GET)
    @ResponseBody
    public String getUserInfoByNamelite(HttpServletRequest request, HttpServletResponse response,String username) {
        System.out.println("username = " + username);
        String secretKey = "ovATL3QOQmKh0WiTqhkSbg==";
        //String v = DESUtil.decryption(username);//.encryption(currentlogincookie, secretKey);
        Map<String, String> m = JacksonUtil.fromJsonToMap(username);
        EntityWrapper<Plat_User> ew = new EntityWrapper<Plat_User>();
        //ew.setEntity(user);
        ew.eq("username", m.get("username"));//.eq("user_id", 4);
        String username2 = m.get("username");
        Plat_User user = this.userServiceImpl.selectOne(ew);
        //User user = users.get(0);
        user.setPassword(null);
        TokenBean token = AccessUtil.create(request, username);
        user.setToken(token);


       

        UserLoginInfo userLoginInfo = new UserLoginInfo();
        userLoginInfo.setUserInfo(user);

        userLoginInfo.isAdmin = true;
        userLoginInfo.isRoot = false;

        String text = JacksonUtil.toJson(userLoginInfo);
        System.out.println(text);
        return text;
    }
    
    @Description("梅州任务-用户ID-查找2")
    @Params({@Param(nameCN = "数据", name = "id", desc = "id",type="int"),})
    @RequestMapping(value = "/findByIdOne.data", method = RequestMethod.GET)
    @ResponseBody
    public String findByIdOne(@RequestParam("id") int id) {
         Plat_User user  = userServiceImpl.selectById(id);
         String text = JacksonUtil.toJson(user);
         return text;
    }

}

