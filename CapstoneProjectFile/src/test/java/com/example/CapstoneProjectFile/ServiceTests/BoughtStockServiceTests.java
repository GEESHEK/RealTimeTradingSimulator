package com.example.CapstoneProjectFile.ServiceTests;

import com.example.CapstoneProjectFile.Model.BoughtStocks;
import com.example.CapstoneProjectFile.Model.UserAccount;
import com.example.CapstoneProjectFile.Repo.IStockRepo;
import com.example.CapstoneProjectFile.Service.BoughtStocksService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BoughtStockServiceTests {

    BoughtStocksService uut;
    IStockRepo repository;
    BoughtStocks boughtStocks;
    UserAccount userAccount;
    String ticker;
    BigDecimal buyPrice;
    Long boughtStockId;
    Long userId;
    int unitsOfStock;
    BoughtStocks actualBoughtStocks;
    Iterable<BoughtStocks> testIterable;
    Iterable<BoughtStocks> actualIterable;
    List<BoughtStocks> testList;
    List<BoughtStocks> actualList;

    @BeforeEach
    public void setUp() {
        repository = mock(IStockRepo.class);
        boughtStocks = mock(BoughtStocks.class);
        uut = new BoughtStocksService();
        uut.setStockRepo(repository);
    }

    @Test
    void testConstruction() {
        assertNotNull(repository);
        assertNotNull(boughtStocks);
        assertNotNull(uut);
    }

    @Test
    void testGetAllStocks() {
        when(repository.findAll()).thenReturn(testIterable);
        actualIterable = uut.getAllStocks();
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetBoughtStocksByUserAccount() {
        when(repository.getBoughtStocksByUserAccount(userAccount)).thenReturn(testIterable);
        actualIterable = uut.getBoughtStocksByUserAccount(userAccount);
        verify(repository, times(1)).getBoughtStocksByUserAccount(userAccount);
    }

}
