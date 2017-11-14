package com.wyj.treasure.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wangyujie
 *         on 2017/11/14.17:59
 *         TODO
 */
 /*放在方法上面*/
@Target(ElementType.METHOD)
/*什么时候检测*/
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionSuccess {
    public int requestCode();//请求码

}
