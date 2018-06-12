package cn.wolfcode.wms.util;

import java.lang.reflect.Method;

public abstract class PermissionUtil {
    public static String buildExpression(Method m){
        String clzName = m.getDeclaringClass().getName();
        return clzName + ":" + m.getName();
    }
}
