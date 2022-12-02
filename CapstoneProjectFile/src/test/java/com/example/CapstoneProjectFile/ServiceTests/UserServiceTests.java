package com.example.CapstoneProjectFile.ServiceTests;

import com.example.CapstoneProjectFile.Model.UserAccount;
import com.example.CapstoneProjectFile.Repo.IUserAccountRepo;
import com.example.CapstoneProjectFile.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
public class UserServiceTests {


    UserService uut;
    IUserAccountRepo repo;
    UserAccount userAccount;
    UserAccount actualUserAccount;
    String userName;
    Iterable<UserAccount> testIterable;
    Iterable<UserAccount> actualIterable;
    Long Id;


    @BeforeEach
    public void setUp(){
        repo = mock(IUserAccountRepo.class);
        userAccount = mock(UserAccount.class);
        uut = new UserService();
        uut.setUserRepo(repo);
    }

    @Test
    void testConstruction(){
        assertNotNull(repo);
        assertNotNull(uut);
        assertNotNull(userAccount);
    }

    @Test
    void testGetAllUsers(){
        when(repo.findAll()).thenReturn(testIterable);
        actualIterable = uut.getAllUsers();
        verify(repo,times(1)).findAll();
    }

    @Test
    void testGetUserByUserName(){
        userName = "user55";
        when(repo.getUserAccountByUsername(userName)).thenReturn(userAccount);
        actualUserAccount = uut.getUserByUserName(userName);
        verify(repo,times(1)).getUserAccountByUsername(userName);
    }

    @Test
    void testGetUserAccountById() {
        Long id = 1L;
        when(repo.getUserAccountById(id)).thenReturn(userAccount);
        actualUserAccount = uut.getUserAccountById(id);
        verify(repo,times(1)).getUserAccountById(id);
    }



}
