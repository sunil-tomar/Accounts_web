
package com.homemaker.Accounts.repository;

import com.homemaker.Accounts.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {

}
