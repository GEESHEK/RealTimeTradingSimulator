package com.example.CapstoneProjectFile.ServiceTests;

import com.example.CapstoneProjectFile.Model.StockHistory;
import com.example.CapstoneProjectFile.Model.UserAccount;
import com.example.CapstoneProjectFile.Repo.ITransactionalHistory;
import com.example.CapstoneProjectFile.Service.StockHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class StockHistoryServiceTest {

    StockHistoryService uut;
    ITransactionalHistory repository;
    StockHistory stockHistory;
    UserAccount userAccount;
    List<StockHistory> testList;
    List<StockHistory> actualList;

    @BeforeEach
    public void setUp(){
        repository = mock(ITransactionalHistory.class);
        stockHistory = mock(StockHistory.class);
        userAccount = mock(UserAccount.class);
        uut = new StockHistoryService();
        uut.setTransactionalHistory(repository);
    }

    @Test
    void testConstruction(){
        assertNotNull(repository);
        assertNotNull(stockHistory);
        assertNotNull(uut);
    }

    @Test
    void testGetStockHistoryByUserAccount() {
        when(repository.getStockHistoryByUserAccount(userAccount)).thenReturn(testList);
        actualList = uut.getStockHistoryByUserAccount(userAccount);
        verify(repository,times(1)).getStockHistoryByUserAccount(userAccount);
    }

    @Test
    void testGetStockHistoryById() {
        UserAccount userAccount1 = new UserAccount("user1","1234",4510);
        Long id = userAccount1.getId();
        when(repository.findStockHistoryByUserAccountId(id)).thenReturn(testList);
        actualList = uut.getUserStockHistoryByUserId(userAccount1);
        verify(repository,times(1)).findStockHistoryByUserAccountId(id);
    }


}
