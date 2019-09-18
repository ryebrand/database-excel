/**
 * Copyright (C), 2015-2019, 重庆新媒农信科技有限公司
 * FileName: User
 * Author:   张玉俊
 * Date:     2019/8/16 9:20
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package pers.jun.exceldemo.pojo;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author 张玉俊
 * @create 2019/8/16
 * @since 1.0.0
 */

public class User implements Serializable {

    private Integer uid;

    private String username;

    private String password;


    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

