/**
 * Copyright (C), 2015-2019, 重庆新媒农信科技有限公司
 * FileName: UserServiceImpl
 * Author:   张玉俊
 * Date:     2019/8/16 9:23
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package pers.jun.exceldemo.service.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pers.jun.exceldemo.exception.MyException;
import pers.jun.exceldemo.mapper.UserMapper;
import pers.jun.exceldemo.pojo.User;
import pers.jun.exceldemo.service.UserService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author 张玉俊
 * @create 2019/8/16
 * @since 1.0.0
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectUsers() {
        List<User> users = userMapper.selectUsers();
        return users;
    }

    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public boolean batchImport(String fileName, MultipartFile file) throws Exception {

        boolean notNull = false;

        List<User> users = new ArrayList<>();
        //检验文件名是否正确
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new MyException("上传文件格式不正确");
        }

        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }

        InputStream inputStream = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(inputStream);
        }else {
            wb = new XSSFWorkbook(inputStream);
        }

        Sheet sheet = wb.getSheetAt(0);
        if(sheet != null)
            notNull = true;

        User user;
        for (int i = 2; i < sheet.getLastRowNum(); i++) {

            //得到每一行
            Row row = sheet.getRow(i);
            if(row == null){
                continue;
            }

            //判断uid格式
            user = new User();
            if (row.getCell(0).getCellType() != 1) {
                throw new MyException("导入失败(第"+(i+1)+"行,用户名请设为文本格式)");
            }

            //得到username
            String username = row.getCell(0).getStringCellValue();

            if (StringUtils.isEmpty(username)) {
                throw new MyException("导入失败（第"+(i+1)+"行，用户名未填写！");
            }


            //得到密码并校验
            row.getCell(1).setCellType(CellType.STRING);
            String password = row.getCell(1).getStringCellValue();
            if (StringUtils.isEmpty(password)) {
                throw new MyException("导入失败（第"+(i+1) + "行，密码未填写！");
            }

            //完整的一次循环，组成一个user对象
            user.setUsername(username);
            user.setPassword(password);
            users.add(user);

        }


        for (User userRecord : users) {
            String username = userRecord.getUsername();

            int r = userMapper.selectByName(username);
            if (r == 0) {
                userMapper.addUser(userRecord);
                System.out.println("插入 "+ userRecord);
            }
            else {
                userMapper.updateUserByName(userRecord);
                System.out.println("更新 " + userRecord);
            }
        }
        return notNull;
    }
}