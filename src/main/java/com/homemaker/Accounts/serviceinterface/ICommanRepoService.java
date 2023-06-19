package com.homemaker.Accounts.serviceinterface;

import com.homemaker.Accounts.dto.MonthlyExpenseDto;
import org.aspectj.bridge.Message;

import static com.homemaker.Accounts.utils.MonthlyExpansesContants.STATUS;
import static com.homemaker.Accounts.utils.MonthlyExpansesContants.FALSE;
import static com.homemaker.Accounts.utils.MonthlyExpansesContants.MESSAGE;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ICommanRepoService<Type> {

    Type findById(Long id);

    List<Type> findAll();

    Type save(Type entity);

    Iterable<Type> saveAll(List<Type> all);


    default Map<String, Object> addOrUpdateEntity(MonthlyExpenseDto monthlyExpenseDto) {
        return notImplementedHashMap();
    }

    default Map<String, Object> addDummyDataList() throws Exception {
        return notImplementedHashMap();
    }

    default Map<String, Object> notImplementedHashMap(){
        Map<String, Object> map=new HashMap<>();
        map.put(STATUS, FALSE);
        map.put(MESSAGE, "method implementation not avaiable in service class.");
        return map;
    }

}