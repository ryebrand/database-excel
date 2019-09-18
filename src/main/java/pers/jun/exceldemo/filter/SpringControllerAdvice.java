/**
 * Copyright (C), 2015-2019, 重庆新媒农信科技有限公司
 * FileName: SpringControllerAdvice
 * Author:   张玉俊
 * Date:     2019/9/18 16:52
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package pers.jun.exceldemo.filter;

import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * 〈一句话功能简述〉<br> 
 * 〈https://blog.csdn.net/zxfryp909012366/article/details/82955259〉
 *
 * @author 张玉俊
 * @create 2019/9/18
 * @since 1.0.0
 */
@ControllerAdvice(basePackages = "pers.jun.exceldemo.controller")
public class SpringControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public String RuntimeExceptionHandler(RuntimeException ex) {
        ex.printStackTrace();
        return ex.toString();
    }

    /**
     * 在每次request请求进行参数转换时都会调用，用于判断指定的参数是否为其可以转换的参数
     * @param binder
     */
    @InitBinder
    public void GlobalInitBinder(WebDataBinder binder) {
        binder.addCustomFormatter(new DateFormatter("yyyy-MM-dd"));
    }

    /**
     * 声明在方法上，并且结合@ControllerAdvice，该方法将会在@ControllerAdvice所指定的范围内的所有接口
     * 方法执行之前执行，并且@ModelAttribute标注的方法的返回值还可以供给后续会调用的接口方法使用。
     * @return
     */
    @ModelAttribute(value = "name")
    public String GlobalModelAttribute() {
        System.out.println("global modelAttribute");
        return "global modelAttribute";
    }

}