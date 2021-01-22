package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor //final 붙어있는 args만 생성자 만들어줌
@Transactional(readOnly = true) //우선권을 갖는 설정
@Service
public class MemberService {

    //필드 인젝션
    // @Autowired
    private final MemberRepository memberRepository;

    //setter 인젝션을 하면 TEST할 때 편할 수 있음
    //생성자 인젝션을 권장, 의존 관계를 명확하게 알 수 있음
    //생성자에 필드가 한개 있는 경우에? 스프링에서 자동으로 @Autowired 해줌
    //    @Autowired
    //    public MemberService(MemberRepository memberRepository) {
    //        this.memberRepository = memberRepository;
    //    }

    //회원 가입
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    // 동시에 가입하는 경우 뚫릴 수 있긴함
    public void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
