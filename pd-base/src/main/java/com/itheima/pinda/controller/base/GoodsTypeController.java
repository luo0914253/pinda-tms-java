package com.itheima.pinda.controller.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itheima.pinda.DTO.base.GoodsTypeDto;
import com.itheima.pinda.common.utils.PageResponse;
import com.itheima.pinda.entity.base.PdGoodsType;
import com.itheima.pinda.entity.base.PdTruckTypeGoodsType;
import com.itheima.pinda.service.base.IPdGoodsTypeService;
import com.itheima.pinda.service.truck.IPdTruckTypeGoodsTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * @param goodsTypeDto 货物类型信息
     * @return 货物类型信息
     */
    @PostMapping("")
    @ApiOperation(value = "添加货物类型")
    public GoodsTypeDto saveGoodsType(@RequestBody GoodsTypeDto goodsTypeDto) {
        PdGoodsType pdGoodsType = new PdGoodsType();
        BeanUtils.copyProperties(goodsTypeDto,pdGoodsType);
//      保存货物类型信息到货物类型表
        pdGoodsType = pdGoodsTypeService.saveGoodsType(pdGoodsType);
        String goodsTypeId = pdGoodsType.getId();
//      保存货物类型和车辆类型关联信息到关联表
        List<String> truckTypeIds = goodsTypeDto.getTruckTypeIds();
        if (truckTypeIds != null && truckTypeIds.size() >0){
            List<PdTruckTypeGoodsType> list = truckTypeIds.stream().map(truckTypeId -> {
                PdTruckTypeGoodsType pdTruckTypeGoodsType = new PdTruckTypeGoodsType();
                pdTruckTypeGoodsType.setGoodsTypeId(goodsTypeId);
                pdTruckTypeGoodsType.setTruckTypeId(truckTypeId);
                return pdTruckTypeGoodsType;
            }).collect(Collectors.toList());
            pdTruckTypeGoodsTypeService.batchSave(list);
        }
        BeanUtils.copyProperties(pdGoodsType,goodsTypeDto);
        return goodsTypeDto;
    }
    /**
     * 根据id查询货物类型
     *
     * @param id 货物类型id
     * @return 货物类型信息
     */
    @GetMapping("/{id}")
    public GoodsTypeDto fineById(@PathVariable(name = "id") String id) {
        PdGoodsType pdGoodsType = pdGoodsTypeService.getById(id);
        GoodsTypeDto goodsTypeDto = new GoodsTypeDto();
        BeanUtils.copyProperties(pdGoodsType,goodsTypeDto);
        List<PdTruckTypeGoodsType> pdTruckTypeGoodsTypeList = pdTruckTypeGoodsTypeService.findAll(null, id);
        if (pdTruckTypeGoodsTypeList != null && pdTruckTypeGoodsTypeList.size() > 0){
            List<String> truckTypeId = pdTruckTypeGoodsTypeList.stream().map(pdTruckTypeGoodsType ->
                pdTruckTypeGoodsType.getTruckTypeId()
            ).collect(Collectors.toList());
            goodsTypeDto.setTruckTypeIds(truckTypeId);
        }
        return goodsTypeDto;
    }
    /**
     * 查询所有货物类型
     * @return
     */
    @GetMapping("/all")
    @ApiOperation(value = "查询所有货物类型")
    public List<GoodsTypeDto> findAll() {
        List<PdGoodsType> goodsTypes = pdGoodsTypeService.findAll();
        if (CollectionUtils.isNotEmpty(goodsTypes)){
            List<GoodsTypeDto> goodsTypeDtos = goodsTypes.stream().map(goodsType -> {
                GoodsTypeDto goodsTypeDto = new GoodsTypeDto();
                BeanUtils.copyProperties(goodsType, goodsTypeDto);
                return goodsTypeDto;
            }).collect(Collectors.toList());
            return goodsTypeDtos;
        }
        return null;
    }
    /**
     * 获取分页货物类型数据
     *
     * @param page          页码
     * @param pageSize      页尺寸
     * @param name          货物类型名称
     * @param truckTypeId   车辆类型Id
     * @param truckTypeName 车辆类型名称
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "获取分页货物类型数据")
    public PageResponse<GoodsTypeDto> findByPage(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "pageSize") Integer pageSize,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "truckTypeId", required = false) String truckTypeId,
            @RequestParam(name = "truckTypeName", required = false) String truckTypeName){
        IPage<PdGoodsType> goodsTypePage = pdGoodsTypeService.findByPage(page, pageSize, name, truckTypeId, truckTypeName);
        List<PdGoodsType> goodsTypePageRecords = goodsTypePage.getRecords();
        if (CollectionUtils.isNotEmpty(goodsTypePageRecords)){
            List<GoodsTypeDto> goodsTypeDtoList = goodsTypePageRecords.stream().map(goodsType -> {
//              获取车辆类型与货物类型关联
                List<PdTruckTypeGoodsType> truckTypeGoodsTypes = pdTruckTypeGoodsTypeService.findAll(null, goodsType.getId());
                List<String> truckTypeIds = truckTypeGoodsTypes.stream().map(pdTruckTypeGoodsType ->
                        pdTruckTypeGoodsType.getTruckTypeId()).collect(Collectors.toList());
                GoodsTypeDto goodsTypeDto = new GoodsTypeDto();
                BeanUtils.copyProperties(goodsType, goodsTypeDto);
                goodsTypeDto.setTruckTypeIds(truckTypeIds);
                return goodsTypeDto;
            }).collect(Collectors.toList());
            return PageResponse.<GoodsTypeDto>builder().items(goodsTypeDtoList).counts(goodsTypePage.getTotal())
                    .page(page).pagesize(pageSize).build();
        }
        return null;
    }
    /**
     * 获取货物类型列表
     *
     * @return 货物类型列表
     */
    @GetMapping("")
    @ApiOperation(value = "获取货物类型列表")
    public List<GoodsTypeDto> findAll(@RequestParam(name = "ids", required = false) List<String> ids) {
        List<PdGoodsType> pdGoodsTypeList = pdGoodsTypeService.findAll(ids);
        if (CollectionUtils.isNotEmpty(pdGoodsTypeList)){
            List<GoodsTypeDto> goodsTypeDtos = pdGoodsTypeList.stream().map(pdGoodsType -> {
                List<PdTruckTypeGoodsType> pdTruckTypeGoodsTypeList = pdTruckTypeGoodsTypeService.findAll(null, pdGoodsType.getId());
                List<String> truckTypeIds = pdTruckTypeGoodsTypeList.stream().map(pdTruckTypeGoodsType ->pdTruckTypeGoodsType.getTruckTypeId()).collect(Collectors.toList());
                GoodsTypeDto dto = new GoodsTypeDto();
                BeanUtils.copyProperties(pdGoodsType, dto);
                dto.setTruckTypeIds(truckTypeIds);
                return dto;
            }).collect(Collectors.toList());
            return goodsTypeDtos;
        }
        return null;
    }
}
