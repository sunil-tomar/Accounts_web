package com.homemaker.Accounts.service;

import com.homemaker.Accounts.entities.Category;
import com.homemaker.Accounts.entities.Product;
import com.homemaker.Accounts.entities.SubCategory;
import com.homemaker.Accounts.repository.ProductRepo;
import com.homemaker.Accounts.repository.SubCategoryRepo;
import com.homemaker.Accounts.serviceinterface.IProductService;
import com.homemaker.Accounts.serviceinterface.ISubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.homemaker.Accounts.utils.MonthlyExpansesContants.*;

@Service
public class ProductServiceImpl extends BaseService<Product> implements IProductService {

    @Autowired
    ProductRepo productRepo;

    public ProductServiceImpl(ProductRepo repository) {
        super(repository);
    }
    @Override
    public Map<String, Object> addDummyDataList() throws Exception {
        List<String> pharmaProdNameList =    Arrays.asList("A-Pharma", "B-Pharma", "C-Pharma", "D-Pharma", "E-Pharma");
        List<String> groceriesProdNameList = Arrays.asList("A-Groceries", "B-Groceries", "C-Groceries", "D-Groceries", "E-Groceries");
        List<String> babyCareProdNameList =  Arrays.asList("A-Baby", "B-Baby", "C-Baby", "D-Baby", "E-Baby");

        subCatSaveList(1L, pharmaProdNameList);
        subCatSaveList(2L, groceriesProdNameList);
        subCatSaveList(3L, babyCareProdNameList);

        return new HashMap<>() {{
            put(STATUS, TRUE);
            put(MESSAGE, "All record added successfully!");
        }};
    }

    void subCatSaveList(Long suCatId, List<String> catNameList){
        List<Product> prodList=new ArrayList<>();
        SubCategory subCatObj = new SubCategory();
        subCatObj.setId(suCatId);

        catNameList.forEach(u->{
            Product product=new Product();
            product.setName("Product-SubCategory"+u);
            product.setSubCategory(subCatObj);
            prodList.add(product);
        });
        //SAVING RECORDS.
        saveAll(prodList);
    }
}
 