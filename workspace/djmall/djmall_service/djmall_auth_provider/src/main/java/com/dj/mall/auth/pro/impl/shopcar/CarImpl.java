package com.dj.mall.auth.pro.impl.shopcar;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.auth.pro.bo.CarBO;
import com.dj.mall.auth.pro.entity.shopcar.CarEntity;
import com.dj.mall.auth.pro.mapper.carshop.CarMapper;
import com.dj.mall.autr.api.dto.shopcar.CarDTO;
import com.dj.mall.autr.api.shopcar.CarApi;
import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.util.DozerUtil;

import java.util.List;

@Service
public class CarImpl extends ServiceImpl<CarMapper, CarEntity> implements CarApi {
    /**
     * 添加商品到购物车
     * @param carDTO 购物车信息
     * @throws BusinessException
     */
    @Override
    public void addShop(CarDTO carDTO) throws BusinessException {
        CarEntity entity = DozerUtil.map(carDTO, CarEntity.class);
        entity.setCheckStatus(0);
        QueryWrapper<CarEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", carDTO.getUserId())
                .eq("sku_id", carDTO.getSkuId());
        CarEntity carEntity = super.getOne(wrapper);
        if(carEntity != null){
            Integer count = carEntity.getCount() + entity.getCount();
            UpdateWrapper<CarEntity> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("count", count).eq("sku_id", carDTO.getSkuId());
            super.update(updateWrapper);
        } else {
          super.save(entity);
        }
    }

    /**
     * 更新勾选状态
     * @param carId 根据ID修改
     * @param checkStatus 勾选状态
     */
    @Override
    public void updateStatus(Integer carId, Integer checkStatus) {
        UpdateWrapper<CarEntity> wrapper = new UpdateWrapper<>();
        if(checkStatus == 1){
            wrapper.set("check_status", 0).eq("id", carId);
        }else {
            wrapper.set("check_status", 1).eq("id", carId);
        }
        super.update(wrapper);
    }

    /**
     *
     * @param carId 根据ID删除
     * @throws BusinessException
     */
    @Override
    public void delCarByCarId(Integer carId) throws BusinessException {
        super.removeById(carId);
    }

    /**
     * 根据购物车ID查询已勾选商品
     * @param id 购物车ID
     * @return
     * @throws BusinessException
     */
    @Override
    public List <CarDTO> findCarById(Integer id) throws BusinessException {
        List<CarBO> carBOList = super.baseMapper.findCarById(id);
        return DozerUtil.mapList(carBOList, CarDTO.class);
    }
}
