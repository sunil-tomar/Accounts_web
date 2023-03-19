package com.homemaker.Accounts;

import com.homemaker.Accounts.dto.MonthlyExpenseDto;
import com.homemaker.Accounts.entities.MonthlyExpense;
import com.homemaker.Accounts.service.CommonCodeService;
import com.homemaker.Accounts.serviceinterface.IMonthlyExpenseService;
import static com.homemaker.Accounts.utils.MonthlyExpansesContants.STATUS;
import static com.homemaker.Accounts.utils.MonthlyExpansesContants.FALSE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/monthly-expense")
public class MonthlyExpenseController extends CommonCodeService<MonthlyExpense> {
@Autowired IMonthlyExpenseService iMonthlyExpenseService;

@GetMapping(value = "/fetch-all", produces = "application/json")
public ResponseEntity fetchAll(){
List<MonthlyExpense> fetchList=iMonthlyExpenseService.findAll();
Map<String, Object> resp=new HashMap<>();
resp.put("monthly-expanses-list" , fetchList);
return success(resp);
}

@PostMapping(value = "/add", consumes = "application/json")
public ResponseEntity add(@RequestBody MonthlyExpenseDto monthlyExpenseDto){
    //validation.
   Map<String, Object> resp=iMonthlyExpenseService.addOrUpdateEntity(monthlyExpenseDto);
   if (resp.containsKey(STATUS)&&resp.get(STATUS).equals(FALSE)){
    return error(resp);
   }
    return success(resp);
}


}
