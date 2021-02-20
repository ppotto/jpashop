package com.jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberForm {
    // 에러를 발생시킴
    @NotEmpty(message = "회원 이름은 필수 입니다.")
    private String name; //필수로 받아야하는
    private String city;
    private String street;
    private String zipcode;
}
