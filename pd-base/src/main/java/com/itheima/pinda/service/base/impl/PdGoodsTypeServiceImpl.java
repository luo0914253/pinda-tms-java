package com.itheima.pinda.service.base.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.pinda.common.CustomIdGenerator;
import com.itheima.pinda.entity.base.PdGoodsType;
import com.itheima.pinda.mapper.base.PdGoodsTypeMapper;
import com.itheima.pinda.service.base.IPdGoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PdGoodsTypeServiceImpl extends ServiceImpl<PdGoodsTypeMapper, PdGoodsType> implements IPdGoodsTypeService {
    @Autowired
    private CustomIdGenerator idGenerator;
    /**
     * 添加货物类型
     *
     * @param pdGoodsType 货物类型信息
     * @return 货物类型信息
     */
    @Override
    public PdGoodsType saveGoodsType(PdGoodsType pdGoodsType) {
        pdGoodsType.setId(idGenerator.nextId(pdGoodsType)+"");
        baseMapper.insert(pdGoodsType);
        System.out.println("git");
        return pdGoodsType;
    }

    /**
     * 查询所有货物类型
     *
     * @return
     */
    @Override
    public List<PdGoodsType> findAll() {
        QueryWrapper<PdGoodsType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",1);
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 获取分页货物类型数据
     *
     * @param page          页码
     * @param pageSize      页尺寸
     * @param name
     * @param truckTypeId
     * @param truckTypeName
     * @return 分页货物数据
     */
    @Override
    public IPage<PdGoodsType> findByPage(Integer page, Integer pageSize, String name, String truckTypeId, String truckTypeName) {
        Page<PdGoodsType> ipage = new Page<>(page,pageSize);
        ipage.addOrder(OrderItem.asc("id"));
        ipage.setRecords(baseMapper.findByPage(ipage,name,truckTypeId,truckTypeName));
        return ipage;
    }
    @Override
    public List<PdGoodsType> findAll(List<String> ids) {
        LambdaQueryWrapper<PdGoodsType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (ids != null && ids.size() > 0) {
            lambdaQueryWrapper.in(PdGoodsType::getId, ids);
        }
        return baseMapper.selectList(lambdaQueryWrapper);
    }
}
