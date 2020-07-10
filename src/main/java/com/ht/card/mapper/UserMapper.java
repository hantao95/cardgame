package com.ht.card.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.card.entities.UserInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<UserInfo> {
    UserInfo getInfo(String id);

    UserInfo getRInfo(String id);
}
