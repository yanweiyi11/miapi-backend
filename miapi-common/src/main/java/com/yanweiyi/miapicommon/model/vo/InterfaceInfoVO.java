package com.yanweiyi.miapicommon.model.vo;

import com.yanweiyi.miapicommon.model.entity.InterfaceInfo;
import lombok.Data;

/**
 * 接口信息封装视图
 */
@Data
public class InterfaceInfoVO extends InterfaceInfo {

    private static final long serialVersionUID = -4536212914788337342L;

    /**
     * 调用次数
     */
    private int totalNum;
}
