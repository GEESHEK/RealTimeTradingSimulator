package com.example.CapstoneProjectFile.Repo;

import com.example.CapstoneProjectFile.Model.StockHistory;
import com.example.CapstoneProjectFile.Model.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITransactionalHistory extends CrudRepository<StockHistory,Long> {

    public StockHistory getStockHistoryById(Long id);

    public List<StockHistory> findStockHistoryByUserAccountId(Long id);

    public List<StockHistory> getStockHistoryByUserAccount(UserAccount userAccount);

}
