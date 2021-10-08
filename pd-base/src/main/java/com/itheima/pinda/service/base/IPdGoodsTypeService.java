package com.itheima.pinda.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.pinda.entity.base.PdGoodsType;
/**
 * 货物类型
 */
public interface IPdGoodsTypeService extends IService<PdGoodsType> {
    /**
     * 添加货物类型
     *
     * @param pdGoodsType 货物类型信息
     * @return 货物类型信息
     */
    PdGoodsType saveGoodsType(PdGoodsType pdGoodsType);
}