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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

@Component
public class SellPopUp {
    JFrame frame;
    JPanel headerPanel;
    JLabel headerLabel;
    JPanel contentPanel;
    JLabel latestPriceText;
    JLabel latestPriceNumber;
    JLabel numberofSharesText;
    JTextField quantityTextField;
    JLabel totalCost;
    JLabel totalCost$;
    JButton confirmBuyButton;

    private BigDecimal stockPrice;

    private UserAccount userAccount;
    private String ticker;

    private BoughtStocks boughtStocks;

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public void setBoughtStocks(BoughtStocks boughtStocks) {
        this.boughtStocks = boughtStocks;
    }

    //    public ConfirmationPopUp(String ticker, UserAccount userAccount) {
//        this.ticker = ticker;
//        this.userAccount = userAccount;
//
//    }

    StockService service = new StockService();

    @Autowired
    BoughtStocksService boughtStocksService;


    private int frameWidth = 350;
    private int frameHeight = 350;

    private int headerPanelHeight = frameHeight / 10;
    private int contentPanelYPosition = headerPanelHeight + 10;
    private int contentPanelHeight = frameHeight - headerPanelHeight - 20;

    private int latestPriceYPosition = contentPanelHeight / 5;
    private int column2X = 3 * (frameWidth / 4);

    private int confirmBuyButtonWidthAndXPosition = frameWidth / 3;

    void openWindow() throws IOException {
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
        String companyName = service.findName(ticker);
        headerLabel.setText("Sell " + companyName);
        headerLabel.setBounds((30 + imageLogoWidth), (headerPanelHeight / 4), (frameWidth - imageLogoWidth - 30),(headerPanelHeight - 10));
        headerLabel.setFont(new FontUIResource("Arial", 3, 15));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);


        contentPanel = new JPanel();
        contentPanel.setBounds(0, contentPanelYPosition, frameWidth, contentPanelHeight);
        contentPanel.setLayout(null);
        contentPanel.setBackground(Color.decode("#FFFFFF"));
        frame.add(contentPanel);

        latestPriceText = new JLabel("Latest Price");
        latestPriceText.setBounds(10, latestPriceYPosition, 200, 50);
        contentPanel.add(latestPriceText);

        // open new Info Window using input ^ as the stock
        Stock stock;
        try {
            stock = YahooFinance.get(ticker);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        stockPrice = stock.getQuote().getPrice();
        latestPriceNumber = new JLabel("$" + stockPrice);
        latestPriceNumber.setBounds(column2X, latestPriceYPosition, 200, 50);
        contentPanel.add(latestPriceNumber);

        numberofSharesText = new JLabel("Number of Shares");
        numberofSharesText.setBounds(10, (latestPriceYPosition * 2), 200, 50);
        contentPanel.add(numberofSharesText);

        quantityTextField = new JTextField("1");
        quantityTextField.setBounds(column2X, (latestPriceYPosition * 2), 200, 50);
//        addActionListeners();
        contentPanel.add(quantityTextField);

        totalCost = new JLabel("Total Cost (est)");
        totalCost.setBounds(10, (latestPriceYPosition * 3), 200, 50);
        contentPanel.add(totalCost);

        totalCost$ = new JLabel();
        int stockPriceInt = stockPrice.intValue();
        totalCost$.setText((getQuantity() * stockPriceInt) + ""); // set to quantity * getPrice;
        totalCost$.setBounds(column2X, (latestPriceYPosition * 3), 200, 50);
        contentPanel.add(totalCost$);

        confirmBuyButton = new JButton("Confirm Sell");
        confirmBuyButton.setBounds(confirmBuyButtonWidthAndXPosition, (latestPriceYPosition * 4), confirmBuyButtonWidthAndXPosition, 50);
        contentPanel.add(confirmBuyButton);

        addActionListeners();

        frame.setVisible(true);
    }

    private int getQuantity() {
        int quantity = parseInt(quantityTextField.getText());
        return quantity;
    }

    // only accept integers numberofSharesText
    private void addActionListeners() {
        confirmBuyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // check if enough stocks; add to database
                int quantityInput = getQuantity();
                if(quantityInput >= boughtStocks.getUnitsOfStock()){
                    quantityInput = boughtStocks.getUnitsOfStock();
                    System.out.println("Not enough stocks! All remaining stocks sold");
                    GUI_Main.boughtStocksList.remove(boughtStocks);
                }
                boughtStocksService.sellStock(quantityInput, stockPrice,boughtStocks ,userAccount);

                frame.setVisible(false);
            }
        });

        quantityTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String stockPrice = latestPriceNumber.getText().substring(1);
                double price = getQuantity() * parseDouble(stockPrice);
                totalCost$.setText(String.valueOf(price));
            }

            Action action = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("text updated");
                }
            };

        });

    }
}
