
package com.homemaker.Accounts.repository;

import com.homemaker.Accounts.entities.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface SubCategoryRepo extends JpaRepository<SubCategory, Long> {

    Set<SubCategory> findByCategoryIdIn(List<Long> categoryIds);
}
