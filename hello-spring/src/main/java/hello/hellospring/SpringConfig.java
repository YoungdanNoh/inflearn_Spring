package hello.hellospring;


import hello.hellospring.repository.*;
import hello.hellospring.service.MemberService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    /**
     * 자바 코드로 직접 스프링 빈 등록하기!!
     * **/
    
    //@Autowired DataSource dataSource;
    //스프링으로 DataSource를 제공받는 첫번째 방법

    /*DataSource dataSource;

    public SpringConfig(DataSource dataSource){ //DI
        this.dataSource = dataSource;
    }*/

    EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em){
        this.em = em;
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
        //return new MemoryMemberRepository();

        //return new JdbcMemberRepository(dataSource);
        // 기존 코드는 손대지 않고 이 한줄만 바꿔주면 나머지 실제 애플리케이션에 관련된 코드는 손댈 필요가 없다.(다형성, 구현체만 바뀜)
        // 스프링은 이를 DI를 사용해 더욱 쉽게 할 수 있도록 도와준다.
        // 개방-폐쇄 원칙(OCP, Open-Closed Principle): 확장에는 열려있고, 수정, 변경에는 닫혀있다.
        // 스프링의 DI(Dependencies Injection)을 사용하면 기존 코드를 전혀 손대지 않고, 설정만으로 구현 클래스를 변경할 수 있다.

        //return new JdbcTemplateMemberRepository(dataSource);

        return new JpaMemberRepository(em);
    }
}
