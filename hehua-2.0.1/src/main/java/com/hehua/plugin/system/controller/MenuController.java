package com.hehua.plugin.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hehua.plugin.system.model.Plat_Menu;
import com.hehua.plugin.system.service.Impl.MenuServiceImpl;
import util.AjaxJson;
import util.DatePropertyEditor;
import util.JacksonUtil;
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
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Administrator on 2018/4/6.
 * IntelliJ IDEA 2018 of gzcss
 */
@Controller
@RequestMapping(value = "/menu")
public class MenuController {
    @InitBinder
    public void initDataBinder(WebDataBinder binder) {

        DatePropertyEditor propertyEditor = new DatePropertyEditor();
        propertyEditor.setFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, propertyEditor);
    }

    @Resource
    @Autowired(required = true)
    private MenuServiceImpl menuServiceImpl;

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    @ResponseBody
    public String findByMenu(String name) {

        return menuServiceImpl.findByMenu(name);
    }

    @RequestMapping(value = "/del.data", method = RequestMethod.GET)
    @ResponseBody
    public String delMenu(String ids, int type) {
        AjaxJson ajaxJson = new AjaxJson();
        StringTokenizer tokenizer = new StringTokenizer(ids, ",");
        while (tokenizer.hasMoreTokens()) {
            Long id = Long.valueOf(tokenizer.nextToken());
            if(type==0){
                Plat_Menu menu = this.menuServiceImpl.selectById(id);

                this.menuServiceImpl.insertOrUpdate(menu);
            }else{
                this.menuServiceImpl.deleteById(id);
            }
        }
        //List<Long> idsList = new ArrayList<Long>();
//        boolean res = menuServiceImpl.deleteBatchIds(idsList);
//        if (res) {
//            ajaxJson.setSuccess(false);
//            ajaxJson.setMsg("删除成功");
//        } else {
//            ajaxJson.setSuccess(true);
//            ajaxJson.setMsg("删除失败");
//        }

        String text = JacksonUtil.toJson(ajaxJson);
        return text;
    }

    @RequestMapping(value = "/save.data", method = RequestMethod.GET)
    @ResponseBody
    public String saveMenu(String json) {
        AjaxJson ajaxJson = new AjaxJson();
        Plat_Menu menu = JacksonUtil.fromJson(json, Plat_Menu.class);
        if (menu.getId()!=null) {
            boolean res = this.menuServiceImpl.insertOrUpdate(menu);
            if (res) {
                ajaxJson.setMsg("保存成功");
                ajaxJson.setSuccess(true);
            } else {
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("保存失败");
            }
        } else {
            boolean res = menuServiceImpl.insert(menu);
            if (res) {
                ajaxJson.setMsg("新增成功");
                ajaxJson.setSuccess(true);
            } else {
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("新增失败");
            }
        }
        String text = JacksonUtil.toJson(ajaxJson);
        return text;
    }

    @RequestMapping(value = "/list.data", method = RequestMethod.GET)
    @ResponseBody
    public String getList(int index,int size,String json) {
        AjaxJson ajaxJson = new AjaxJson();
        System.out.println("json = " + json);
        List<Plat_Menu> list= null;
        try {
            EntityWrapper<Plat_Menu> ew = new EntityWrapper<Plat_Menu>();
            ew.eq("type", 0);
            list = this.menuServiceImpl.selectList(ew);
            ajaxJson.setObj(list);
                ajaxJson.setSuccess(true);
                ajaxJson.setMsg("保存成功");
        } catch (Exception e) {
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("保存失败");
            e.printStackTrace();
        } finally {
        }
        String text = JacksonUtil.toJson(ajaxJson);
        return text;
    }
    @RequestMapping(value = "/view.data", method = RequestMethod.GET)
    @ResponseBody
    public String getView(String keyword, int type, int index, int size) {
        AjaxJson ajaxJson = new AjaxJson();

        Page<Plat_Menu> page = null;
        int total = 0;
        try {
            EntityWrapper ew = new EntityWrapper();
            page = new Page<Plat_Menu>(index, size);

            if (!StringUtils.isEmpty(keyword)) {
                ew.like("name", keyword);
                //ew(ew.like("", keyword));
            }
            page = menuServiceImpl.selectPage(page,ew);
            total = menuServiceImpl.selectCount(ew);
            if (total != -1) {
                ajaxJson.setSuccess(true);
                ajaxJson.setMsg("查询成功");
            } else {
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("没有数据");
            }
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("新增失败");
            e.printStackTrace();
        } finally {
        }
        PageResult pr = new PageResult(total, size, index, page.getRecords());
        String text = JacksonUtil.toJson(pr);
        return text;
    }
}
