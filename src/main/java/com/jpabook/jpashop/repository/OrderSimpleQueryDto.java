package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.Address;
import com.jpabook.jpashop.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;


//DTO는 엔티티를 참조해도 괜찮다
@Data
public class OrderSimpleQueryDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public OrderSimpleQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }

}

