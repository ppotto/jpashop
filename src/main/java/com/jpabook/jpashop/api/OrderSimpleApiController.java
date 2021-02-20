package com.jpabook.jpashop.api;

import com.jpabook.jpashop.domain.Address;
import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.domain.OrderStatus;
import com.jpabook.jpashop.repository.OrderRepository;
import com.jpabook.jpashop.repository.OrderSearch;
import com.jpabook.jpashop.repository.OrderSimpleQueryDto;
import com.jpabook.jpashop.service.OrderService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Order
 * Order -> Member
 * Order -> Delivery
 * X To One 관계 (ManyToOne, OneToOne)
 */

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    // Orders->Members -> Orders... 무한루프
    // 양방향 관계에서 한쪽을 JsonIgnore 해주기
    // 강제 레이지 로딩을 시켜서 로딩하는 경우 엔티티를 고대로 노출하게 되므로 안좋음
    // 또한 api 스펙상 불필요한 엔티티 노출을 하게되고, 성능사 문제도 생기는 것.
    // 프록시로 있는걸 getName같은 실제 값을 가져오기 하면, Lazy 강제 초기화를 할 수 있음.
    // 엔티티를 직접 노출하면 정말 많은 문제가 발생한다.
    // DTO 로 변환해서 반환하는 것이 가장 좋은 방법이다.
    // 지연로딩을 피하기위해 즉시로딩으로 하면 안됨!!!
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        return all;
    }


    //이렇게 리스트로 반환하면 안되고 Result 로 한번 감싸야함!
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
        return orderRepository.findAllByString(new OrderSearch()).stream().map(order -> new SimpleOrderDto(order))
                .collect(Collectors.toList());
    }

    @Data
    private class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName(); // LAZY 초기화( 영속성 컨텍스트에서 찾고 없으면 가져와서 영속성 컨텍스트에 등록하는것)
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        return orders.stream().map(order -> new SimpleOrderDto(order)).collect(Collectors.toList());
    }


    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderRepository.findOrderDtos();
    }
}
