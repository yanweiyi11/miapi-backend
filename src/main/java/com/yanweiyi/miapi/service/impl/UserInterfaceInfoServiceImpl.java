package com.yanweiyi.miapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanweiyi.miapi.common.ErrorCode;
import com.yanweiyi.miapi.exception.BusinessException;
import com.yanweiyi.miapi.mapper.UserInterfaceInfoMapper;
import com.yanweiyi.miapi.model.entity.UserInterfaceInfo;
import com.yanweiyi.miapi.service.UserInterfaceInfoService;
import org.springframework.stereotype.Service;

/**
 * @author 27645
 * @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
 * @createDate 2024-04-08 20:33:05
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

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        if (interfaceInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        LambdaUpdateWrapper<UserInterfaceInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserInterfaceInfo::getInterfaceInfoId, interfaceInfoId);
        updateWrapper.eq(UserInterfaceInfo::getUserId, userId);
        updateWrapper.gt(UserInterfaceInfo::getLeftNum, 0);
        updateWrapper.setSql("leftNum = leftNum - 1, totalNum = totalNum + 1");
        return this.update(updateWrapper);
    }
}




