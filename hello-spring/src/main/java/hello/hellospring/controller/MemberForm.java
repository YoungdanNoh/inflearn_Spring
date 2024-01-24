package hello.hellospring.controller;

public class MemberForm {
    private String name; //crateMemberForm.html에 있는 input의 name과 매칭됨. 스프링이 setName을 호출해 넣어주는 것임

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
