package com.example.CapstoneProjectFile.Controller;

import com.example.CapstoneProjectFile.Model.UserAccount;
import com.example.CapstoneProjectFile.Service.UserService;
import com.example.CapstoneProjectFile.View.GUI_Main;
import com.example.CapstoneProjectFile.View.LogInWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

@Component
public class LogInController implements ActionListener {

    LogInWindow myWindow;
    private UserService userService;
@Autowired
    GUI_Main gui_main;


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setMyWindow(LogInWindow myWindow) {
        this.myWindow = myWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String userName = myWindow.getUserName();
        String password = myWindow.getPassword();

        if (userService.verifyUserLogIn(userName, password) != null && userService.getUserByUserName(userName).getPassword().equals(password)) {
            myWindow.logInSuccessful();
            myWindow.frame.setVisible(false);

            UserAccount userAccount = userService.getUserByUserName(userName);
            //pass the userAccount object to GUI_Main

            try {
//                GUI_Main userWindow = new GUI_Main(userAccount);
                gui_main.setUserAccount(userAccount);
                gui_main.openFrame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }




        } else {
            myWindow.logInFailed();
        }
    }
}
