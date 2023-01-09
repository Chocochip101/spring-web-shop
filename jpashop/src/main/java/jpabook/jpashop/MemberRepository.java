package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;


//    Command와 Query를 분리해라.
    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }


    public Member find(Long id){
        return em.find(Member.class, id);
    }
}
