/**
 * Copyright (C), 2015-2019, 重庆新媒农信科技有限公司
 * FileName: UserController
 * Author:   张玉俊
 * Date:     2019/8/16 9:41
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package pers.jun.exceldemo.controller;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import pers.jun.exceldemo.pojo.User;
import pers.jun.exceldemo.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author 张玉俊
 * @create 2019/8/16
 * @since 1.0.0
 */

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/runtimeException")
    public String runtimeException() {
        throw new RuntimeException();
    }

    @RequestMapping("/initBinder")
    @ResponseBody
    public Date initBinder(Date date) {
        return date;
    }

    @RequestMapping("/globalModelAttribute")
    @ResponseBody
    public Object globalModelAttribute(@RequestParam("id")int id,
                                       @ModelAttribute("name") String name){
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("modelAttribute",name);
        return map;
    }

    @RequestMapping("/index")
    public String index(Model model){
        List<User> users = userService.selectUsers();

        System.out.println(users);
        model.addAttribute("users",users);

        return "index";
    }

    /**
     * 导出
     * @param response
     */
    @RequestMapping("/export")
    public void expot(HttpServletResponse response) throws IOException {

        List<User> users = userService.selectUsers();

        //创建一个excel文件
        HSSFWorkbook wb = new HSSFWorkbook();

        //创建一个excel的sheet
        HSSFSheet sheet = wb.createSheet("获取excel获取表格");

        HSSFRow row = null;

        //创建一行
        row = sheet.createRow(0);
        row.setHeight((short) (26.25*20));
        //为第一行单元格设置默认值
        row.createCell(0).setCellValue("用户信息列表");

        /*
         *合并单元格
         *    第一个参数：第一个单元格的行数（从0开始）
         *    第二个参数：第二个单元格的行数（从0开始）
         *    第三个参数：第一个单元格的列数（从0开始）
         *    第四个参数：第二个单元格的列数（从0开始）
         */
        CellRangeAddress rowRegion = new CellRangeAddress(0,0,0,2);
        sheet.addMergedRegion(rowRegion);

        /*
        动态获取数据库列 sql语句 select COLUMN_NAME from INFORMATION_SCHEMA.Columns where table_name='user' and table_schema='test'
         * 第一个table_name 表名字
         * 第二个table_name 数据库名称
         */
        row = sheet.createRow(1);
        row.setHeight((short) (22.50*22));
        //为第一个单元格设置值
        row.createCell(0).setCellValue("用户ID");
        //为第二个单元格设置值
        row.createCell(1).setCellValue("用户名");
        //为第三个单元格设置值
        row.createCell(2).setCellValue("用户密码");

        //将查询到的user集合添加到excel中
        for (int i = 0; i < users.size(); i++) {

            User user = users.get(i);
            row = sheet.createRow(i + 2);
            row.createCell(0).setCellValue(user.getUid());
            row.createCell(1).setCellValue(user.getUsername());
            row.createCell(2).setCellValue(user.getPassword());

        }
        sheet.setDefaultRowHeight((short) (16.5 * 20));

        //列宽自适应
        for (int i = 0; i < 13; i++) {
            sheet.autoSizeColumn(i);
        }

        response.setContentType("application/vnd.ms-excel;charset=UTF-8");

        //默认excel文件名
        response.setHeader("Content-disposition","attachment;filename=user.xls");

        //ServletOutputStream outputStream = response.getOutputStream();
        OutputStream os = response.getOutputStream();
        wb.write(os);
        os.flush();
        os.close();

    }


    @RequestMapping("/import")
    public String exImport(@RequestParam(value = "filename")MultipartFile file, HttpSession session){

        boolean a = false;
        String originalFilename = file.getOriginalFilename();

        try {
            a = userService.batchImport(originalFilename, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:index";
    }
}