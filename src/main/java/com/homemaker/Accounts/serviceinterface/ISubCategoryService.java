
package com.homemaker.Accounts.serviceinterface;

import com.homemaker.Accounts.entities.SubCategory;

import java.util.List;
import java.util.Set;

public interface ISubCategoryService extends ICommanRepoService<SubCategory> {

    Set<SubCategory> findByCategoryIdIn(List<Long> categoryIds);

}
 