package com.example.CapstoneProjectFile.Service;

import yahoofinance.histquotes.HistoricalQuote;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HistoryDataService {
    StockService stockService = new StockService();

    String ticker;

    int len;

    public HistoryDataService(String ticker) {
        this.ticker = ticker;
    }

    List<BigDecimal> ClosePriceHistory = new ArrayList<>();
    List<Calendar> historyDate = new ArrayList<>();

    public List<BigDecimal> getClosePriceHistory() {
        return ClosePriceHistory;
    }

    public List<Calendar> getHistoryDate() {
        return historyDate;
    }

    public void get1YearHistoryClosePriceAndDates() throws IOException {
        for (HistoricalQuote historicalQuote : stockService.find1YearHistory(ticker)) {
            ClosePriceHistory.add(historicalQuote.getClose());
            historyDate.add(historicalQuote.getDate());
        }
        len = ClosePriceHistory.size();
    }

    public int getLen() {
        return len;
    }




}
