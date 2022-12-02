package com.example.CapstoneProjectFile.View;
import com.example.CapstoneProjectFile.Model.BoughtStocks;
import com.example.CapstoneProjectFile.Model.StockHistory;
import com.example.CapstoneProjectFile.Model.UserAccount;
import com.example.CapstoneProjectFile.Service.BoughtStocksService;
import com.example.CapstoneProjectFile.Service.HistoryDataService;
import com.example.CapstoneProjectFile.Service.StockHistoryService;
import com.example.CapstoneProjectFile.Service.StockService;
import com.example.CapstoneProjectFile.Service.UserService;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.text.DecimalFormat;
import java.util.List;
import static java.awt.Color.*;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.valueOf;
import static org.aspectj.runtime.internal.Conversions.doubleValue;
@Component
public class GUI_Main {

    GridBagConstraints gbc = new GridBagConstraints(); // layout

    UserAccount userAccount = new UserAccount();
    @Autowired
    BoughtStocksService boughtStocksService; // autowired classes to retrieve information

    @Autowired
    UserService userService;

    @Autowired
    StockHistoryService stockHistoryService;

    @Autowired
    SadClass sadClass;

    @Autowired
    ConfirmationPopUp popUp;

    @Autowired
    LogInWindow loginWindow;

    @Autowired
    SellPopUp sellPopUp;

    @Autowired
    StockService stockService = new StockService();

    public void setUserAccount(UserAccount userAccount) { // automatically set from login screen
        this.userAccount = userAccount;
    }

    public UserAccount getUserAccount() {
        return this.userAccount;
    }

    public GUI_Main() throws IOException {
//        openFrame();

    }

    // Frame
    JFrame frame;
    private int frameWidth = 1300;
    private int frameHeight = 820;
    public void openFrame() throws IOException {

        frame = new JFrame("Bloo Trading App");
        frame.setSize(frameWidth, frameHeight); // scale
        frame.setResizable(false); // disabled for presentation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.decode("#B0D5DE"));
        frame.setForeground(Color.decode("#B0D5DE"));
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        addAllPanelsToFrame(); // in discrete functions to prevent big monolithinc function
        addActionListeners();  // so that this function only deals with the frame
        frame.setVisible(true);
    }


    //Header Panel
    JPanel headerPanel;
    int openHeaderPanelHeight = 100;
    JButton logOut;
    private void openHeaderPanel() throws IOException {
        // create the header panel
        headerPanel = new JPanel();
        headerPanel.setBounds(0,0,frameWidth,openHeaderPanelHeight); // set in reference to frame laout
        headerPanel.setBackground(Color.decode("#B0D5DE"));
        headerPanel.setForeground(Color.decode("#B0D5DE")); // hexaadecimal color scheme
        headerPanel.setLayout(null);
        addPanel(headerPanel); // discrete function
        setResetButton(); // discrete function

        // create the Account Balance Label
        String accountBalance = "Account Balance: $" + userService.getUserBalance(userAccount); // contacts service layer using Spring to retrieve info from database
        JLabel percentChangeOverTime = new JLabel(accountBalance);
        percentChangeOverTime.setFont(new FontUIResource("Arial", 1, 20));
        percentChangeOverTime.setForeground(WHITE);
        percentChangeOverTime.setBounds(((frameWidth / 7) - 160),(openHeaderPanelHeight / 4) - 10,400,80);
        headerPanel.add(percentChangeOverTime); // ^ note: formatting referencing frame width for scale

        Image logoImage = ImageIO.read(new File("src/main/resources/Logo5.png"));
        // note: the dimensions are 2013 × 507, has to scale
        int imageLogoHeight = 63; // scale with original picture
        int imageLogoWidth = 252;
        Image dimg1 = logoImage.getScaledInstance(imageLogoWidth, imageLogoHeight,
                Image.SCALE_SMOOTH); // scaled down smoothly
        Icon logoIcon = new ImageIcon(dimg1); // convert to icon
        JLabel imageLogo = new JLabel(logoIcon); // set jlabel content as icon
        int imageLogoYposition = openHeaderPanelHeight - (imageLogoHeight)- 20;
        imageLogo.setBounds(((frameWidth /2) - (imageLogoWidth / 2)), imageLogoYposition, imageLogoWidth,imageLogoHeight);
        headerPanel.add(imageLogo);

        logOut = new JButton("Log Out");
        logOut.setFont(new FontUIResource("Arial", 1, 12));
        logOut.setBounds((frameWidth - 80),5, 80,30);
        headerPanel.add(logOut);
    }

    JButton resetButton;
    private void setResetButton() throws IOException {
        // create the refresh button
        int resetButtonWidth = 20;
        int resetButtonHeight = 20;

        Image image = ImageIO.read(new File("src/main/resources/refreshLogo.png"));
        Image dimg = image.getScaledInstance(resetButtonWidth, resetButtonHeight,
                Image.SCALE_SMOOTH);
        Icon icon = new ImageIcon(dimg);
        resetButton = new JButton(icon);
        resetButton.setBackground(Color.decode("#B0D5DE"));
        resetButton.setBounds(0,0,resetButtonWidth,resetButtonHeight);
        headerPanel.add(resetButton);
    }


    // Owned Stocks Panel:
    JPanel ownedStocksPanel;
    int ownedStocksPanelWidth = 400;
    int ownedStocksPanelHeight = (frameHeight - openHeaderPanelHeight - 35);
    int ownedStocksPanelYPosition = openHeaderPanelHeight + 5;
    private void openOwnedStocksPanel() throws IOException {
        // create teh owned stocks panel
        ownedStocksPanel = new JPanel();
        ownedStocksPanel.setBounds(5,ownedStocksPanelYPosition, ownedStocksPanelWidth, ownedStocksPanelHeight);
        ownedStocksPanel.setBackground(Color.decode("#F7F5F4"));
        ownedStocksPanel.setForeground(Color.decode("#F7F5F4"));
        ownedStocksPanel.setLayout(new GridBagLayout());
        gbc.anchor = GridBagConstraints.NORTHWEST; // grid bag layout
        gbc.weightx = 1;
        gbc.weighty = 1;
        addPanel(ownedStocksPanel); // calls function below; remove boilerplate code
        setOwnedStocksButtons(); // calls function below
    }
    // owned stocks table function (ownedStocksButtons()) in the owned stocks panel
    int smallerButtonHeight;
    int smallerButtonWidth;
    JLabel[] ownedStocksTextFields;
    JLabel[] tableHeaders;
    JButton[] sellButtons;
    public static List<BoughtStocks> boughtStocksList = new ArrayList<>();
    Border c = BorderFactory.createCompoundBorder( // Border lines
            BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#FFFFFF")),
            BorderFactory.createEmptyBorder(0, 0, 2, 0));

    private void setOwnedStocksButtons() throws IOException {
        Iterable<BoughtStocks> ownedStocks = boughtStocksService.getBoughtStocksByUserAccount(userAccount);
        int len = 0; // find how many rows in the database (i.e. 1 for each stock)
        boughtStocksList.clear();
        for(BoughtStocks boughtStocks : ownedStocks){
            boughtStocksList.add(boughtStocks);
            len++;
        } // iterates through the length of the stocks the User owns; assigns to 'len'
        ownedStocksTextFields = new JLabel[len*4]; // ^ determines the amount of labels generated
        smallerButtonHeight = (ownedStocksPanelHeight / (ownedStocksTextFields.length / 5) - 25); // ^determines height to scale
        smallerButtonWidth = (ownedStocksPanelWidth / 4) - 15;
        int i = 0;
        for(BoughtStocks boughtStocks : ownedStocks){ // iterator to create each label of owned stocks
            gbc.fill = GridBagConstraints.BOTH;
            ownedStocksTextFields[i] = new JLabel(boughtStocks.getTicker()); // the Stock itself; uses Spring to access database
            ownedStocksTextFields[i].setFont(new FontUIResource("Arial", 5, 20));
            ownedStocksTextFields[i].setForeground(BLACK);
            ownedStocksTextFields[i].setBackground(Color.decode("#F7F5F4"));
            gbc.gridx = 0;
            gbc.gridy = (i / 4) + 1; // algorithm taking account of length
            setGBCOwnedStocksTextFields();
            ownedStocksTextFields[i].setBorder(c); // set border
            ownedStocksTextFields[i].setHorizontalAlignment(SwingConstants.CENTER);
            ownedStocksTextFields[i].setVerticalAlignment(SwingConstants.CENTER);
            ownedStocksPanel.add(ownedStocksTextFields[i], gbc);


            ownedStocksTextFields[(i + 1)] = new JLabel(String.valueOf(boughtStocks.getPrice())); // bought price; Spring
            ownedStocksTextFields[i + 1].setFont(new FontUIResource("Arial", 5, 20));
            ownedStocksTextFields[i + 1].setForeground(BLACK);
            ownedStocksTextFields[i + 1].setBackground(Color.decode("#F7F5F4"));
            gbc.gridx = 2;
            gbc.gridy = (i / 4) + 1;
            setGBCOwnedStocksTextFields();
            ownedStocksTextFields[i + 1].setBorder(c);
            ownedStocksTextFields[i+1].setHorizontalAlignment(SwingConstants.CENTER);
            ownedStocksTextFields[i+1].setVerticalAlignment(SwingConstants.CENTER);
            ownedStocksPanel.add(ownedStocksTextFields[(i + 1)], gbc);

            double currentPrice = doubleValue(stockService.findPrice(boughtStocks.getTicker())); // plug in below
            double initialPrice = doubleValue(boughtStocks.getPrice());
            ownedStocksTextFields[(i + 2)] = new JLabel(String.valueOf(stockService.findPrice(boughtStocks.getTicker())));
            ownedStocksTextFields[i + 2].setFont(new FontUIResource("Arial", 5, 20));
            if (currentPrice > initialPrice) { // function to determine color of current price
                ownedStocksTextFields[i + 2].setForeground(Color.decode("#0FFF50")); // green if profit
            } else if (currentPrice < initialPrice) {
                ownedStocksTextFields[i + 2].setForeground(RED); // red if loss
            } else {
                ownedStocksTextFields[i + 2].setForeground(Color.decode("#000080")); // blue if neutral
            }
            ownedStocksTextFields[i + 2].setBackground(Color.decode("#F7F5F4"));
            gbc.gridx = 3;
            gbc.gridy = (i / 4) + 1;
            setGBCOwnedStocksTextFields();
            ownedStocksTextFields[i + 2].setBorder(c);
            ownedStocksTextFields[i+2].setHorizontalAlignment(SwingConstants.CENTER);
            ownedStocksTextFields[i+2].setVerticalAlignment(SwingConstants.CENTER);
            ownedStocksPanel.add(ownedStocksTextFields[(i + 2)], gbc);

            ownedStocksTextFields[(i + 3)] = new JLabel((String.valueOf(boughtStocks.getUnitsOfStock())));
            ownedStocksTextFields[i + 3].setFont(new FontUIResource("Arial", 5, 20));
            ownedStocksTextFields[i + 3].setForeground(BLACK); // quantity of stocks owned
            ownedStocksTextFields[i + 3].setBackground(darkGray);
            gbc.gridx = 1;
            gbc.gridy = (i / 4) + 1;
            setGBCOwnedStocksTextFields();
            ownedStocksTextFields[i + 3].setBorder(c);
            ownedStocksTextFields[i+3].setHorizontalAlignment(SwingConstants.CENTER);
            ownedStocksTextFields[i+3].setVerticalAlignment(SwingConstants.CENTER);
            ownedStocksPanel.add(ownedStocksTextFields[(i + 3)], gbc);

            i += 4; // for loop ticker; we generate 4 buttons at a time
        }

        // generate the sell buttons
        sellButtons = new JButton[len]; // length of array defined before (quantity of stocks)
        for (int j = 0; j < len; j++) {
            sellButtons[j] = new JButton("sell");
            gbc.gridx = 4;
            gbc.gridy = (j + 1);
            gbc.gridheight = 1; // grid bag layout
            gbc.gridwidth = 1;
            gbc.ipady = 40;
            gbc.ipadx = (smallerButtonWidth / 2);
            sellButtons[j].setSize((smallerButtonWidth / 2), (smallerButtonHeight / 3));
            sellButtons[j].setBackground(WHITE);
            sellButtons[j].setForeground(Color.decode("#000080"));
            ownedStocksPanel.add(sellButtons[j], gbc);
        }


        // headers at the top of the owned stocks table
        tableHeaders = new JLabel[4];
        int a = 0;
        for (JLabel tableHeader : tableHeaders) {
            tableHeaders[a] = new JLabel();
            gbc.gridx = a;
            gbc.gridy = 0;
            gbc.gridheight = 1;
            gbc.gridwidth = 1;
            gbc.ipady = (smallerButtonHeight / 2) - 5;
            gbc.ipadx = (smallerButtonWidth);
            tableHeaders[a].setBackground(BLUE);
            tableHeaders[a].setForeground(Color.decode("#000080"));
            tableHeaders[a].setFont(new FontUIResource("Arial", 5, 15));
            tableHeaders[a].setBorder(c);
            tableHeaders[a].setHorizontalAlignment(SwingConstants.CENTER);
            tableHeaders[a].setVerticalAlignment(SwingConstants.CENTER);
            ownedStocksPanel.add(tableHeaders[a], gbc);
            a++;
        }
        tableHeaders[0].setText("Ticker");
        tableHeaders[1].setText("Quantity");
        tableHeaders[2].setText("Initial ($)");
        tableHeaders[3].setText("Current ($)");

    }

    // formatting function - remove boilerplate code that gets repeated with every button generated
    private void setGBCOwnedStocksTextFields() {
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.ipady = (smallerButtonHeight); // scales according to frame size
        gbc.ipadx = (smallerButtonWidth);
    }


    // Info Panel
    ChartPanel infoPanel;
    int infoPanelWidth = frameWidth - ownedStocksPanelWidth - 15;
    int infoPanelHeight = (2 * (frameHeight - openHeaderPanelHeight - 10) / 3);
    int infoPanelYPosition = openHeaderPanelHeight + 5;
    int infoPanelXPosition = ownedStocksPanelWidth + 10;
    private void openInfoPanel() throws IOException {
        DefaultCategoryDataset dataset = createDataset();
        JFreeChart chart = ChartFactory.createLineChart("Stock Chart", "Date", "Closing Price", dataset);
        infoPanel = new ChartPanel(chart);
        infoPanel.setBounds(infoPanelXPosition,infoPanelYPosition, infoPanelWidth, infoPanelHeight);
        addPanel(infoPanel);
    }

    private DefaultCategoryDataset createDataset() throws IOException {
        Iterable<BoughtStocks> ownedStocks = boughtStocksService.getBoughtStocksByUserAccount(userAccount);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    for(BoughtStocks boughtStocks : ownedStocks) {
        HistoryDataService historyDataService = new HistoryDataService(boughtStocks.getTicker());
        historyDataService.get1YearHistoryClosePriceAndDates();
        List<BigDecimal> historicalPrice = historyDataService.getClosePriceHistory();
        List<Calendar> historicalDate = historyDataService.getHistoryDate();

        for(int i=0; i<6; i++){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
            dataset.addValue(historicalPrice.get(i), boughtStocks.getTicker(), sdf.format(historicalDate.get(i).getTime()));
            System.out.println(historicalDate.get(i).getTime());
        }
    }

    return dataset;
}


    // transaction history
    int transactionHistoryPanelHeight = ((infoPanelHeight / 3) - 20);
    int transactionHistoryPanelWidth = infoPanelWidth;
    int transactionHistoryPanelYPosition = openHeaderPanelHeight+5+infoPanelHeight+5;
    int transactionHistoryPanelXPosition = infoPanelXPosition;
    private JPanel transactionHistoryPanel;
    private JLabel[] transactionHistoryLabels;
    private void openTransactionHistoryPanel() {
        transactionHistoryPanel = new JPanel();
        transactionHistoryPanel.setBounds(transactionHistoryPanelXPosition, transactionHistoryPanelYPosition, transactionHistoryPanelWidth, transactionHistoryPanelHeight);
        transactionHistoryPanel.setBackground(Color.decode("#f0ffff"));

        JLabel titleLabel = new JLabel("Transaction History:");
        titleLabel.setBounds(0, 0, (transactionHistoryPanelWidth - 30), (transactionHistoryPanelHeight - 30));
        transactionHistoryPanel.add(titleLabel);

        String[] stockHistory = new String[100];
        int i = 0;
        for (StockHistory stockHistory1 : stockHistoryService.getUserStockHistoryByUserId(userAccount)) {
            BigDecimal price = stockHistory1.getPrice();
            int quantity = stockHistory1.getUnitsOfStock();
            UserAccount userAccount = stockHistory1.getUserAccount();
            String ticker = stockHistory1.getTicker();
            LocalDateTime time = stockHistory1.getTimeOfTransaction();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedTime = time.format(formatter);

            String stockStatus = stockHistory1.getStockStatus();
            String stockHistoryString = formattedTime + ": " + stockStatus + " "+  quantity + " x " + ticker + " for $" + price + "\n";
            stockHistory[i] = stockHistoryString;
            i++;
        }
        transactionHistoryLabels = new JLabel[i];
        int transactionHistorylabelsHeight = (transactionHistoryPanelHeight / i) - 40;
        int transactionHistoryLabelsWidth = transactionHistoryPanelWidth - 10;
//        int transactionHistoryLabelsY = (i * transactionHistorylabelsHeight) + 30;
        for (int a = 0; a < i; a++) {
            transactionHistoryLabels[a] = new JLabel(stockHistory[a]);
            int transactionHistoryLabelsY = (a * transactionHistorylabelsHeight) + 20;
            transactionHistoryLabels[a].setBounds(10, transactionHistoryLabelsY, transactionHistoryLabelsWidth, transactionHistorylabelsHeight);
            transactionHistoryPanel.add(transactionHistoryLabels[a]);
        }

        addPanel(transactionHistoryPanel);
    }


    // ButtonPanel
     JPanel buttonPanel;
    int buttonPanelHeight = (infoPanelHeight / 6) - 20;
    int buttonPanelWidth = infoPanelWidth;
    int buttonPanelYPosition = openHeaderPanelHeight+5+infoPanelHeight+5 + transactionHistoryPanelHeight + 5;
    int buttonPanelXPosition = infoPanelXPosition;
    JButton buyButton;
    JButton infoButton;
    JButton newsButton;
    JButton sadButton;
    static JTextField searchStockTextField;
    private void openButtonPanel() throws IOException { // function to open the panel
        buttonPanel = new JPanel();
        buttonPanel.setBounds(buttonPanelXPosition,buttonPanelYPosition, buttonPanelWidth, buttonPanelHeight);
        buttonPanel.setBackground(Color.decode("#f8f8f8"));
        buttonPanel.setForeground(Color.decode("#f8f8f8"));
        buttonPanel.setLayout(null);
        addPanel(buttonPanel);

        // add search field to panel
        searchStockTextField = new JTextField("search");
        int searchStockTextFieldWidth = (buttonPanelWidth / 3);
        int searchStockTextFieldHeight = 50;
        int searchStockTextFieldYPosition = (buttonPanelHeight / 2) - (searchStockTextFieldHeight / 2);
        int searchStockTextFieldXPosition = 20;
        searchStockTextField.setBounds(searchStockTextFieldXPosition, searchStockTextFieldYPosition, searchStockTextFieldWidth ,searchStockTextFieldHeight);
        buttonPanel.add(searchStockTextField);

        // add buy button to panel
        int buyButtonWidth = 100;
        int buyButtonHeight = 50;
        int buybuttonYPosition = searchStockTextFieldYPosition; // + (searchStockTextFieldHeight / 4);
        int buyButtonXPosition = (searchStockTextFieldXPosition + searchStockTextFieldWidth + 25);
        buyButton = new JButton("Buy");
        buyButton.setBounds(buyButtonXPosition,buybuttonYPosition,buyButtonWidth, buyButtonHeight);
        buyButton.setForeground(Color.decode("#000080"));
        buttonPanel.add(buyButton);

        // add info button to panel
        int infoButtonWidth = buyButtonWidth;
        int infoButtonHeight = buyButtonHeight;
        int infobuttonYPosition = buybuttonYPosition;
        int infoButtonXPosition = (buyButtonXPosition + buyButtonWidth + 25);
        infoButton = new JButton("Info");
        infoButton.setBounds(infoButtonXPosition,infobuttonYPosition,infoButtonWidth, infoButtonHeight);
        infoButton.setForeground(Color.decode("#000080"));
        buttonPanel.add(infoButton);

        // add news button to info panel
        newsButton = new JButton("News");
        int newsButtonXPosition = (infoButtonXPosition + infoButtonWidth + 25);
        newsButton.setBounds(newsButtonXPosition, infobuttonYPosition, infoButtonWidth, infoButtonHeight);
        newsButton.setForeground(Color.decode("#000080"));
        buttonPanel.add(newsButton);

        // ☹
        sadButton = new JButton("☹");
        int sadButtonXPosition = (newsButtonXPosition + infoButtonWidth + 25);
        sadButton.setBounds(sadButtonXPosition, infobuttonYPosition, infoButtonWidth, infoButtonHeight);
        sadButton.setForeground(Color.decode("#000080"));
        sadButton.setFont(new FontUIResource("Arial", 1, 20));
        buttonPanel.add(sadButton);

    }

    // function to generate all action listeners; called when the window is opened after the panel function is called
    private void addActionListeners() throws IOException {
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = searchStockTextField.getText();
                StockInfoWindow stockInfoWindow = new StockInfoWindow(input);
                try {
                    stockInfoWindow.openWindow();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

         buyButton.addActionListener(new ActionListener() {
             @Override
                public void actionPerformed(ActionEvent e) {
                 String input = searchStockTextField.getText();
                    try {
                        popUp.setTicker(input); // passes in the ticker and user account to the popup GUI
                        popUp.setUserAccount(userAccount);
                        popUp.openWindow();
                        System.out.println(input);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            searchStockTextField.addMouseListener(new MouseAdapter(){ // clears text in textfield when clicked on initially
            @Override
            public void mouseClicked(MouseEvent e){
                if (searchStockTextField.getText().equals("search")) {
                    searchStockTextField.setText("");
                }
            }
        });

        resetButton.addActionListener(new ActionListener() { // refresh the frame
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                try {
                    openFrame();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        logOut.addActionListener(new ActionListener() { // logs out
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                try {
                    loginWindow.openWindow();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        for (int i=0; i<sellButtons.length; i++) {
            String ticker = boughtStocksList.get(i).getTicker();
            BigDecimal sellPrice = stockService.findPrice(ticker);
            BoughtStocks boughtStocks = boughtStocksList.get(i);
            sellButtons[i].addActionListener(new ActionListener() { // sell stocks
                @Override
                public void actionPerformed(ActionEvent e) {
                    sellPopUp.setTicker(ticker);
                    sellPopUp.setUserAccount(userAccount);
                    sellPopUp.setBoughtStocks(boughtStocks);
                    try {
                        sellPopUp.openWindow();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

        }

        newsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRandomNewsPage(); // calls this function below
            }
        });

        sadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sadClass.openSadFrame(); // opens the sad cat class
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }

    public static String getTicker() { // retrieve the ticker for reference
        String ticker = searchStockTextField.getText();
        return ticker;
    }

    private void openRandomNewsPage() {
        String[] newsSources = new String[5]; // array of 5 urls
        newsSources[0] = "https://www.bloomberg.com/uk";
        newsSources[1] = "https://www.ft.com/";
        newsSources[2] = "https://www.reuters.com/markets/";
        newsSources[3] = "https://www.cnbc.com/markets/";
        newsSources[4] = "https://www.marketwatch.com/";

        Random rand = new Random(); // random number generator
        int upperBound = newsSources.length - 1;
        int int_random = rand.nextInt(upperBound);
        openWebPage(newsSources[int_random]); // calls the openWebPage function, passing in one of these URLs
    }

    private void openWebPage(String url){ // opens a web page, taking a URL as input
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        }
        catch (java.io.IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // add panel function to remove boilerplate code,
    // ensure visibility is set to true before panel is added
    private void addPanel(JPanel panel) {
        panel.setVisible(true);
        frame.add(panel);
    }

    // adds all panels in the openwindow function
    private void addAllPanelsToFrame() throws IOException {
        openHeaderPanel();
        openOwnedStocksPanel();
        openInfoPanel();
        openButtonPanel();
        openTransactionHistoryPanel();
    }


}
