package com.ht.card.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_info")
public class UserInfo {
	@TableId("userid")
    private String userid;

    @TableField("username")
    private String username;
    
    @TableField("password")
    private String password;

    @TableField("remark")
    private String remark;

    private String queryInfo;
}
