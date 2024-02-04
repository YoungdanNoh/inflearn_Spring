package hello.hellospring.domain;

import jakarta.persistence.*;

// jpa는 객체와 ORM이라는 기술이다. ORM(Object, Relational, Mapping) 즉, 객체와 릴레이셔널 DB의 테이블을 매핑한다.
@Entity // 매핑하기 위한 어노테이션 -> 이제부터 Member는 jpa가 관리하는 엔티티가 된다.
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //PK 매핑
    private Long id; // 고객이 아닌 시스템이 저장하는 id

    //@Column(name = "username") DB의 컬럼명이 "username"일 경우 이 애노테이션 추가
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
