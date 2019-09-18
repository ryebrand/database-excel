/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: UserService
 * Author:   张玉俊
 * Date:     2019/8/16 9:23
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package pers.jun.exceldemo.service;

import org.springframework.web.multipart.MultipartFile;
import pers.jun.exceldemo.pojo.User;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author 石头
 * @create 2019/8/16
 * @since 1.0.0
 */
public interface UserService {

    List<User> selectUsers();


    boolean batchImport(String fileName, MultipartFile file) throws Exception;

}
