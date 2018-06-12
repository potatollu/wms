package cn.wolfcode.wms.util;

import cn.wolfcode.wms.domain.Employee;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.util.List;

public abstract class UserContext {

    public static final String USER_KEY = "EMPLYEE_IN_SESSION";
    public static final String EXPS_KEY = "EXPS_IN_SESSION";

    private static HttpSession getSession(){
        ServletRequestAttributes attributes = (ServletRequestAttributes)
                                    RequestContextHolder.getRequestAttributes();
        return attributes.getRequest().getSession();
    }

    public static void setCurrentUser(Employee e){
        getSession().setAttribute(USER_KEY,e);
    }

    public static Employee getCurrentuser(){
        return (Employee) getSession().getAttribute(USER_KEY);
    }

    public static void setExpression(List<String> exps){
        getSession().setAttribute(EXPS_KEY,exps);
    }

    public static List<String> getExpression(){
        return (List<String>) getSession().getAttribute(EXPS_KEY);
    }

}
