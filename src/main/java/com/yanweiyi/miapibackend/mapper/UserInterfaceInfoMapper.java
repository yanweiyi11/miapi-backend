package com.yanweiyi.miapibackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yanweiyi.miapicommon.model.entity.UserInterfaceInfo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    @Select("select interfaceInfoId, sum(totalNum) as totalNum from user_interface_info group by interfaceInfoId order by totalNum desc limit #{limit}")
    List<UserInterfaceInfo> listTopInvokeInterface(int limit);

}




