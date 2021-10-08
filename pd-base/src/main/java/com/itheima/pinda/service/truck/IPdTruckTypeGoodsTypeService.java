package com.itheima.pinda.service.truck;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.pinda.entity.base.PdTruckTypeGoodsType;

import java.util.List;
/**
 * 车辆类型与货物类型关联
 */
public interface IPdTruckTypeGoodsTypeService extends IService<PdTruckTypeGoodsType> {
    /**
     * 批量添加车辆类型与货物类型关联
     *
     * @param truckTypeGoodsTypeList 车辆类型与货物类型信息
     */
    void batchSave(List<PdTruckTypeGoodsType> truckTypeGoodsTypeList);
}