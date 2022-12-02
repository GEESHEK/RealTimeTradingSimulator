package com.example.CapstoneProjectFile.Repo;

import com.example.CapstoneProjectFile.Model.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserAccountRepo extends CrudRepository<UserAccount, Long> {


    public UserAccount getUserAccountById(Long id);



    public UserAccount getUserAccountByUsername(String userName);
}
