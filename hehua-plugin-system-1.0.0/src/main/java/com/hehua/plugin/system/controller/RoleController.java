package com.hehua.plugin.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hehua.plugin.system.model.Plat_Role;
import com.hehua.plugin.system.model.Plat_User;
import com.hehua.plugin.system.service.Impl.RoleServiceImpl;
import util.AjaxJson;
import util.DatePropertyEditor;
import util.JacksonUtil;
import util.utils;
import util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Administrator on 2018/4/6.
 * IntelliJ IDEA 2018 of gzcss
 */
@Controller
@RequestMapping(value = "/role")
public class RoleController {
    @InitBinder
    public void initDataBinder(WebDataBinder binder) {

        DatePropertyEditor propertyEditor = new DatePropertyEditor();
        propertyEditor.setFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, propertyEditor);
    }

    @Resource
    @Autowired(required = true)
    private RoleServiceImpl roleServiceImpl;
//    @Resource
//    @Autowired(required=true)
//    private UserServiceImpl userServiceImpl;

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    @ResponseBody
    public String findByMenu(String name) {
        return roleServiceImpl.findByMenu(name);
    }

    @RequestMapping(value = "/del.data", method = RequestMethod.GET)
    @ResponseBody
    public String delMenu(String ids, int type) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            StringTokenizer tokenizer = new StringTokenizer(ids, ",");
            while (tokenizer.hasMoreTokens()) {
                Long id = Long.valueOf( tokenizer.nextToken());
                if (type == 0) {
                    Plat_Role menu = this.roleServiceImpl.selectById(id);

                    this.roleServiceImpl.insertOrUpdate(menu);
                } else {
                    this.roleServiceImpl.deleteById(id);
                }
            }
            ajaxJson.setMsg("删除成功");
            ajaxJson.setSuccess(true);

        } catch (NumberFormatException e) {
            ajaxJson.setSuccess(false);
            e.printStackTrace();
            ajaxJson.setMsg("删除失败");
        }
        List<Long> idsList = new ArrayList<Long>();

        String text = JacksonUtil.toJson(ajaxJson);
        return text;
    }

    @RequestMapping(value = "/save.data", method = RequestMethod.GET)
    @ResponseBody
    public String saveMenu(String json) {
        AjaxJson ajaxJson = new AjaxJson();
        try {
            Plat_Role platRole = JacksonUtil.fromJson(json, Plat_Role.class);
            if (platRole.getId()!=null) {
                Plat_Role oldRole = this.roleServiceImpl.selectById(platRole.getId());
                oldRole.setTitle(platRole.getTitle());
                oldRole.setDescription(platRole.getDescription());
                oldRole.setRoleName(platRole.getRoleName());
                boolean res = this.roleServiceImpl.insertOrUpdate(oldRole);
                if (res) {
                    ajaxJson.setSuccess(true);
                    ajaxJson.setMsg("保存成功");
                } else {
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("保存失败");
                }
            } else {

                boolean res = roleServiceImpl.insert(platRole);
                platRole.autoid = utils.toAutoid("R",6, platRole.getId());
                roleServiceImpl.insertOrUpdate(platRole);
                if (res) {
                    ajaxJson.setSuccess(true);
                    ajaxJson.setMsg("新增成功");
                } else {
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("新增失败");
                }
            }
        } catch (Exception e) {


        } finally {
        }
        String text = JacksonUtil.toJson(ajaxJson);
        return text;
    }

    @RequestMapping(value = "/list.data", method = RequestMethod.GET)
    @ResponseBody
    public String getList(String keyword, int type, int index, int size) {
        AjaxJson ajaxJson = new AjaxJson();

        Page<Plat_Role> page = new Page<Plat_Role>(index, size);
        page = roleServiceImpl.selectPage(page);
        EntityWrapper ew = new EntityWrapper();

        int total = roleServiceImpl.selectCount(ew);
        if (total != -1) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("新增成功");
        } else {
            ajaxJson.setSuccess(true);
            ajaxJson.setMsg("新增失败");
        }

        String text = JacksonUtil.toJson(ajaxJson);
        return text;
    }

    @RequestMapping(value = "/view.data", method = RequestMethod.GET)
    @ResponseBody
    public String getView(String keyword, int type, int index, int size) {
        AjaxJson ajaxJson = new AjaxJson();
        Page<Plat_Role> page = new Page<Plat_Role>(index, size);
        EntityWrapper<Plat_Role> ew = new EntityWrapper<Plat_Role>();
        ew.orderBy("id", false);
        if (!StringUtils.isEmpty(keyword)) {
            ew.like("roleName", keyword).orNew().like("autoid",keyword);
        }
        List<Plat_Role> mList = roleServiceImpl.selectList(ew);

        int total = roleServiceImpl.selectCount(ew);
        page = roleServiceImpl.selectPage(page,ew);
        if (total != -1) {
            ajaxJson.setSuccess(true);
            ajaxJson.setMsg("新增成功");
        } else {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("新增失败");
        }
        PageResult pr = new PageResult(total, size, index, page.getRecords());
        String text = JacksonUtil.toJson(pr);
        return text;
    }

    @RequestMapping(value = "/roleUsersView.data", method = RequestMethod.GET)
    @ResponseBody
    public String getRoleUsersView(String keyword, int type, int index, int size,Long roleid) {
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> condition = new HashMap<>();
        Page<Plat_User> page = new Page<Plat_User>(index, size);
        condition.put("roleid", roleid);
        if (!StringUtils.isEmpty(keyword)) {
            condition.put("fullname", keyword);
        }
        List<Plat_User> mList = roleServiceImpl.getRoleUsersView(page, condition);

        int total =(int) page.getTotal();
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

    @RequestMapping(value = "/addUserToRole.data", method = RequestMethod.GET)
    @ResponseBody
    public String addUserToRole(int roleid, String userids, int type ) {
        AjaxJson ajaxJson = new AjaxJson();

        String[] ids=userids.split(",");
        List<String> list = Arrays.asList(ids);
        Map<String, Object> condition = new HashMap<>();
        condition.put("roleid", roleid);
        if (type == 0) {
            int total=0;
            for (int i = 0; i < ids.length; i++) {
                String id = ids[i];
                condition.put("user_id", id);
                total += roleServiceImpl.addUsersToRole(condition);
            }
            if (total == ids.length) {
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("添加用户成功");
            } else {
                ajaxJson.setSuccess(true);
                ajaxJson.setMsg("添加用户失败");
            }
        }else{
            condition.put("userids", userids);
            int total = this.roleServiceImpl.removeUsersFromRole(condition);
            if (total>=ids.length){
                ajaxJson.setSuccess(true);
                ajaxJson.setMsg("移除用户成功");
            } else {
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("移除用户失败");
            }
        }


        String text = JacksonUtil.toJson(ajaxJson);
        return text;
    }
}
