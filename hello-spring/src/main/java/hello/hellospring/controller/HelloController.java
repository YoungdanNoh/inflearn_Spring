package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model){
        // @RequestParam: 외부에서 파라미터를 받을 때 사용
        // @RequestParam의 required는 true가 디폴트이다. 따라서 무조건 파라미터를 넣어줘야 한다.
        model.addAttribute("name", name);
        return "hello-template";
    }
    /*
     * MVC: Model, View, Controller
     * 과거에는 View, Controller가 따로 분리되어 있지 않았음.(View에서 다 함. 이를 소위 Model1 방식이라고 하며, jsp로 많이 했음.)
     * 현재에는 MVC 방식으로 많이 함.
     * View: 화면을 그리는 데 모든 영향을 집중해야 함.
     * Controller: 비즈니스 로직과 서버 뒷단에 관련된 일을 처리.
     * Model: Model에 화면에 필요한 것들을 담아서 화면쪽에 넘겨준다.
     * */


    @GetMapping("hello-string")
    @ResponseBody // @ResponseBody: http의 body에 "hello "+ name 데이터를 직접 넣어주겠다는 의미.
    public String helloString(@RequestParam("name") String name){
        return "hello "+ name; // ex) "hello spring"이라는 문자가 요청한 클라이언트에 그대로 내려간다.
                               // 템플릿 엔진과의 차이는 view가 없이 문자가 그대로 내려간다는 것이다.
    }
    
    //API 방식
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello; // JSON 방식으로 넘어감.
        // @ResponseBody를 하고 객체를 반환하는 것은 JSON으로 반환하는 것이 기본으로 세팅되어 있다.

        /*
        * 동작 방식
        * 1. 웹 브라우저에서 localhost:8080/hello-api를 친다.
        * 2. 톰캣 내장 서버에서 이 요청을 스프링에 넘긴다.
        * 3. 스프링은 @ResponseBody 애노테이션이 붙어있으므로 http 응답에 그대로 이 데이터를 넘기는 것으로 동작한다.
        *    그런데 이 데이터가 문자가 아니고 객체라면 JSON 방식으로 데이터를 만들어서 http 응답으로 반환한다.
        * */
    }

    /*
    * @ResponseBody
    * http의 body에 문자 내용을 직접 반환
    * viewResolver 대신에 HttpMessageConverter가 동작한다.
    * 기본 문자처리: StringHttpMessageConverter
    * 기본 객체처리: MappingJackson2HttpMessageConverter
    * byte 처리 등등 기타 여러 HttpMessageConverter가 기본으로 등록되어 있음.
    * */

    static class Hello{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}