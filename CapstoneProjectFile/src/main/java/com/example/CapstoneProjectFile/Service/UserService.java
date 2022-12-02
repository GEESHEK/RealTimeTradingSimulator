package com.example.CapstoneProjectFile.Service;

import com.example.CapstoneProjectFile.Model.BoughtStocks;
import com.example.CapstoneProjectFile.Model.UserAccount;
import com.example.CapstoneProjectFile.Repo.IUserAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private IUserAccountRepo userRepo;

    public Iterable<UserAccount> getAllUsers() {
        return userRepo.findAll();
    }

    public UserAccount getUserAccountById(Long id) {
        return userRepo.getUserAccountById(id);
    }

    public UserAccount getUserByUserName(String userName){return userRepo.getUserAccountByUsername(userName);}

    //this updates the users balance from the stock service class from buying and selling, and makes sure the updates are saved to the repo
    public void updateBalance(int totalStockValue, UserAccount userAccount) {
        UserAccount updatedUser = userRepo.getUserAccountById(userAccount.getId());
        updatedUser.setBalance(userAccount.getBalance() + totalStockValue);
        userRepo.save(updatedUser);
    }

    //use the one in the stock service to find the stocks that belong to the user
    public List<BoughtStocks> getAllUserStocks(UserAccount userAccount) {
        UserAccount boughtStocks = userRepo.getUserAccountById(userAccount.getId());
        return boughtStocks.getBoughtStocks();
    }

    //Updated user balance, must use this method to get the latest user balance.
    public double getUserBalance(UserAccount userAccount) {
        UserAccount getUpdatedBalance = userRepo.getUserAccountById(userAccount.getId());
        return getUpdatedBalance.getBalance();
    }


    @Autowired
    public void setUserRepo(IUserAccountRepo userRepo) {
        this.userRepo = userRepo;
    }

    public String verifyUserLogIn(String userName, String password){
        if (userRepo.getUserAccountByUsername(userName)!=null && userRepo.getUserAccountByUsername(userName).getPassword().equals(password)){
            return userName;
        }
        return null;
    }
}
