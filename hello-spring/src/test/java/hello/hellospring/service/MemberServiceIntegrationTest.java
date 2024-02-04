package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional // 이것을 사용함으로써 @AfterEach로 다음 테스트에 영향을 미치지 않도록 메모리 DB 데이터를 지우는 코드가 필요 없어진다.
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository; // 구현체는 SpringConfig.java에서 등록한 빈으로 올라온다.
    // 스프링 컨테이너에게 memberService, memberRepository 객체를 요구한다.
    // 기존 코드들은 생성자로 주입하는 것이 좋지만, test 코드는 다른 곳에서 가져다 쓰지 않기 때문에 필드 주입을 받는 것이 편하다.

    @Test
    void 회원가입() { // @Transactional 으로 테스트를 시작하기 전에 트랜젝션을 걸고 DB에 쿼리를 다 날린 후 롤백할 수 있게 해준다.
        //given
        Member member = new Member();
        member.setName("Spring");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        // () -> memberService.join(member2) 로직 실행 시 IllegalStateException 예외가 터져야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다."); //메시지 검증

        //then
    }
}

/**
 * @SpringBootTest: 스프링 컨테이너와 테스트를 함께 실행한다. 진짜 스프링을 띄워서 테스트를 한다.
 * @Transactional: 테스트 케이스에 이 애노테이션이 있으면 테스트 시작 전에 트랜잭션을 시작하고, 테스트 완료 후에 항상 롤백한다.
 *                 이렇게 하면 DB에 데이터가 남지 않으므로 다음 테스트에 영향을 주지 않는다.
 *                 참고로 @Transactional 테스트 케이스가 아닌 서비스 같은 곳에 붙으면 롤백하지 않고 정상 작동한다.
 * **/