package com.homemaker.Accounts.service;

import com.homemaker.Accounts.entities.BusinessUnit;
import com.homemaker.Accounts.entities.Category;
import com.homemaker.Accounts.repository.CategoryRepo;
import com.homemaker.Accounts.serviceinterface.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.homemaker.Accounts.utils.MonthlyExpansesContants.*;

@Service
public class CategoryServiceImpl extends BaseService<Category> implements ICategoryService {

    @Autowired
    CategoryRepo categoryRepo;

    public CategoryServiceImpl(CategoryRepo repository) {
        super(repository);
    }

    @Override
    public Map<String, Object> addDummyDataList() throws Exception {
        List<String> pharmaCatNameList =    Arrays.asList("A-Pharma", "B-Pharma", "C-Pharma", "D-Pharma", "E-Pharma");
        List<String> groceriesCatNameList = Arrays.asList("A-Groceries", "B-Groceries", "C-Groceries", "D-Groceries", "E-Groceries");
        List<String> babyCareCatNameList =  Arrays.asList("A-Baby", "B-Baby", "C-Baby", "D-Baby", "E-Baby");

        buSaveList(1L, pharmaCatNameList);
        buSaveList(2L, groceriesCatNameList);
        buSaveList(3L, babyCareCatNameList);

        return new HashMap<>() {{
            put(STATUS, TRUE);
            put(MESSAGE, "All record added successfully!");
        }};
    }

     void buSaveList(Long buId, List<String> buNameList){
         List<Category> catList=new ArrayList<>();
         BusinessUnit buObj = new BusinessUnit();
         buObj.setId(buId);

         buNameList.forEach(u->{
           Category category=new Category();
           category.setName(u);
           category.setBusinessUnit(buObj);
           catList.add(category);
        });
        //SAVING RECORDS.
        saveAll(catList);
    }
}
 