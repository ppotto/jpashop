package com.jpabook.jpashop.api;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public List<Member> memberV1() {
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result memberV2() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(member -> new MemberDto(member.getName()))
                .collect(Collectors.toList());
        return new Result(collect.size(), collect);

    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }

    // api 스펙을 위한 별도의 DTO를 만들어야함
    // 외부에서 오는 제이슨을 그대로 갖다쓰면 후에 장애가 생길 가능성이 큼
    // api를 만들 때는 항상 Entity를 파라미터로 받지 말 것!
    // 실무에서 비슷한 api를 여러가지 경우로 만드는데 Entity로만 받으면 감당안됨
    // 엔티티를 외부로 노출하지 말 것.
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    //@RequestBody로 CreateMemberRequest에 바인딩
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {

        Member member = new Member();
        member.setName(request.getName());
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id, @RequestBody UpdateMemberRequest request) {
        //커맨드와 쿼리를 분리한다.
        memberService.update(id, request.getName()); //업데이트 커맨드 (즉 하나의 트랜잭션)
        Member findMember = memberService.findOne(id);//그리고 다시 멤버를 찾아오는 쿼리
        //이렇게 해서 유지보수 유용하게
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }


    @Data
    static class CreateMemberRequest {
        @NotEmpty
        private String name;
    }


    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }


}
