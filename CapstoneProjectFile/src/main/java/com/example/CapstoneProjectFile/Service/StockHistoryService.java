package com.example.CapstoneProjectFile.Service;

import com.example.CapstoneProjectFile.Model.BoughtStocks;
import com.example.CapstoneProjectFile.Model.StockHistory;
import com.example.CapstoneProjectFile.Model.UserAccount;
import com.example.CapstoneProjectFile.Repo.ITransactionalHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockHistoryService {

    private ITransactionalHistory transactionalHistory;

    public void addBoughtStockToHistory(String ticker, int unitsOfStock, BigDecimal buyPrice, UserAccount userAccount) {
        LocalDateTime sellTime = LocalDateTime.now();
        transactionalHistory.save(new StockHistory(ticker, unitsOfStock, buyPrice, LocalDateTime.of(sellTime.getYear(), sellTime.getMonth()
                , sellTime.getDayOfMonth(), sellTime.getHour(), sellTime.getMinute()), userAccount, false));
    }

    public void addSellStockToHistory(int unitsOfStock, BigDecimal sellPrice, BoughtStocks boughtStocks, UserAccount userAccount) {
        LocalDateTime sellTime = LocalDateTime.now();
        transactionalHistory.save(new StockHistory(boughtStocks.getTicker(), unitsOfStock, sellPrice, LocalDateTime.of(sellTime.getYear(), sellTime.getMonth()
                , sellTime.getDayOfMonth(), sellTime.getHour(), sellTime.getMinute()), userAccount, true));
    }

    public List<StockHistory> getUserStockHistoryByUserId(UserAccount userAccount) {
        return transactionalHistory.findStockHistoryByUserAccountId(userAccount.getId());
    }

    public List<StockHistory> getStockHistoryByUserAccount(UserAccount userAccount) {
        return transactionalHistory.getStockHistoryByUserAccount(userAccount);
    }

    @Autowired
    public void setTransactionalHistory(ITransactionalHistory transactionalHistory) {
        this.transactionalHistory = transactionalHistory;
    }
}
