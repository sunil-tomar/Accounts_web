package com.homemaker.Accounts.service;

import com.homemaker.Accounts.entities.BusinessUnit;
import com.homemaker.Accounts.entities.Category;
import com.homemaker.Accounts.entities.SubCategory;
import com.homemaker.Accounts.repository.BusinessUnitRepo;
import com.homemaker.Accounts.repository.SubCategoryRepo;
import com.homemaker.Accounts.serviceinterface.ISubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.homemaker.Accounts.utils.MonthlyExpansesContants.*;

@Service
public class SubCategoryServiceImpl extends BaseService<SubCategory> implements ISubCategoryService {

    @Autowired
    SubCategoryRepo subCategoryRepo;

    public SubCategoryServiceImpl(SubCategoryRepo repository) {
        super(repository);
    }
    @Override
    public Map<String, Object> addDummyDataList() throws Exception {
        List<String> pharmaSubCatNameList =    Arrays.asList("A-Pharma", "B-Pharma", "C-Pharma", "D-Pharma", "E-Pharma");
        List<String> groceriesSubCatNameList = Arrays.asList("A-Groceries", "B-Groceries", "C-Groceries", "D-Groceries", "E-Groceries");
        List<String> babyCareSubCatNameList =  Arrays.asList("A-Baby", "B-Baby", "C-Baby", "D-Baby", "E-Baby");

        subCatSaveList(1L, pharmaSubCatNameList);
        subCatSaveList(2L, groceriesSubCatNameList);
        subCatSaveList(3L, babyCareSubCatNameList);

        return new HashMap<>() {{
            put(STATUS, TRUE);
            put(MESSAGE, "All record added successfully!");
        }};
    }

    void subCatSaveList(Long catId, List<String> catNameList){
        List<SubCategory> catList=new ArrayList<>();
        Category catObj = new Category();
        catObj.setId(catId);

        catNameList.forEach(u->{
            SubCategory subCategory=new SubCategory();
            subCategory.setName("Sub-"+u);
            subCategory.setCategory(catObj);
            catList.add(subCategory);
        });
        //SAVING RECORDS.
        saveAll(catList);
    }

    @Override
    public Set<SubCategory> findByCategoryIdIn(List<Long> categoryIds) {
        return subCategoryRepo.findByCategoryIdIn(categoryIds);
    }
}
 