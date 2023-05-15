
package com.homemaker.Accounts.serviceinterface;

import com.homemaker.Accounts.entities.MonthlyExpense;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import static com.homemaker.Accounts.utils.MonthlyExpansesContants.*;

public interface IMonthlyExpenseService extends ICommanRepoService<MonthlyExpense> {
    default Map<String, Object> addDummyDataList() throws Exception {
        return new HashMap<>() {{
            put(STATUS, FALSE);
            put(MESSAGE, "method implementation not avaiable in service class.");
        }};
    }
}
 