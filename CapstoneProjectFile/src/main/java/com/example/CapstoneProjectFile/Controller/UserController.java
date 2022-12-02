package com.example.CapstoneProjectFile.Controller;

import com.example.CapstoneProjectFile.Model.UserAccount;
import com.example.CapstoneProjectFile.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/user")
public class UserController {

    private UserService userService;

    @GetMapping
    public Iterable<UserAccount> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "{id}")
    public UserAccount getUserAccountById(@PathVariable Long id) {
        return userService.getUserAccountById(id);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
