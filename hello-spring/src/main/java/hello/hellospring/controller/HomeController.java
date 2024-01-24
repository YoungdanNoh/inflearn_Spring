package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * 이전에 index.html을 welcome page로 만들어 준다고 하였다.
     * 하지만 이때 "/"(localhost:8080)에 맵핑된 컨트롤러가 있다면 해당 컨트롤러를 호출하는 것으로 한다.
     * 따라서 index.html은 무시된다.("/"에 맵핑된 컨트롤러를 삭제하면 다시 index.html이 welcome page가 됨.)
     * **/
    @GetMapping("/")
    public String home(){
        return "home";
    }
}
