package com.yanweiyi.miapibackend.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yanweiyi.miapibackend.common.ErrorCode;
import com.yanweiyi.miapibackend.exception.BusinessException;
import com.yanweiyi.miapibackend.mapper.UserMapper;
import com.yanweiyi.miapicommon.model.entity.User;
import com.yanweiyi.miapicommon.service.InnerUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class InnerUserServiceImpl implements InnerUserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 从数据库中查是否已分配给用户密钥（accessKey）
     *
     * @param accessKey
     * @return
     */
    @Override
    public User getInvokeUser(String accessKey) {
        if (StringUtils.isBlank(accessKey)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccessKey, accessKey);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "未分配给用户密钥");
        }
        return user;
    }
}
