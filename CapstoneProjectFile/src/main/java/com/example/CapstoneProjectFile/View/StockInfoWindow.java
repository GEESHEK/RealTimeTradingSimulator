package com.example.CapstoneProjectFile.View;

import com.example.CapstoneProjectFile.Service.StockService;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class StockInfoWindow {
    public JFrame frame;
    private JPanel panel;

    private JPanel headerPanel;

    private JLabel headerLabel;

    private int frameWidth = 350;
    private int frameHeight = 300;

    private int headerPanelHeight = frameHeight / 10;
    private int contentPanelYPosition = headerPanelHeight + 10;
    private int contentPanelHeight = frameHeight - headerPanelHeight - 20;


    String ticker;

    public JTextArea displayTextArea;

    public StockService stockService = new StockService();

    public StockInfoWindow(){}

    public StockInfoWindow(String ticker){
        this.ticker = ticker;
    }

    public void openWindow() throws IOException {
        frame = new JFrame("Stock Information");
        frame.setSize(frameWidth, frameHeight);
        frame.setResizable(false);

        headerPanel = new JPanel();
        headerPanel.setBounds(0,0, frameWidth, headerPanelHeight);
        headerPanel.setBackground(Color.decode("#B0D5DE"));
        headerPanel.setLayout(null);
        frame.add(headerPanel);

        Image logoImage = ImageIO.read(new File("src/main/resources/Logo5.5.png"));
        StyleContext context = new StyleContext();
        StyledDocument document1 = new DefaultStyledDocument(context);
        Style labelStyle1 = context.getStyle(StyleContext.DEFAULT_STYLE);
        // note: the dimensions are 2013 × 507, has to scale
        int imageLogoHeight = (headerPanelHeight - 10);
        int imageLogoWidth = (headerPanelHeight - 10);
        Image dimg1 = logoImage.getScaledInstance(imageLogoWidth, imageLogoHeight,
                Image.SCALE_SMOOTH);
        Icon logoIcon = new ImageIcon(dimg1);
        JLabel imageLogo = new JLabel(logoIcon);
        imageLogo.setBounds(10, (headerPanelHeight / 4), imageLogoWidth, imageLogoHeight);
        headerPanel.add(imageLogo);

        headerLabel = new JLabel();
        String companyName = stockService.findName(ticker);
        headerLabel.setText(companyName);
        headerLabel.setBounds((30+imageLogoWidth), (headerPanelHeight / 4), (frameWidth - imageLogoWidth - 30),(headerPanelHeight - 10));
        headerLabel.setFont(new FontUIResource("Arial", 3, 15));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);

        panel = new JPanel();
        panel.setLayout(null);
        frame.add(panel);
        panel.setBounds(0, contentPanelYPosition, frameWidth, contentPanelHeight);
        panel.setBackground(Color.decode("#FFFFFF"));


        displayTextArea = new JTextArea();
        displayTextArea.setBounds(10, contentPanelYPosition, frameWidth, contentPanelHeight);
        displayTextArea.setFont(new FontUIResource("Arial", 2, 14));

        String stockTickerText = "Stock Symbol: "+ ticker;
        String StockNameText = stockService.findName(ticker);
        String MarketCapText = "Market Cap: "+ stockService.findMarketCap(ticker);
        String StockExchangeText = "Stock Exchange: "+ stockService.findStockExchange(ticker);
        String StockCurrency = "Currency: "+ stockService.findCurrency(ticker);
        String StockPriceText = "Stock Price: "+ stockService.findPrice(ticker);
        String AskPriceText = "Ask: "+ stockService.findAskPrice(ticker);
        String BidPriceText = "Bid: "+ stockService.findBidPrice(ticker);
        String ChangeInPercentText = "Change in %: "+ stockService.findChangeInPercent(ticker);
        String ChangeFrom200MeanText = "Change From Avg 200 in %: "+ stockService.findChangeFrom200MeanPercent(ticker);


        displayTextArea.setText(stockTickerText+"\n"+MarketCapText+"\n"+StockExchangeText+"\n"+StockCurrency+"\n"+StockPriceText+"\n"+AskPriceText+"\n"+BidPriceText+"\n"+ChangeInPercentText+"\n"+ChangeFrom200MeanText+"\n");
        panel.add(displayTextArea);
        displayTextArea.setEditable(false);



        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
