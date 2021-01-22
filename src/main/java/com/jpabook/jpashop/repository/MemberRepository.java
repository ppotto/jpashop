package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


//        em.find();    // 엔티티 조회
//        em.persist(); // 엔티티 저장
//        em.remove();  // 엔티티 삭제
//        em.flush();   // 영속성 컨텍스트 내용을 데이터베이스에 반영
//        em.detach();  // 엔티티를 준영속 상태로 전환
//        em.merge();   // 준영속 상태의 엔티티를 영속상태로 변경
//        em.clear();   // 영속성 컨텍스트 초기화
//        em.close();   // 영속성 컨텍스트 종료

@Repository
@RequiredArgsConstructor //final 필드 생성자 만들어주는 lombok인데, EntityManager의 @Persistence? 이거도 같이 해줌
public class MemberRepository {

//    @PersistenceContext
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
