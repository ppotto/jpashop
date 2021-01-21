package com.jpabook.jpashop.domain;


import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public class Address {

    private String city;
    private String street;
    private String zipcode;

    //얘를 함부로 new로 생성하면 안될때
    protected Address(){
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
