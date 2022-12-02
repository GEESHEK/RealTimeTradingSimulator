package com.example.CapstoneProjectFile.Service;

import com.example.CapstoneProjectFile.Model.BoughtStocks;
import com.example.CapstoneProjectFile.Model.UserAccount;
import com.example.CapstoneProjectFile.Repo.IStockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BoughtStocksService {

    private IStockRepo stockRepo;
    private UserService userService;
    private StockHistoryService stockHistoryService;

    public Iterable<BoughtStocks> getAllStocks() {
        return stockRepo.findAll();
    }

    public BoughtStocks getBoughtStocksById(Long id) {
        return stockRepo.findBoughtStocksById(id);
    }

    public void addStock(String ticker, int unitsOfStock, BigDecimal buyPrice, UserAccount userAccount) {
        LocalDateTime sellTime = LocalDateTime.now();
        stockRepo.save(new BoughtStocks(ticker, unitsOfStock, buyPrice, LocalDateTime.of(sellTime.getYear(), sellTime.getMonth()
                , sellTime.getDayOfMonth(), sellTime.getHour(), sellTime.getMinute()), userAccount));
        //-1 to subtract the value from user balance
        userService.updateBalance(-1 * (unitsOfStock * buyPrice.intValue()), userAccount);
        stockHistoryService.addBoughtStockToHistory(ticker, unitsOfStock, buyPrice, userAccount);
    }

    //this updates the units of the stocks and removes it from the database if the stock reaches zero units
    public void sellStock(int unitsOfStock, BigDecimal sellPrice, BoughtStocks boughtStocks, UserAccount userAccount) {
        BoughtStocks boughtStocks1 = stockRepo.findBoughtStocksById(boughtStocks.getId());
        boughtStocks1.setUnitsOfStock(boughtStocks1.getUnitsOfStock() - unitsOfStock);
        //boughtStocks.setUnitsOfStock(boughtStocks.getUnitsOfStock() - unitsOfStock);
        stockRepo.save(boughtStocks1);
        if (boughtStocks1.getUnitsOfStock() == 0) {
            stockRepo.delete(boughtStocks1);
        }
        //updates and increases user balance
        userService.updateBalance((unitsOfStock * sellPrice.intValue()), userAccount);
        stockHistoryService.addSellStockToHistory(unitsOfStock, sellPrice, boughtStocks, userAccount);
    }

    public List<BoughtStocks> getUserStocks(UserAccount userAccount) {
        return stockRepo.findBoughtStocksByUserAccountId(userAccount.getId());
        //find all the stocks that belongs to the user by checking the stock's user ID
    }

    @Autowired
    public void setStockRepo(IStockRepo stockRepo) {
        this.stockRepo = stockRepo;
    }

    public Iterable<BoughtStocks> getBoughtStocksByUserAccount(UserAccount userAccount) {
        return stockRepo.getBoughtStocksByUserAccount(userAccount);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setStockHistoryService(StockHistoryService stockHistoryService) {
        this.stockHistoryService = stockHistoryService;
    }
}
