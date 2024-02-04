package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    private final EntityManager em; // jpa는 엔티티 매니저라는 것으로 모든 것이 동작한다.
    // build.gradle에서 data-jpa 라이브러리를 받았으므로 스프링 부트가 자동으로 현재 데이터베이스와 연결하여 EntityManager라는 것을 생성해준다.
    // 우리는 이것을 인젝션 받으면 된다.

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) { //PK 기반으로 찾으므로 간단
        Member member = em.find(Member.class, id); //em.find() 매개변수로는 조회할 타입과 식별자 PK값을 넣어주면 된다.
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        List<Member> result = em.createQuery("select m from Memeber m", Member.class).getResultList();
        // 테이블 대상으로 쿼리를 날리는 것이 아닌 객체를 대상으로 쿼리를 날린다.
        // select m: 멤버 엔티티 자체를 select 한다는 의미.
        // from Memeber m에서는 as가 생략된 것. (from Memeber as m)과 동일
        return result;
    }
}
