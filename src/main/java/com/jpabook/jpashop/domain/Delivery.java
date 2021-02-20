package com.jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Getter
@Setter
@Entity
public class Delivery {
    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;


    @OneToOne(mappedBy = "delivery",  fetch = LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) //EnumType.ORDINAL (숫자로 들어감) 절대 사용 X
    private DeliveryStatus status; //READY, COMP
}
