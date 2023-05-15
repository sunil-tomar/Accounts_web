package com.homemaker.Accounts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homemaker.Accounts.entities.BusinessUnit;
import com.homemaker.Accounts.repository.BusinessUnitRepo;
import com.homemaker.Accounts.serviceinterface.IBusinessUnitService;

@Service
public class BusinessUnitServiceImpl extends BaseService<BusinessUnit>
 implements IBusinessUnitService {

    @Autowired
    BusinessUnitRepo businessUnitRepo;

    public BusinessUnitServiceImpl(BusinessUnitRepo repository) {
        super(repository);
    }

}
 