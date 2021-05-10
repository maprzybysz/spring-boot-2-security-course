package pl.maprzybysz.springboot2security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class TestApi {

    @GetMapping("/forAll")
    public String forAll(){
        return "you have been logout";
    }
    @GetMapping("/forUser")
    public String forUser(Principal principal){
        return "forUser "+principal.getName();
    }
    @GetMapping("/forAdmin")
    public String forAdmin(Principal principal){
        return "forAdmin "+principal.getName();
    }
}
