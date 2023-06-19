package com.homemaker.Accounts.controller;

import com.homemaker.Accounts.dto.MonthlyExpenseDto;
import com.homemaker.Accounts.entities.BusinessUnit;
import com.homemaker.Accounts.entities.Category;
import com.homemaker.Accounts.entities.MonthlyExpense;
import com.homemaker.Accounts.entities.SubCategory;
import com.homemaker.Accounts.service.CommonService;
import com.homemaker.Accounts.serviceinterface.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.homemaker.Accounts.utils.MonthlyExpansesContants.FALSE;
import static com.homemaker.Accounts.utils.MonthlyExpansesContants.STATUS;

@RestController
//@RequestMapping("/monthly-expense")
public class ProductMgmtController extends CommonService<MonthlyExpense> {
@Autowired
IBusinessUnitService iBusinessUnitService;
@Autowired
ICategoryService iCategoryService;
@Autowired
ISubCategoryService iSubCategoryService;
@Autowired
IProductService iProductService;

    @GetMapping(value = "/add-bu-data", produces = "application/json")
    public ResponseEntity addDummyData(){
        //validation.
        Map<String, Object> resp=null;
        try {
            resp = iBusinessUnitService.addDummyDataList();
            resp.putAll(iCategoryService.addDummyDataList());
            resp.putAll(iSubCategoryService.addDummyDataList());
            resp.putAll(iProductService.addDummyDataList());
        }catch (Exception e){
            e.printStackTrace();
        }
        if (resp.containsKey(STATUS)&&resp.get(STATUS).equals(FALSE)){
            return error(resp);
        }
        return success(resp);
    }
    @GetMapping(value = "/fetch-produc-by-data"+"/{id}", produces = "application/json")
    public ResponseEntity fetchProductByBuId(@PathVariable("id") Long buId){
        //validation.
        Map<String, Object> resp=new HashMap<>();
        try {
            BusinessUnit bu = iBusinessUnitService.findById(buId);
            Set<Category> categorySet=bu.getCategorySet();
            List<Long> categoryIds=new ArrayList<>();
            for (Category category : categorySet) {
                categoryIds.add(category.getId());
            }
            Set<SubCategory> subCategorySet=iSubCategoryService.findByCategoryIdIn(categoryIds);
            resp.put("subCategorySet", subCategorySet);

        }catch (Exception e){
            e.printStackTrace();
        }
        if (resp.containsKey(STATUS)&&resp.get(STATUS).equals(FALSE)){
            return error(resp);
        }
        return success(resp);
    }


}
