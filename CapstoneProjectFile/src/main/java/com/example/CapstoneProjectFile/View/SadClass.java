package com.example.CapstoneProjectFile.View;

import com.example.CapstoneProjectFile.Model.BoughtStocks;
import com.example.CapstoneProjectFile.Model.UserAccount;
import com.example.CapstoneProjectFile.Service.BoughtStocksService;
import com.example.CapstoneProjectFile.Service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static java.lang.Integer.parseInt;

@Component
public class SadClass {
    JFrame frame;
    JPanel headerPanel;
    JLabel headerLabel;
    JPanel contentPanel;

    // 667 × 1000 - scale to this ratio
    final int catImageWidth = 333;
    final int catImageHeight = 500;
    final private int frameWidth = catImageWidth + 16;
    final private int frameHeight = catImageHeight + 86;
    final private int headerPanelHeight = 40;
    final private int contentPanelYPosition = 45;
    final private int contentPanelHeight = frameHeight - headerPanelHeight - 20;


    void openSadFrame() throws IOException {
        // set frame
        frame = new JFrame("Confirmation");
        frame.setSize(frameWidth, frameHeight);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        // headerPanel
        headerPanel = new JPanel();
        headerPanel.setBounds(0,0, frameWidth, headerPanelHeight);
        headerPanel.setBackground(Color.decode("#B0D5DE"));
        headerPanel.setLayout(null);
        frame.add(headerPanel);

        // the logo in the header
        Image logoImage = ImageIO.read(new File("src/main/resources/Logo5.5.png"));
        int imageLogoHeight = (headerPanelHeight - 10);
        int imageLogoWidth = (headerPanelHeight - 10);
        Image dimg1 = logoImage.getScaledInstance(imageLogoWidth, imageLogoHeight,
                Image.SCALE_SMOOTH);
        Icon logoIcon = new ImageIcon(dimg1);
        JLabel imageLogo = new JLabel(logoIcon);
        imageLogo.setBounds(20, (headerPanelHeight / 4), imageLogoWidth, imageLogoHeight);
        headerPanel.add(imageLogo);

        // header text
        headerLabel = new JLabel();
        headerLabel.setText("Rough day in the market?");
        headerLabel.setBounds((30 + imageLogoWidth), (headerPanelHeight / 4), (frameWidth - imageLogoWidth - 30),(headerPanelHeight - 10));
        headerLabel.setFont(new FontUIResource("Arial", 3, 15));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);

        // content panel
        contentPanel = new JPanel();
        contentPanel.setBounds(0, contentPanelYPosition, frameWidth, contentPanelHeight);
        contentPanel.setLayout(null);
        contentPanel.setBackground(Color.decode("#FFFFFF"));

        // cat image inserted
        ArrayList<String> imageList = new ArrayList<>(Arrays.asList(
                "src/main/resources/HangInThere.jpg",
                "src/main/resources/catMeme2.jpg",
                "src/main/resources/catMeme3.jpg",
                "src/main/resources/catMeme4.jpg",
                "src/main/resources/MonkeyMeme5.png",
                "src/main/resources/meme6.jpg"
        ));
        int len = imageList.size();
        Random rand = new Random(); // random number generator
        int upperBound = len;
        int int_random = rand.nextInt(upperBound);

        Image catImage = ImageIO.read(new File(imageList.get(int_random)));
        Image dimg2 = catImage.getScaledInstance(catImageWidth, catImageHeight,
                Image.SCALE_SMOOTH);
        Icon logoIcon2 = new ImageIcon(dimg2);
        JLabel imageIcon2 = new JLabel(logoIcon2);
        imageIcon2.setBounds(8, 48, catImageWidth, catImageHeight);
        contentPanel.add(imageIcon2);

        frame.add(contentPanel);
        frame.setVisible(true);


    }


}
