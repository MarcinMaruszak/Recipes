package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import recipes.model.User;
import recipes.service.UserDetailsServiceImpl;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    UserDetailsServiceImpl userService;

    @PostMapping(path = "/api/register")
    public @ResponseBody void register(@Valid @RequestBody User user){
        userService.register(user);
    }
}
