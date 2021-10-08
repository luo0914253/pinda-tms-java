package com.itheima.pinda.controller.base;

import com.itheima.pinda.DTO.base.GoodsTypeDto;
import com.itheima.pinda.entity.base.PdGoodsType;
import com.itheima.pinda.entity.base.PdTruckTypeGoodsType;
import com.itheima.pinda.service.base.IPdGoodsTypeService;
import com.itheima.pinda.service.truck.IPdTruckTypeGoodsTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 货物类型管理
 */
@RestController
@RequestMapping("base/goodsType")
@Api(tags = "货物类型管理")
public class GoodsTypeController {
    @Autowired
    private IPdGoodsTypeService pdGoodsTypeService;
    @Autowired
    private IPdTruckTypeGoodsTypeService pdTruckTypeGoodsTypeService;
    /**
     * 添加货物类型
     *
     * @param dto 货物类型信息
     * @return 货物类型信息
     */
    @PostMapping("")
    @ApiOperation(value = "添加货物类型")
    public GoodsTypeDto saveGoodsType(@RequestBody GoodsTypeDto dto) {
        PdGoodsType pdGoodsType = new PdGoodsType();
        BeanUtils.copyProperties(dto,pdGoodsType);
//      保存货物类型信息到货物类型表
        pdGoodsType = pdGoodsTypeService.saveGoodsType(pdGoodsType);
        String goodsTypeId = pdGoodsType.getId();
//      保存货物类型和车辆类型关联信息到关联表
        List<String> truckTypeIds = dto.getTruckTypeIds();
        if (truckTypeIds != null && truckTypeIds.size() >0){
            List<PdTruckTypeGoodsType> list = truckTypeIds.stream().map(truckTypeId -> {
                PdTruckTypeGoodsType pdTruckTypeGoodsType = new PdTruckTypeGoodsType();
                pdTruckTypeGoodsType.setGoodsTypeId(goodsTypeId);
                pdTruckTypeGoodsType.setTruckTypeId(truckTypeId);
                return pdTruckTypeGoodsType;
            }).collect(Collectors.toList());
            pdTruckTypeGoodsTypeService.batchSave(list);
        }
        BeanUtils.copyProperties(pdGoodsType,dto);
        return dto;
    }
}
