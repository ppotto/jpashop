package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    //@Autowired EntityManager em;


    @Test
   // @Rollback(value = false) //영속성 컨텍스트에 등록을 안한다고,,,??? 그래서 인서트를 안한단소린감
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member);

        //then
        //  em.flush(); // 수동으로 ? 디비에 쿼리날리기
        Assertions.assertEquals(member, memberRepository.findOne(saveId));

    }


    @Test(expected = IllegalStateException.class) //try catch 문을 이렇게 작성 할 수 있음
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");
        //when
        memberService.join(member1);
        memberService.join(member2);
            
        //then
        Assert.fail("예외가 발생해야한다."); //즉, 여기에 도달하면 안됨

    }
}