package com.smartspring.service.v4;

import com.smartspring.beans.factory.annotation.Autowired;
import com.smartspring.dao.v4.AccountDao;
import com.smartspring.dao.v4.ItemDao;
import com.smartspring.stereotype.Component;

@Component(value="petStore")
public class PetStoreService {
    @Autowired
    private AccountDao accountDao;

    @Autowired
    private ItemDao itemDao;

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }
}