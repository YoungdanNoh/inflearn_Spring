package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    //MemberService memberService = new MemberService();
    //MemoryMemberRepository MemberRepository = new MemoryMemberRepository();
    //MemberService에서 만든 객체와 다른 객체를 생성하여 테스트하는 것이므로 문제가 있음
    //수정
    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach // 하나의 메서드가 끝날 때마다 호출되는 콜백 메서드
    public void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("hello");

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

        /* 방법1: try, catch 사용
        try {
            memberService.join(member2);
            fail();
        }catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
        */

        //방법2
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        // () -> memberService.join(member2) 로직 실행 시 IllegalStateException 예외가 터져야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다."); //메시지 검증

        //then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}