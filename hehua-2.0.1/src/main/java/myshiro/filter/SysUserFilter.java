package myshiro.filter;


import com.hehua.plugin.system.service.Impl.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by Administrator on 2017/7/30.
 * IntelliJ IDEA 2017 of gzcss
 */
public class SysUserFilter extends PathMatchingFilter {
    @Autowired
    private UserServiceImpl userService;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
       // return super.onPreHandle(request, response, mappedValue);
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        request.setAttribute("CURRENT_USER",username);// userService.findByUsername(username));
        return true;
    }
}
