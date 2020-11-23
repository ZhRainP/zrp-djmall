package com.dj.mall.order.pro.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.autr.api.address.AddressApi;
import com.dj.mall.autr.api.dto.address.AddressDTO;
import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.order.api.OrderApi;
import com.dj.mall.order.api.dto.OrderDTO;
import com.dj.mall.order.pro.bo.CarOrderBO;
import com.dj.mall.order.pro.entity.OrderDetail;
import com.dj.mall.order.pro.entity.OrderEntity;
import com.dj.mall.order.pro.entity.OrderInfo;
import com.dj.mall.order.pro.mapper.OrderMapper;
import com.dj.mall.order.pro.service.OrderDetailService;
import com.dj.mall.order.pro.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderApiImpl extends ServiceImpl<OrderMapper, OrderEntity> implements OrderApi {
    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Reference
    private AddressApi addressApi;
    @Override
    public void insertOrder(OrderDTO orderDTO) throws BusinessException {
        //根据用户ID获取用户下单信息
        List<CarOrderBO> carOrderBOList = super.baseMapper.findCarOrderByUserId(orderDTO.getBuyerId());
        //收获地址信息
        AddressDTO addressDTO = addressApi.findAddress(orderDTO.getAddressId());
        //父订单号
        String format = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        int rand = new Random().nextInt(899) + 100;
        String orderNo = "DJ" + format + rand;
        // 父订单总金额
        BigDecimal totalMoney = new BigDecimal(0);
        // 父订单实付金额
        BigDecimal totalPayMoney = new BigDecimal(0);
        // 总运费
        BigDecimal totalFreight = new BigDecimal(0);
        // 总购买数量
        Integer totalBuyCount = 0;
        // 创建子订单与详情
        List<OrderInfo> childOrderList = new ArrayList<>();
        List<OrderDetail> orderDetailList = new ArrayList<>();
        // 2.订单拆分 根据商品Id拆分 商品Id sku信息
        Map<Integer, List<CarOrderBO>> tradeMap = carOrderBOList.stream()
                .collect(Collectors.groupingBy(CarOrderBO::getProductId));
        for (Map.Entry<Integer, List<CarOrderBO>> entry : tradeMap.entrySet()) {
            // 子订单号
            String format1 = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            int rand1 = new Random().nextInt(899) + 100;
            String orderNo1 = "DJ" + format1 + rand1;
            // 子订单总金额
            BigDecimal childTotalMoney = new BigDecimal(0);
            // 子订单实付金额
            BigDecimal childTotalPayMoney = new BigDecimal(0);
            // 子订单总运费
            BigDecimal childTotalFreight = new BigDecimal(0);
            Integer childTotalBuyCount = 0;
            // 订单详情组装 运费算N次
            for (CarOrderBO orderTradeBO : entry.getValue()) {
                // 订单总金额 = 原价格*数量+运费
                childTotalMoney = childTotalMoney.add(orderTradeBO.getSkuPrice().multiply(new BigDecimal(orderTradeBO.getCount())));
                // 折扣价
                BigDecimal rateMoney = orderTradeBO.getSkuPrice().multiply(new BigDecimal((100 - orderTradeBO.getSkuRate()) * 0.01)).setScale(2, BigDecimal.ROUND_HALF_UP);
                // 实付总金额 = 折扣价*数量+运费
                childTotalPayMoney = childTotalPayMoney.add(rateMoney.multiply(new BigDecimal(orderTradeBO.getCount())));
                // 数据映射
                OrderDetail orderDetail = DozerUtil.map(orderTradeBO, OrderDetail.class);
                orderDetail.setParentOrderNo(orderNo);
                orderDetail.setProductId(entry.getKey());
                orderDetail.setChildOrderNo(orderNo1);
                orderDetail.setBuyerId(orderDTO.getBuyerId());
                orderDetail.setCreateTime(LocalDateTime.now());
                orderDetail.setSkuId(orderTradeBO.getSkuId());
                orderDetail.setSkuInfo(orderTradeBO.getSkuName());
                orderDetail.setSkuPrice(orderTradeBO.getSkuPrice());
                orderDetail.setSkuRate(orderTradeBO.getSkuRate());
                orderDetail.setBuyCount(orderTradeBO.getCount());
                orderDetail.setPayMoney(childTotalPayMoney);
                orderDetailList.add(orderDetail);
            }
            // 子订单数据组装
            childOrderList.add(OrderInfo.builder()
                    .orderNo(orderNo1).parentOrderNo(orderNo).buyerId(orderDTO.getBuyerId()).productId(entry.getKey())
                    .totalMoney(childTotalMoney).totalPayMoney(childTotalPayMoney).totalFreight(childTotalFreight).totalBuyCount(childTotalBuyCount)
                    .payType(orderDTO.getPayType()).orderStatus("1").createTime(LocalDateTime.now())
                    .receiverProvince(addressDTO.getProvice()).receiverCity(addressDTO.getCity())
                    .receiverCounty(addressDTO.getCounties()).receiverName(addressDTO.getUserName())
                    .receiverPhone(addressDTO.getPhone()).receiverDetail(addressDTO.getAddress())
                    .build());
            // 父订单金额计算
            totalMoney = totalMoney.add(childTotalMoney);
            totalPayMoney = totalPayMoney.add(childTotalPayMoney);
            totalFreight = totalFreight.add(childTotalFreight);
            totalBuyCount += childTotalBuyCount;
        }
        // 创建父订单
        OrderEntity orderEntity = OrderEntity.builder()
                .orderNo(orderNo).buyerId(orderDTO.getBuyerId())
                .totalMoney(totalMoney).totalPayMoney(totalPayMoney).totalFreight(totalFreight).totalBuyCount(totalBuyCount)
                .payType(orderDTO.getPayType()).orderStatus("1").createTime(LocalDateTime.now())
                .receiverProvince(addressDTO.getProvice()).receiverCity(addressDTO.getCity())
                .receiverCounty(addressDTO.getCounties()).receiverName(addressDTO.getUserName())
                .receiverPhone(addressDTO.getPhone()).receiverDetail(addressDTO.getAddress())
                .build();
        // 创建主订单
        super.save(orderEntity);
        // 创建子订单
        orderInfoService.saveBatch(childOrderList);
        // 订单详情
        orderDetailService.saveBatch(orderDetailList);
    }
}
