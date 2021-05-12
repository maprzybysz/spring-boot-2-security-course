package pl.maprzybysz.springboot2security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.maprzybysz.springboot2security.model.AppUser;
import pl.maprzybysz.springboot2security.service.UserService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    private UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }




    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    @RequestMapping("/signup")
    public ModelAndView signup(){
        return new ModelAndView("registration", "user", new AppUser());
    }
    @RequestMapping("/register")
    public ModelAndView register(AppUser user, HttpServletRequest request) throws MessagingException {
        userService.addNewUser(user, request);
        return new ModelAndView("redirect:/login");
    }
    @RequestMapping("/verifyToken")
    public ModelAndView verifyToken(@RequestParam String token){
        userService.verifyToken(token);
        return new ModelAndView("redirect:/login");

    }
    @RequestMapping("/verifyAdmin")
    public ModelAndView verifyAdmin(@RequestParam String token){
        userService.verifyAdminToken(token);
        return new ModelAndView("redirect:/login");

    }
}
