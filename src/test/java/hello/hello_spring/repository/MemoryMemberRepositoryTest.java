package hello.hello_spring.repository;

//import org.junit.jupiter.api.Assertions;
import hello.hello_spring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach // 하나의 메서드가 끝날 때마다 호출되는 콜백 메서드
    public void afterEach(){
        repository.clearStore();
    }
    // 테스트는 서로 순서와 의존 관계가 없이 실행되어야 한다.
    // 따라서 하나의 테스트가 끝날 때마다 저장소나 공용 데이터를 지워주어야 문제가 생기지 않는다.

    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");

        repository.save(member); //이때 자동으로 id가 세팅됨.

        Member result = repository.findById(member.getId()).get();
        //System.out.println("result = " + (member == result)); // 이렇게 봐도 되지만 계속 글자로 확인하는 것은 피곤.. 따라서 assert 사용

        //Assertions.assertEquals(member, result); // 순서는 expected, actual

        //Assertions.assertThat(member).isEqualTo(result);
        // Assersions를 static import를 하면 아래와 같이 변경이 가능해진다.
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
