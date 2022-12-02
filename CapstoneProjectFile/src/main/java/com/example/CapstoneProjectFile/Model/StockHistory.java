package com.example.CapstoneProjectFile.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "StockHistory")
public class StockHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String ticker;
    private int unitsOfStock;
    private BigDecimal price;
    private boolean isSold;
    private String stockStatus;
    private LocalDateTime timeOfTransaction;
    @ManyToOne
    @JoinColumn(name = "userAccountId")
    @JsonIgnoreProperties("stockHistory")
    private UserAccount userAccount;

    public StockHistory() {
    }

    public StockHistory(String ticker, int unitsOfStock, BigDecimal price, LocalDateTime timeOfTransaction, UserAccount userAccount, boolean isSold) {
        this.ticker = ticker;
        this.unitsOfStock = unitsOfStock;
        this.price = price;
        this.timeOfTransaction = timeOfTransaction;
        this.userAccount = userAccount;
        this.isSold = isSold;
        this.stockStatus = isSold ? "sold" : "bought";
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public int getUnitsOfStock() {
        return unitsOfStock;
    }

    public void setUnitsOfStock(int unitsOfStock) {
        this.unitsOfStock = unitsOfStock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }

    public String getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(String stockStatus) {
        this.stockStatus = stockStatus;
    }

    public LocalDateTime getTimeOfTransaction() {
        return timeOfTransaction;
    }

    public void setTimeOfTransaction(LocalDateTime timeOfTransaction) {
        this.timeOfTransaction = timeOfTransaction;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
}
