
package com.homemaker.Accounts.repository;

import com.homemaker.Accounts.entities.MonthlyExpense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthlyExpenseRepo extends JpaRepository<MonthlyExpense, Long> {

}
