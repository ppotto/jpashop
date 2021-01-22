package com.jpabook.jpashop.repository;


import com.jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter //레파지토리 에다가 만 드 나요,,,,???
public class OrderSearch {

    private String memberName;//회원 이름
    private OrderStatus orderStatus; //주문 상태 (ORDER, CANCEL
}
