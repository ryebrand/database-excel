/**
 * Copyright (C), 2015-2019, 重庆新媒农信科技有限公司
 * FileName: ExcelImportUtils
 * Author:   张玉俊
 * Date:     2019/8/16 9:45
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package pers.jun.exceldemo.util;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author 张玉俊
 * @create 2019/8/16
 * @since 1.0.0
 */

public class ExcelImportUtils {


    // @描述：是否是2003的excel，返回true是2003
    public static boolean isExcel2003(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    //@描述：是否是2007的excel，返回true是2007
    public static boolean isExcel2007(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    /**
     * 验证EXCEL文件
     * @param filePath
     * @return
     */
    public static boolean validateExcel(String filePath){
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))){
            return false;
        }
        return true;
    }
}

