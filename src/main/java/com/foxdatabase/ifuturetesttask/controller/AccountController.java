package com.foxdatabase.ifuturetesttask.controller;

import com.foxdatabase.ifuturetesttask.service.AccountService;
import com.foxdatabase.ifuturetesttask.statistic.StatisticServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/add/{id}/{amount}")
    public ResponseEntity<HttpStatus> addAccountAmount(@PathVariable Integer id, @PathVariable Long amount) {
        accountService.addAmount(id, amount);
        StatisticServiceRequest.oneTickWriterData();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public Long getAccountAmount(@PathVariable Integer id) {
        StatisticServiceRequest.oneTickReaderData();
        return accountService.getAmount(id);
    }

    @GetMapping("/startTest")
    public void startTest() {
        StatisticServiceRequest.startHighLoadTest();
    }

    @GetMapping("/resetStatistic")
    public void resetStatistic() {
        StatisticServiceRequest.resetStatistic();
    }
}
