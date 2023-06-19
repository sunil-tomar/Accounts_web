package com.homemaker.Accounts.service;

import com.homemaker.Accounts.entities.MonthlyExpense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homemaker.Accounts.entities.BusinessUnit;
import com.homemaker.Accounts.repository.BusinessUnitRepo;
import com.homemaker.Accounts.serviceinterface.IBusinessUnitService;

import java.security.SecureRandom;
import java.util.*;

import static com.homemaker.Accounts.utils.MonthlyExpansesContants.*;

@Service
public class BusinessUnitServiceImpl extends BaseService<BusinessUnit>
 implements IBusinessUnitService {

    @Autowired
    BusinessUnitRepo businessUnitRepo;

    public BusinessUnitServiceImpl(BusinessUnitRepo repository) {
        super(repository);
    }


    @Override
    public Map<String, Object> addDummyDataList() throws Exception {
        List<String> buNameList = Arrays.asList("Pharma", "Groceries", "Baby-Care", "Dairy",  "Electronics");
        Long[] buId={1L};

        List<BusinessUnit> buList=new ArrayList<>();
        buNameList.forEach(u->{
                BusinessUnit buObj = new BusinessUnit();
                buObj.setId(buId[0]);
                buObj.setName(u);
                buList.add(buObj);
                ++buId[0];
        });
        //SAVING RECORDS.
        saveAll(buList);
        return new HashMap<>() {{
            put(STATUS, TRUE);
            put(MESSAGE, "All record added successfully!");
        }};
    }

}
 