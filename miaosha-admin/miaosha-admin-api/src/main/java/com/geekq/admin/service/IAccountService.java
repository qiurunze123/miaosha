package com.geekq.admin.service;


import com.geekq.admin.entity.Account;

import java.util.List;

public interface IAccountService {

    void update(Account account);

    Account get(Long id);

    void recreateAbstractInfo();

    List<Account> listAll();
}
