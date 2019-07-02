package com.hehua.plugin.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hehua.plugin.system.model.Plat_Rule;
import com.hehua.plugin.system.model.Plat_User;
import com.hehua.plugin.system.service.Impl.RuleServiceImpl;
import util.DatePropertyEditor;
import util.JacksonUtil;
import util.PageResult;
import util.AjaxJson;
import util.utils;
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
@RequestMapping(value = "/rule")
public class RuleController {
    @InitBinder
    public void initDataBinder(WebDataBinder binder) {

        DatePropertyEditor propertyEditor = new DatePropertyEditor();
        propertyEditor.setFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, propertyEditor);
    }

    @Resource
    @Autowired(required = true)
    private RuleServiceImpl ruleServiceImpl;
//    @Resource
//    @Autowired(required=true)
//    private UserServiceImpl userServiceImpl;

    @RequestMapping(value = "/find.data", method = RequestMethod.GET)
    @ResponseBody
    public String findByMenu(String roleid) {
        AjaxJson ajaxJson = new AjaxJson();
        EntityWrapper<Plat_Rule> ew = new EntityWrapper<Plat_Rule>();
        ew.eq("roleid", roleid);
        Plat_Rule rule = ruleServiceImpl.selectOne(ew);
        if (rule == null) {
            rule = new Plat_Rule();
        }
        ajaxJson.setObj(rule);
        ajaxJson.setSuccess(true);
        ajaxJson.setMsg("取得权限成功");
        String text = JacksonUtil.toJson(ajaxJson);
        return text;
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
                    Plat_Rule menu = this.ruleServiceImpl.selectById(id);

                    this.ruleServiceImpl.insertOrUpdate(menu);
                } else {
                    this.ruleServiceImpl.deleteById(id);
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
            Plat_Rule platRule = JacksonUtil.fromJson(json, Plat_Rule.class);
            if (platRule.getId()!=null) {


                Plat_Rule oldRule = this.ruleServiceImpl.selectById(platRule.getId());
                oldRule.setFunIds(platRule.getFunIds());
                oldRule.setDescription(platRule.getDescription());
                oldRule.setRuleName(platRule.getRuleName());
                oldRule.setDictIds(platRule.getDictIds());
                oldRule.setMenuIds(platRule.getMenuIds());
                oldRule.setRoleid(platRule.getRoleid());
                boolean res = this.ruleServiceImpl.insertOrUpdate(oldRule);
                if (res) {
                    ajaxJson.setSuccess(true);
                    ajaxJson.setMsg("保存成功");
                } else {
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("保存失败");
                }
            } else {
                EntityWrapper<Plat_Rule> ew = new EntityWrapper<>();
                Long roleid = platRule.getRoleid();
                ew.eq("roleid", roleid);
                Plat_Rule existPlatRule = this.ruleServiceImpl.selectOne(ew);
                if (existPlatRule != null) {
                    existPlatRule.setFunIds(platRule.getFunIds());
                    existPlatRule.setDescription(platRule.getDescription());
                    existPlatRule.setRuleName(platRule.getRuleName());
                    existPlatRule.setDictIds(platRule.getDictIds());
                    existPlatRule.setMenuIds(platRule.getMenuIds());
                    existPlatRule.setRoleid(platRule.getRoleid());
                    boolean res = this.ruleServiceImpl.insertOrUpdate(existPlatRule);
                    if (res) {
                        ajaxJson.setSuccess(true);
                        ajaxJson.setMsg("保存成功");
                    } else {
                        ajaxJson.setSuccess(false);
                        ajaxJson.setMsg("保存失败");
                    }
                }else{
                    boolean res = ruleServiceImpl.insert(platRule);
                    platRule.autoid = utils.toAutoid("F", 6, platRule.getId());
                    ruleServiceImpl.insertOrUpdate(platRule);
                    if (res) {
                        ajaxJson.setSuccess(true);
                        ajaxJson.setMsg("新增成功");
                    } else {
                        ajaxJson.setSuccess(false);
                        ajaxJson.setMsg("新增失败");
                    }
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

        Page<Plat_Rule> page = new Page<Plat_Rule>(index, size);
        page = ruleServiceImpl.selectPage(page);
        EntityWrapper ew = new EntityWrapper();

        int total = ruleServiceImpl.selectCount(ew);
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
        Page<Plat_Rule> page = new Page<Plat_Rule>(index, size);
        EntityWrapper<Plat_Rule> ew = new EntityWrapper<Plat_Rule>();
        ew.orderBy("id", false);
        if (!StringUtils.isEmpty(keyword)) {
            ew.like("roleName", keyword);
        }
        List<Plat_Rule> mList = ruleServiceImpl.selectList(ew);

        int total = ruleServiceImpl.selectCount(ew);
        page = ruleServiceImpl.selectPage(page,ew);
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
            condition.put("roleName", keyword);
        }
        List<Plat_User> mList = ruleServiceImpl.getRoleUsersView(page, condition);

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
                total += ruleServiceImpl.addUsersToRole(condition);
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
            int total = this.ruleServiceImpl.removeUsersFromRole(condition);
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
