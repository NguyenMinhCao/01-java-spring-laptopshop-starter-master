package vn.hoidanit.laptopshop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/")
    public String index() {
        return "Hello world , cao dep trai, phan tom";
    }

    @GetMapping("admin")
    public String admin() {
        return "admin page";
    }

    @GetMapping("/user")
    public String user() {
        return "user page";
    }
}
