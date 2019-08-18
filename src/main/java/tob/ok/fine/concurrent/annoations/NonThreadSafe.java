package tob.ok.fine.concurrent.annoations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @program
 * @description: 标记线程【不安全类】
 * @author: zhengLin
 * @date 2019/08/18
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface NonThreadSafe {

    String value() default "";

}
