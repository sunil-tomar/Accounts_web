package com.homemaker.Accounts.dto;

import com.homemaker.Accounts.entities.MonthlyExpense;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class MonthlyExpenseDto extends MonthlyExpense
{
    private String paidFor;
    private Double amount;
    private List<MonthlyExpense> monthlyExpensesList;

}
