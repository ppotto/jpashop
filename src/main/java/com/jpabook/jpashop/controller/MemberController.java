package com.jpabook.jpashop.controller;

import com.jpabook.jpashop.domain.Address;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;


@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        //Controller에서 View로 넘어갈 때, data를 실어서 넘기는 것
        //화면에서? memberForm 객체에 접근할 수 있게됨,,
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new") // 이거 form데이터도 다시 가져간다고함,,,
    public String create(@Valid MemberForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "members/createMemberForm"; //문제 있으면 다시 돌아가기
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        // 여기서 화면에 필요한 정보만 보내기 -> DTO
        /*
        *
        * Entity는 절대 api로 외부로 반환하지 말것,,,
        * 이유 : Entity가 변경 될때, api 스펙 또한 같이 바뀌기 때문에
        * 매우 골치아파짐,,, 이경우에는 그냥 템플릿에다가 출력하는거라 ㄱㅊ        *
        * */
        //                             키       ,
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
