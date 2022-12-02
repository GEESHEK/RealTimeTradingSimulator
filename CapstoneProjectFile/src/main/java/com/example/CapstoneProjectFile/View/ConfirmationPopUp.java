 package com.example.CapstoneProjectFile.View;
import com.example.CapstoneProjectFile.Model.UserAccount;
import com.example.CapstoneProjectFile.Service.BoughtStocksService;
import com.example.CapstoneProjectFile.Service.StockService;
import com.example.CapstoneProjectFile.Service.UserService;
import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter // Lombok
public class ConfirmationPopUp {

    // setters accessed before this popup window is called by GUI_Main
    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

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
    StockService service = new StockService();
    @Autowired
    BoughtStocksService boughtStocksService;
    @Autowired
    UserService userService;
    final private int frameWidth = 350;
    final private int frameHeight = 350;
    private int headerPanelHeight = frameHeight / 10;
    private int contentPanelYPosition = headerPanelHeight + 10;
    private int contentPanelHeight = frameHeight - headerPanelHeight - 20;
    private int latestPriceYPosition = contentPanelHeight / 5;
    private int column2X = 3 * (frameWidth / 4);
    private int confirmBuyButtonWidthAndXPosition = frameWidth / 3;

    void openWindow() throws IOException {
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

        // set logo image in header
        Image logoImage = ImageIO.read(new File("src/main/resources/Logo5.5.png"));
//        StyleContext context = new StyleContext();
//        StyledDocument document1 = new DefaultStyledDocument(context);
//        Style labelStyle1 = context.getStyle(StyleContext.DEFAULT_STYLE);
        // note: the dimensions are 2013 × 507, has to scale
        int imageLogoHeight = (headerPanelHeight - 10);
        int imageLogoWidth = (headerPanelHeight - 10);
        Image dimg1 = logoImage.getScaledInstance(imageLogoWidth, imageLogoHeight,
                Image.SCALE_SMOOTH);
        Icon logoIcon = new ImageIcon(dimg1);
        JLabel imageLogo = new JLabel(logoIcon);
        imageLogo.setBounds(10, (headerPanelHeight / 4), imageLogoWidth, imageLogoHeight);
        headerPanel.add(imageLogo);

        // uses Spring boot to find the company name from the ticker (ticker passed in by GUI_Main)
        headerLabel = new JLabel();
        String companyName = service.findName(ticker);
        headerLabel.setText("Buy " + companyName);
        headerLabel.setBounds((30 + imageLogoWidth), (headerPanelHeight / 4), (frameWidth - imageLogoWidth - 30),(headerPanelHeight - 10));
        headerLabel.setFont(new FontUIResource("Arial", 3, 15));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);

        // new content Panel
        contentPanel = new JPanel();
        contentPanel.setBounds(0, contentPanelYPosition, frameWidth, contentPanelHeight);
        contentPanel.setLayout(null);
        contentPanel.setBackground(Color.decode("#FFFFFF"));
        frame.add(contentPanel);

        // latest price
        latestPriceText = new JLabel("Latest Price");
        latestPriceText.setBounds(10, latestPriceYPosition, 200, 50);
        contentPanel.add(latestPriceText);
        Stock stock;
        try {
            stock = YahooFinance.get(ticker); // get live stock information from YahooFinance API
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        stockPrice = stock.getQuote().getPrice(); // using the yahoo finance API, we get the real-time price
        latestPriceNumber = new JLabel("$" + stockPrice);
        latestPriceNumber.setBounds(column2X, latestPriceYPosition, 200, 50);
        contentPanel.add(latestPriceNumber);

        // number of shares the user bought
        numberofSharesText = new JLabel("Number of Shares");
        numberofSharesText.setBounds(10, (latestPriceYPosition * 2), 200, 50);
        contentPanel.add(numberofSharesText);

        // quantity of shares
        quantityTextField = new JTextField("1");
        quantityTextField.setBounds(column2X, (latestPriceYPosition * 2), 200, 50);
        contentPanel.add(quantityTextField);

        // total cost of the shares - text
        totalCost = new JLabel("Total Cost (est)");
        totalCost.setBounds(10, (latestPriceYPosition * 3), 200, 50);
        contentPanel.add(totalCost);

        // total cost = quantity * price
        totalCost$ = new JLabel();
        int stockPriceInt = stockPrice.intValue(); // converts from BigDecimal to integer
        totalCost$.setText((getQuantity() * stockPriceInt) + ""); // set to quantity * getPrice;
        totalCost$.setBounds(column2X, (latestPriceYPosition * 3), 200, 50);
        contentPanel.add(totalCost$);

        // confirm the transaction
        confirmBuyButton = new JButton("Confirm Buy");
        confirmBuyButton.setBounds(confirmBuyButtonWidthAndXPosition, (latestPriceYPosition * 4), confirmBuyButtonWidthAndXPosition, 50);
        contentPanel.add(confirmBuyButton);

        addActionListeners(); // call function below
        frame.setVisible(true); // set visibility to prevent issues where you have to click on the scree to see anything
    }

    private int getQuantity() {
        int quantity = parseInt(quantityTextField.getText()); // takes user input from text field
        return quantity;
    }

    // add the relevant action listeners
    private void addActionListeners() {
        confirmBuyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // check if sufficient funds; add to database
                Double userBalance = userService.getUserBalance(userAccount);

                if(userBalance >= stockPrice.doubleValue()*getQuantity()){
                    boughtStocksService.addStock(ticker,getQuantity(), stockPrice,userAccount);

                    frame.setVisible(false);
                }
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


