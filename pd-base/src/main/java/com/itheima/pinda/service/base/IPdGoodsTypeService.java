package com.itheima.pinda.service.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.pinda.entity.base.PdGoodsType;

import java.util.List;

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

    /**
     * 查询所有货物类型
     * @return
     */
    List<PdGoodsType> findAll();

    /**
     * 获取分页货物类型数据
     * @param page 页码
     * @param pageSize 页尺寸
     * @return 分页货物数据
     */
    IPage<PdGoodsType> findByPage(Integer page, Integer pageSize, String name, String truckTypeId, String truckTypeName);
}
