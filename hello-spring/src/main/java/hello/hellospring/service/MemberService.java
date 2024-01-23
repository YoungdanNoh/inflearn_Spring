package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    //private final MemberRepository memberRepository = new MemoryMemberRepository();
    //이렇게 하게되면 MemberServiceTest와 다른 memberRepository 객체를 생성하여 테스트를 하게 되는 문제가 발생함.
    //따라서 아래와 같이 수정
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){ //생성자 사용, 이러한 것을 Dependency Injection이라고 한다.
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     * **/
    public Long join(Member member){
        // 같은 이름이 있는 중복 회원X
        validateDuplicateMember(member); // 중복 회원 검증

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     * **/
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
