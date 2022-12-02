package com.example.CapstoneProjectFile.Repo;

import com.example.CapstoneProjectFile.Model.BoughtStocks;
import com.example.CapstoneProjectFile.Model.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStockRepo extends CrudRepository<BoughtStocks,Long> {

    public BoughtStocks findBoughtStocksById(Long id);

    public List<BoughtStocks> findBoughtStocksByUserAccountId(Long id);

    public Iterable<BoughtStocks> getBoughtStocksByUserAccount(UserAccount userAccount);
//    public List<BoughtStocks> findBoughtStocksByUserAccountId(Long id);
}
