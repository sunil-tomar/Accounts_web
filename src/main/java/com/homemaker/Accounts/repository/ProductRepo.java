
package com.homemaker.Accounts.repository;

import com.homemaker.Accounts.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {

}
