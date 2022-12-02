package com.example.CapstoneProjectFile.View;

import com.example.CapstoneProjectFile.Controller.LogInController;
import com.example.CapstoneProjectFile.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

@Component
public class LogInWindow {
    public JFrame frame;
    private JPanel panel;

    private JTextField userNameField;

    private JLabel userLabel;

    private JLabel passwordLabel;

    private JPasswordField passwordField;

    private JButton submitButton = new JButton("Log In");;

    private JLabel successLabel;



    public JButton getSubmitButton() {
        return submitButton;
    }

    public void openWindow() throws IOException {
        frame = new JFrame("Bloo Trading App");
        frame.setSize(350, 350);
        frame.setBackground(Color.decode("#B0D5DE"));
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.decode("#B0D5DE"));
        frame.setLocationRelativeTo(null);
        frame.add(panel);

        Image logoImage = ImageIO.read(new File("src/main/resources/Logo5.png"));
        StyleContext context = new StyleContext();
        StyledDocument document1 = new DefaultStyledDocument(context);
        Style labelStyle1 = context.getStyle(StyleContext.DEFAULT_STYLE);
        // note: the dimensions are 2013 × 507, has to scale
        int imageLogoHeight = 39;
        int imageLogoWidth = 155;
        Image dimg1 = logoImage.getScaledInstance(imageLogoWidth, imageLogoHeight,
                Image.SCALE_SMOOTH);
        Icon logoIcon = new ImageIcon(dimg1);
        JLabel imageLogo = new JLabel(logoIcon);
        imageLogo.setBounds(100,20,imageLogoWidth,imageLogoHeight);
        panel.add(imageLogo);

        userLabel = new JLabel("User Name:");
        userLabel.setBounds(10, 80, 80, 25);
        panel.add(userLabel);

        userNameField = new JTextField(20);
        userNameField.setBounds(100, 80, 175, 25);
        panel.add(userNameField);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 110, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 110, 175, 25);
        panel.add(passwordField);


        submitButton.setBounds(120, 145, 80, 25);
//        submitButton.addActionListener(new LogInController(this));
        panel.add(submitButton);

        successLabel = new JLabel();
        successLabel.setBounds(10, 200, 300, 25);
        panel.add(successLabel);
        successLabel.setForeground(Color.RED);


        frame.setVisible(true);
    }
    @Lazy
    @Autowired
    @Qualifier("logInController")
    public void setSubmitButton(ActionListener actionListener){
        this.submitButton.addActionListener(actionListener);
    }

    public String getUserName() {
        return userNameField.getText();
    }

    public String getPassword() {
        return String.valueOf(passwordField.getPassword());
    }

    public void logInSuccessful() {
        successLabel.setText("Log In Successful");
    }

    public void logInFailed() {
        successLabel.setText("Log In Failed");
    }
}
