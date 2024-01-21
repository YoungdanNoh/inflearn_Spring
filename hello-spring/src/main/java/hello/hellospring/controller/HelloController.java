package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    // 웹 애플리케이션에서 첫번째 진입점이 바로 컨트롤러이다.

    @GetMapping("hello") // 이렇게 하게되면 웹 어플리케이션에서 /hello라고 들어오면 해당 메서드를 호출해준다.
    public String hello(Model model){
        // Spring이 model이라는 것을 만들어서 넣어준다.
        model.addAttribute("data", "hello!!");
        // 즉 model에 (data:hello!!)라고 넣어줌
        return "hello"; // 이때 return의 의미는 resources/templates에 있는 hello.html로 가서 렌더링해라(화면을 실행시켜라)의 의미가 된다.
                        // 기본적으로 Spring Boot에서 세팅된 것임.
        //컨트롤러에서 리턴 값으로 문자를 반환하면 뷰 리졸버('viewResolver')가 화면을 찾아서 처리한다.
            // 스프링 부트 템플릿엔진은 기본적으로 viewName 매핑이 됨.
            // 'resources:templates/' + {ViewName} + '.html'
    }
}
