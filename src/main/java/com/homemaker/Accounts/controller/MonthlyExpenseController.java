package com.homemaker.Accounts.controller;

import com.homemaker.Accounts.dto.MonthlyExpenseDto;
import com.homemaker.Accounts.entities.MonthlyExpense;
import com.homemaker.Accounts.service.CommonService;
import com.homemaker.Accounts.serviceinterface.IMonthlyExpenseService;

import static com.homemaker.Accounts.utils.ACCOUNTS_URL.URL_ADD;
import static com.homemaker.Accounts.utils.MonthlyExpansesContants.STATUS;
import static com.homemaker.Accounts.utils.MonthlyExpansesContants.FALSE;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/monthly-expense")
public class MonthlyExpenseController extends CommonService<MonthlyExpense> {
@Autowired IMonthlyExpenseService iMonthlyExpenseService;

@GetMapping(value = "/fetch-all", produces = "application/json")
public ResponseEntity fetchAll(){
List<MonthlyExpense> fetchList=iMonthlyExpenseService.findAll();
Map<String, Object> resp=new HashMap<>();
resp.put("monthly-expanses-list" , fetchList);
return success(resp);
}

@PostMapping(value = URL_ADD, consumes = "application/json")
public ResponseEntity add(@RequestBody MonthlyExpenseDto monthlyExpenseDto){
    //validation.
   Map<String, Object> resp=iMonthlyExpenseService.addOrUpdateEntity(monthlyExpenseDto);
   if (resp.containsKey(STATUS)&&resp.get(STATUS).equals(FALSE)){
    return error(resp);
   }
    return success(resp);
}


    @PostMapping(value = "/add-all", consumes = "application/json")
    public ResponseEntity addAll(@RequestBody MonthlyExpenseDto monthlyExpenseDto){
        //validation.
        Map<String, Object> resp=null;
        try {
            List<MonthlyExpense> monthlyExpenseList= (List<MonthlyExpense>) iMonthlyExpenseService.saveAll(monthlyExpenseDto.getMonthlyExpensesList());
            resp =new HashMap<>();
            resp.put("monthlyExpensesList", monthlyExpenseList);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (resp.containsKey(STATUS)&&resp.get(STATUS).equals(FALSE)){
            return error(resp);
        }
        return success(resp);
    }

    @PostMapping(value = "/add-dummy-data", consumes = "application/json")
    public ResponseEntity addDummyData(){
        //validation.
        Map<String, Object> resp=null;
        try {
            resp = iMonthlyExpenseService.addDummyDataList();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (resp.containsKey(STATUS)&&resp.get(STATUS).equals(FALSE)){
            return error(resp);
        }
        return success(resp);
    }

}
