package com.example.CapstoneProjectFile;

import com.example.CapstoneProjectFile.Model.BoughtStocks;
import com.example.CapstoneProjectFile.Model.StockHistory;
import com.example.CapstoneProjectFile.Model.UserAccount;
import com.example.CapstoneProjectFile.Repo.IStockRepo;
import com.example.CapstoneProjectFile.Repo.ITransactionalHistory;
import com.example.CapstoneProjectFile.Repo.IUserAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

@Component
public class DataBasePopulator implements CommandLineRunner {

    IUserAccountRepo userRepo;
    IStockRepo stockRepo;
    ITransactionalHistory transactionalHistory;

    @Override
    public void run(String... args) throws Exception {

        UserAccount user1 = new UserAccount("user1@gmail.com", "12345", 40000.00);
        UserAccount user2 = new UserAccount("user2@gmail.com", "123456", 45000.00);
        userRepo.save(user1);
        userRepo.save(user2);


        BoughtStocks stock1 = new BoughtStocks("GOOG", 5, new BigDecimal("50"), LocalDateTime.of(2022, Month.OCTOBER, 5, 12, 30), user1);
        BoughtStocks stock2 = new BoughtStocks("AAPL", 4, new BigDecimal("100"), LocalDateTime.of(2022, Month.NOVEMBER, 5, 12, 30), user1);
        BoughtStocks stock3 = new BoughtStocks("TSLA", 2, new BigDecimal("200"), LocalDateTime.of(2022, Month.JANUARY, 5, 12, 30), user1);
//        BoughtStocks stock4 = new BoughtStocks("AAPL", 1, new BigDecimal("150"), LocalDateTime.of(2022, Month.MAY, 5, 12, 30), user1);
//        BoughtStocks stock5 = new BoughtStocks("GOOG", 10, new BigDecimal("25"), LocalDateTime.of(2022, Month.JUNE, 5, 12, 30), user1);
//        stockRepo.saveAll(List.of(stock1,stock2,stock3,stock4,stock5));
        List<BoughtStocks> stocks = Arrays.asList(stock1, stock2, stock3);
        user1.setBoughtStocks(stocks);
//        userRepo.save(user1);


        BoughtStocks stock6 = new BoughtStocks("AMD", 10, new BigDecimal("25"), LocalDateTime.of(2022, Month.JUNE, 5, 12, 30), user1);
        user1.setBoughtStocks(List.of(stock6));
        stockRepo.save(stock6);
//        user1.setBalance(100);
//        userRepo.save(user1);
//        userRepo.delete(user1);


        BoughtStocks stock7 = new BoughtStocks("AMD", 5, new BigDecimal("25"), LocalDateTime.of(2022, Month.JUNE, 5, 12, 30), user2);
        BoughtStocks stock8 = new BoughtStocks("GOOG", 10, new BigDecimal("100"), LocalDateTime.of(2022, Month.JUNE, 5, 12, 30), user2);
        BoughtStocks stock9 = new BoughtStocks("T", 10, new BigDecimal("75"), LocalDateTime.of(2022, Month.JUNE, 5, 12, 30), user2);
        List<BoughtStocks> stocks1 = List.of(stock7,stock8,stock9);
        user2.setBoughtStocks(stocks1);
//        userRepo.save(user2);
        stockRepo.save(stock7);
        stockRepo.save(stock8);
        stockRepo.save(stock9);

        //add stocks to transaction History
        StockHistory stockHistory1 = new StockHistory("GOOG", 5, new BigDecimal("50"), LocalDateTime.of(2022, Month.OCTOBER, 5, 12, 30), user1, false);
        StockHistory stockHistory2 = new StockHistory("AAPL", 4, new BigDecimal("100"), LocalDateTime.of(2022, Month.NOVEMBER, 5, 12, 30), user1, false);
        StockHistory stockHistory3 = new StockHistory("TSLA", 2, new BigDecimal("200"), LocalDateTime.of(2022, Month.JANUARY, 5, 12, 30), user1, false);
//        StockHistory stockHistory4 = new StockHistory("AAPL", 1, new BigDecimal("150"), LocalDateTime.of(2022, Month.MAY, 5, 12, 30), user1, false);
//        StockHistory stockHistory5 = new StockHistory("GOOG", 10, new BigDecimal("25"), LocalDateTime.of(2022, Month.JUNE, 5, 12, 30), user1, false);
        StockHistory stockHistory6 = new StockHistory("AMD", 10, new BigDecimal("25"), LocalDateTime.of(2022, Month.JUNE, 5, 12, 30), user1, false);
        List<StockHistory> stockHistoryUser1 = Arrays.asList(stockHistory1, stockHistory2, stockHistory3, stockHistory6);
        user1.setStockHistory(stockHistoryUser1);
        userRepo.save(user1);

        StockHistory stockHistory7 = new StockHistory("AMD", 5, new BigDecimal("25"), LocalDateTime.of(2022, Month.JUNE, 5, 12, 30), user2, false);
        StockHistory stockHistory8 = new StockHistory("GOOG", 10, new BigDecimal("100"), LocalDateTime.of(2022, Month.JUNE, 5, 12, 30), user2, false);
        StockHistory stockHistory9 = new StockHistory("T", 10, new BigDecimal("75"), LocalDateTime.of(2022, Month.JUNE, 5, 12, 30), user2, false);
        List<StockHistory> stockHistoryUser2 = List.of(stockHistory7,stockHistory8,stockHistory9);
        user2.setStockHistory(stockHistoryUser2);
        userRepo.save(user2);


        //call this method from the boughstocksservice class and not the repo like I did below
        //use the stock repo method to find stock for the user because the second method might not automatically set the stock list in the user class using the database values.
        //i.e. if I don't setboughtstocks from the user class and just save the stock to repo then it won't add the stock to the list in the user class.
//        List<BoughtStocks> stockList = stockRepo.findBoughtStocksByUserAccountId(user1.getId());
//        System.out.println("Testing the logic for finding all the stocks that belongs to user1 with ID=1, check with username");
//        for (BoughtStocks boughtStocks : stockList) {
//            System.out.println("Stock ticker = " + boughtStocks.getTicker() + " stock belongs to " + boughtStocks.getUserAccount().getUsername());
//        }
//        List<BoughtStocks> stockList3 = stockRepo.findBoughtStocksByUserAccountId(user2.getId());
//        System.out.println("Testing the logic for finding all the stocks that belongs to user2, check with username");
//        for (BoughtStocks boughtStocks : stockList3) {
//            System.out.println("Stock ticker = " + boughtStocks.getTicker() + " stock belongs to " + boughtStocks.getUserAccount().getUsername());
//        }

//        //second method > use the above method.
//        System.out.println("Second way of getting all stock is from the user itself");
//        List<BoughtStocks> stockList1 = user1.getBoughtStocks();
//        for (BoughtStocks boughtStocks:stockList1) {
//            System.out.println("Stock ticker = " + boughtStocks.getTicker() + " stock belongs to " + boughtStocks.getUserAccount().getUsername() );
//        }
//
//        System.out.println("User2 = Second way of getting all stock is from the user itself");
//        List<BoughtStocks> stockList2 = user2.getBoughtStocks();
//        for (BoughtStocks boughtStocks:stockList2) {
//            System.out.println("Stock ticker = " + boughtStocks.getTicker() + " stock belongs to " + boughtStocks.getUserAccount().getUsername() );
//        }

    }

    @Autowired
    public void setUserRepo(IUserAccountRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Autowired
    public void setStockRepo(IStockRepo stockRepo) {
        this.stockRepo = stockRepo;
    }

    @Autowired
    public void setTransactionalHistory(ITransactionalHistory transactionalHistory) {
        this.transactionalHistory = transactionalHistory;
    }

}
