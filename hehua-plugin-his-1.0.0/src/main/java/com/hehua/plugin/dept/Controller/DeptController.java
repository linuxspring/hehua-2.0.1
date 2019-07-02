package com.hehua.plugin.deptController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hehua.core.conf.DataSource;
import com.hehua.core.conf.DataSourceEnum;
import com.hehua.plugin.dept.entity.Dept;
import com.hehua.plugin.dept.service.impl.DeptServiceImpl;
import com.hehua.plugin.user.service.impl.UserServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * 运调大屏接口，可跨越
 */
//@CrossOrigin(origins = "172.16.3.224:8180", methods = { RequestMethod.GET,RequestMethod.POST})
@CrossOrigin(origins = "*",maxAge = 3600) //处理跨越问题
@Controller
@RequestMapping(value = "/dept")
public class DeptController {
    @InitBinder
    public void initDataBinder(WebDataBinder binder) {

        DatePropertyEditor propertyEditor = new DatePropertyEditor();
        propertyEditor.setFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, propertyEditor);
    }

    @Resource
    @Autowired(required = true)
    private DeptServiceImpl deptServiceImpl;

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    @ResponseBody
    public String findByMenu(String name) {

        return deptServiceImpl.findByMenu(name);
    }

    @RequestMapping(value = "/del.data", method = RequestMethod.GET)
    @ResponseBody
    public String delMenu(String ids, int type) {
        AjaxJson ajaxJson = new AjaxJson();
        StringTokenizer tokenizer = new StringTokenizer(ids, ",");
        try {
            while (tokenizer.hasMoreTokens()) {
                Long id = Long.valueOf(tokenizer.nextToken());
                if (type == 0) {
                    Plat_Dept menu = this.deptServiceImpl.selectById(id);
                    menu.isDeleted = 1;
                    this.deptServiceImpl.insertOrUpdate(menu);
                } else {
                    this.deptServiceImpl.deleteById(id);
                }
            }
            ajaxJson.setSuccess(true);
            ajaxJson.setMsg("删除成功");
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("删除失败");
        }


        String text = JacksonUtil.toJson(ajaxJson);
        return text;
    }

    @RequestMapping(value = "/save.data", method = RequestMethod.GET)
    @ResponseBody
    public String saveMenu(String json) {
        AjaxJson ajaxJson = new AjaxJson();
        Plat_Dept menu = JacksonUtil.fromJson(json, Plat_Dept.class);
        if (menu.getId() != null) {
            Plat_Dept oldDept = deptServiceImpl.selectById(menu.getId());
            oldDept.setDeptName(menu.getDeptName());
            oldDept.setDept_type(menu.getDept_type());
            oldDept.setManager(menu.getManager());
            oldDept.setDescription(menu.getDescription());
            oldDept.setTel(menu.getTel());

            boolean res = this.deptServiceImpl.insertOrUpdate(oldDept);
            if (res) {
                ajaxJson.setSuccess(true);
                ajaxJson.setMsg("保存成功");
            } else {
                ajaxJson.setMsg("保存失败");
                ajaxJson.setSuccess(false);
            }
        } else {
            boolean res = deptServiceImpl.insert(menu);
            menu.autoid = utils.toAutoid("D", 6, menu.getId());
            deptServiceImpl.insertOrUpdate(menu);
            if (res) {
                ajaxJson.setSuccess(true);
                ajaxJson.setMsg("新增成功");
            } else {
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("新增失败");
            }
        }
        String text = JacksonUtil.toJson(ajaxJson);
        return text;
    }

    @RequestMapping(value = "/view.data", method = RequestMethod.GET)
    @ResponseBody
    public String getDeptView(String keyword, int index, int size) {
        AjaxJson ajaxJson = new AjaxJson();
        Page<Plat_Dept> page = new Page<Plat_Dept>(index, size);
        EntityWrapper<Plat_Dept> ew = new EntityWrapper<Plat_Dept>();
        ew.orderBy("dept_id", false);

        page = deptServiceImpl.selectPage(page, ew);
        int total = deptServiceImpl.selectCount(ew);

        PageResult pr = new PageResult(total, size, index, page.getRecords());
        String text = JacksonUtil.toJson(pr);
        return text;
    }

    @RequestMapping(value = "/deptUsersView.data", method = RequestMethod.GET)
    @ResponseBody
    public String getDeptUsersView(String keyword, int type, int index, int size, Long roleid) {
        AjaxJson ajaxJson = new AjaxJson();
        Map<String, Object> condition = new HashMap<>();
        Page<Plat_User> page = new Page<Plat_User>(index, size);
        condition.put("deptid", roleid);
        if (!StringUtils.isEmpty(keyword)) {
            condition.put("fullname", keyword);
        }
        List<Plat_User> mList = deptServiceImpl.getDeptUsersView(page, condition);

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

    @RequestMapping(value = "/addUserToDept.data", method = RequestMethod.GET)
    @ResponseBody
    public String addUserToDept(int roleid, String userids, int type) {
        AjaxJson ajaxJson = new AjaxJson();

        String[] ids = userids.split(",");
        List<String> list = Arrays.asList(ids);
        Map<String, Object> condition = new HashMap<>();
        condition.put("dept_id", roleid);
        if (type == 0) {
            int total = 0;
            for (int i = 0; i < ids.length; i++) {
                String id = ids[i];
                condition.put("user_id", id);
                total += deptServiceImpl.addUsersToDept(condition);
            }
            if (total == ids.length) {
                ajaxJson.setMsg("添加用户成功");
                ajaxJson.setSuccess(true);
            } else {
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("添加用户失败");
            }
        } else {
            List<Long> uids = new ArrayList<>();
            for (int i = 0; i < ids.length; i++) {
                String id = ids[i];
                uids.add(Long.valueOf(id));
            }
            condition.put("userids", uids);
            int total = this.deptServiceImpl.removeUsersFromDept(condition);
            if (total >= ids.length) {
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

    @RequestMapping(value = "/getDeptUserTree.data", method = RequestMethod.GET)
    @ResponseBody
    public String getDeptUserTree(Long id, String userids, int type) {
        AjaxJson ajaxJson = new AjaxJson();
        List<DeptUserView> duvs = new ArrayList<>();
        List<Plat_Dept> depts = new ArrayList<>();
        EntityWrapper<Plat_Dept> ew = new EntityWrapper<>();
        ew.eq("pid", id);
        try {
            depts = deptServiceImpl.selectList(ew);
            for (int i = 0; i < depts.size(); i++) {
                Plat_Dept dept = depts.get(i);
                DeptUserView temp = new DeptUserView();
                temp.id = dept.getId();
                temp.cnname = dept.getDeptName();
                temp.autoid = dept.autoid;
                temp.isDept = true;
                temp.type = dept.getDept_type();
                temp.pid = dept.getPid();
                duvs.add(temp);
            }
            if (id == -1) {

            } else {
                Map<String, Object> condition = new HashMap<>();
                Page<Plat_User> page = new Page<Plat_User>(1, 999999);
                condition.put("deptid", id);
                condition.put("fullname", null);
                List<Plat_User> mList = deptServiceImpl.getDeptUsersView(page, condition);
                for (int i = 0; i < mList.size(); i++) {
                    Plat_User user = mList.get(i);
                    DeptUserView temp = new DeptUserView();
                    temp.id =100000+ user.getId();
                    temp.cnname = user.getFullname();
                    temp.autoid = user.autoid;
                    temp.isDept = false;
                    temp.type = user.type;

                    temp.pid = id;
                    duvs.add(temp);
                }
            }
            ajaxJson.setSuccess(true);
            ajaxJson.setMsg("新增成功");
            ajaxJson.setObj(duvs);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("新增失败");
            ajaxJson.setObj(new ArrayList<>());

        }

        String text = JacksonUtil.toJson(duvs);
        return text;
    }
}
