package com.example.CapstoneProjectFile.Controller;

import com.example.CapstoneProjectFile.Model.BoughtStocks;
import com.example.CapstoneProjectFile.Service.BoughtStocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/stock")
public class StockController {

    private BoughtStocksService boughtStocksService;

    @GetMapping
    public Iterable<BoughtStocks> getAllStocks() {
        return boughtStocksService.getAllStocks();
    }

    @GetMapping(path = "{id}")
    public BoughtStocks getBoughtStocksById(@PathVariable Long id) {
        return boughtStocksService.getBoughtStocksById(id);
    }

    @Autowired
    public void setBoughtStocksService(BoughtStocksService boughtStocksService) {
        this.boughtStocksService = boughtStocksService;
    }
}
