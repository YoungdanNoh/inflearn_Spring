package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {
    
    //private final MemberService memberService = new MemberService();
    /**
     * new를 통해 MemberService 객체를 생성해 사용할수도 있다.
     * MemberService는 큰 기능을 가지지 않으며 여러개를 생성할 필요성도 없으므로 하나만 생성하여 공용으로 사용하는 것이 좋다.
     * 따라서 스프링 컨테이너에 등록하여 사용하는 것이 좋다.(스프링 컨테이너에 등록하게 되면 딱 하나만 등록된다.)
     * **/

    private final MemberService memberService;
    //@Autowired private MemberService memberService; //필드 주입

//    private MemberService memberService;
//    @Autowired
//    public void setMemberService(MemberService memberService){
//        this.memberService = memberService;
//    }                                                 setter 주입

    @Autowired
    public MemberController(MemberService memberService) { //생성자로 스프링 컨테이너에 등록(생성자 주입)
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());

        System.out.println("member = " + member.getName());

        memberService.join(member);

        return "redirect:/"; // "/"로 돌아감
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}