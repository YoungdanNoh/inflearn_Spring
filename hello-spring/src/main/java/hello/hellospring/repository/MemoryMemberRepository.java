package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

//@Repository
public class MemoryMemberRepository implements MemberRepository{
    
    private static Map<Long, Member> store = new HashMap<>(); // 실무에서는 동시성 문제로 다른 방식을 사용함.
    private static long sequence = 0L; // 0, 1, 2와 같은 key값을 생성해 주는 것, 실무에서는 동시성 문제로 다른 방식을 사용함.
    
    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); // store에 있는 Member들이 반환됨.
    }

    public void clearStore(){
        store.clear();
    }
}
