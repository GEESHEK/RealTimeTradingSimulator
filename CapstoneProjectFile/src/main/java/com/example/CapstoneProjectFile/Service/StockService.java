package com.example.CapstoneProjectFile.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

@AllArgsConstructor
@Service
public class StockService {

    public Stock findStock(String ticker){
        try{
            return YahooFinance.get(ticker);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }

        return null;
    }

    public BigDecimal findPrice(String ticker) throws IOException{
        return findStock(ticker).getQuote(true).getPrice();
    }

    public BigDecimal findChangeInPercent(String ticker) throws IOException{
        return findStock(ticker).getQuote(true).getChangeInPercent();
    }

    public BigDecimal findChangeFrom200MeanPercent(String ticker) throws IOException{
        return findStock(ticker).getQuote(true).getChangeFromAvg200InPercent();
    }

    public BigDecimal findAskPrice(String ticker) throws IOException{
        return findStock(ticker).getQuote(true).getAsk();
    }

    public BigDecimal findBidPrice(String ticker) throws IOException{
        return findStock(ticker).getQuote(true).getBid();
    }

    public String findStockExchange(String ticker) throws IOException{
        return findStock(ticker).getStockExchange();
    }

    public String findName(String ticker) throws IOException{
        return findStock(ticker).getName();
    }

    public BigDecimal findMarketCap(String ticker) throws IOException{
        return findStock(ticker).getStats(true).getMarketCap();
    }

    public String findCurrency(String ticker) throws IOException {
        return findStock(ticker).getCurrency();
    }

    public List<HistoricalQuote> find1YearHistory(String ticker) throws IOException{
        Calendar from = Calendar.getInstance();
        from.add(Calendar.MONTH, -6);
        return findStock(ticker).getHistory(from, Interval.MONTHLY);
    }


}
