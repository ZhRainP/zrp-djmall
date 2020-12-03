package com.dj.p2p;

import com.dj.p2p.pojo.buycount.BuyCount;
import com.dj.p2p.pojo.pricemanage.AccountManage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author lindemin
 */
@SpringBootApplication
@EnableEurekaClient // 开启客户端注解
@EnableFeignClients // 开启服务之间调用  采用feign
@EnableTransactionManagement
@MapperScan("com.dj.p2p.mapper")
public class PriceStart {

    public static void main(String[] args) {
        SpringApplication.run(PriceStart.class,args);
    }
//public static void main(String[] args) {
//    List<BuyCount> list = new ArrayList<>();
//    BuyCount buyCount = new BuyCount()
//            .setPrice(new BigDecimal("30500.00"))
//            .setSubjectMoney(new BigDecimal("50000.00"))
//            .setYearInterest(new BigDecimal("8.00"))
//            .setSubjectId(3);
//    BuyCount buyCount1 = new BuyCount()
//            .setPrice(new BigDecimal("20500.00"))
//            .setYearInterest(new BigDecimal("8.00"))
//            .setSubjectMoney(new BigDecimal("50000.00"))
//            .setSubjectId(3);
//    list.add(buyCount);
//    list.add(buyCount1);
//    //总代收金额
//    BigDecimal daishou = new BigDecimal("0.00");
//    for (BuyCount count : list) {
//        //利息加本金总额
//        count.setInterestAndSubjectMoney(count.getSubjectMoney().add(count.getSubjectMoney().multiply(count.getYearInterest())
//                .multiply(new BigDecimal("0.01").setScale(2,BigDecimal.ROUND_HALF_UP))));
//        //利息
//        count.setInterest(count.getSubjectMoney().multiply(count.getYearInterest())
//                .multiply(new BigDecimal("0.01")).setScale(2,BigDecimal.ROUND_HALF_UP));
//        //代收金额
//        daishou = daishou.add(count.getPrice().divide(count.getSubjectMoney(),2,BigDecimal.ROUND_HALF_UP).multiply(count
//                .getInterest()).setScale(2,BigDecimal.ROUND_HALF_UP));
//    }
//    System.out.println(daishou);
//}
}
