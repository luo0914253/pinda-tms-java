package com.itheima.pinda.service.truck.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.pinda.common.CustomIdGenerator;
import com.itheima.pinda.entity.base.PdTruckTypeGoodsType;
import com.itheima.pinda.mapper.truck.PdTruckTypeGoodsTypeMapper;
import com.itheima.pinda.service.truck.IPdTruckTypeGoodsTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PdTruckTypeGoodsTypeServiceImpl extends ServiceImpl<PdTruckTypeGoodsTypeMapper, PdTruckTypeGoodsType> implements IPdTruckTypeGoodsTypeService {
    @Autowired
    private CustomIdGenerator idGenerator;
    /**
     * 批量添加车辆类型与货物类型关联
     *
     * @param truckTypeGoodsTypeList 车辆类型与货物类型信息
     */
    @Override
    public void batchSave(List<PdTruckTypeGoodsType> truckTypeGoodsTypeList) {
        truckTypeGoodsTypeList.forEach(pdTruckTypeGoodsType -> {
            pdTruckTypeGoodsType.setId(idGenerator.nextId(pdTruckTypeGoodsType)+"");
        });
        saveBatch(truckTypeGoodsTypeList);
    }

    /**
     * 获取车辆类型与货物类型关联
     *
     * @param truckTypeId 车辆类型id
     * @param goodsTypeId 货物类型id
     * @return 车辆类型与货物类型关联
     */
    @Override
    public List<PdTruckTypeGoodsType> findAll(String truckTypeId, String goodsTypeId) {
        LambdaQueryWrapper<PdTruckTypeGoodsType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(truckTypeId)){
            lambdaQueryWrapper.eq(PdTruckTypeGoodsType::getTruckTypeId,truckTypeId);
        }
        if (StringUtils.isNotBlank(goodsTypeId)){
            lambdaQueryWrapper.eq(PdTruckTypeGoodsType::getGoodsTypeId,goodsTypeId);
        }
        return baseMapper.selectList(lambdaQueryWrapper);
    }
}
