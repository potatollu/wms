package cn.wolfcode.wms.web.interceptor;

import cn.wolfcode.wms.annotation.RequiredPermission;
import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.exception.SecurityException;
import cn.wolfcode.wms.util.PermissionUtil;
import cn.wolfcode.wms.util.UserContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

public class SecurityInterceptor extends HandlerInterceptorAdapter{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Employee emp = UserContext.getCurrentuser();
        if (emp.isAdmin()){
            return true;
        }
        HandlerMethod h = (HandlerMethod) handler;
        Method method = h.getMethod();
        if (!method.isAnnotationPresent(RequiredPermission.class)){
            return true;
        }
        String exp = PermissionUtil.buildExpression(method);
        List<String> exps = UserContext.getExpression();
        if (exps.contains(exp)){
            return true;
        }
        throw new SecurityException();
    }
}
