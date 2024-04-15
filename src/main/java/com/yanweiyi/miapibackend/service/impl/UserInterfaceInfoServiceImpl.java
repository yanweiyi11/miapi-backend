package com.yanweiyi.miapibackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanweiyi.miapibackend.common.ErrorCode;
import com.yanweiyi.miapibackend.exception.BusinessException;
import com.yanweiyi.miapibackend.mapper.UserInterfaceInfoMapper;
import com.yanweiyi.miapibackend.service.UserInterfaceInfoService;
import com.yanweiyi.miapicommon.model.entity.UserInterfaceInfo;
import org.springframework.stereotype.Service;

/**
 * 用户关联接口信息服务实现类
 */
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo> implements UserInterfaceInfoService {

    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean b) {
        if (userInterfaceInfo == null || userInterfaceInfo.getId() <= 0 || userInterfaceInfo.getUserId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (userInterfaceInfo.getLeftNum() < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "剩余次数不能小于0");
        }

    }
}




