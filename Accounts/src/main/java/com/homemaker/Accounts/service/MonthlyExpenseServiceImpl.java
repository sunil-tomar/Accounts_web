package com.homemaker.Accounts.service;


import com.homemaker.Accounts.dto.MonthlyExpenseDto;
import com.homemaker.Accounts.entities.MonthlyExpense;
import com.homemaker.Accounts.repository.MonthlyExpenseRepo;
import com.homemaker.Accounts.serviceinterface.IMonthlyExpenseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.homemaker.Accounts.utils.MonthlyExpansesContants.*;

@Service
public class MonthlyExpenseServiceImpl extends BaseService<MonthlyExpense>
 implements IMonthlyExpenseService {

    @Autowired
    MonthlyExpenseRepo monthlyExpenseRepo;

    public MonthlyExpenseServiceImpl(MonthlyExpenseRepo repository) {
        super(repository);
    }

@Override
public Map<String, Object> addOrUpdateEntity(MonthlyExpenseDto monthlyExpenseDto){
    MonthlyExpense monthlyExpense=new MonthlyExpense();
    BeanUtils.copyProperties(monthlyExpenseDto, monthlyExpense);
    //SAVING.
    monthlyExpense= save(monthlyExpense);
    //RESPONSE.
    Map<String, Object> resp= new HashMap<>();
    resp.put(STATUS, TRUE);
    resp.put(MESSAGE, REGISTERED_SUCCESSFULLY);
    resp.put(ENTITY,monthlyExpense);
return resp;
}

}
 