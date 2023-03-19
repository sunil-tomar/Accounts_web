package com.homemaker.Accounts.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

import static com.homemaker.Accounts.utils.RegularExpressions.*;


@Getter
@Setter
@ToString
@Entity
@Table(name = MonthlyExpense.TABLE_MONTHLY_EXPENSE)
@NamedQuery(name = "MonthlyExpense.findAll", query = "SELECT me FROM MonthlyExpense me")
public class MonthlyExpense extends BaseDomain<Long>{

	@Id
	@Column(name = COL_COLUMN_ID)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name=COL_PAID_FOR)
	@NotNull(message = AMUONT_PAID_FOR_MANDATORY_MSG)
	@Size(min= 3, max= 100, message = AMUONT_PAID_FOR_LENGTH_MSG)
	@Pattern(message = ACC_PAID_FOR_MSG, regexp = ACC_PAID_FOR_REG_KEY)
	private String paidFor;
	@Column(name=COL_AMOUNT)
	private Double amount;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = COL_CREATED_TIME, nullable = false,updatable = false, columnDefinition = DEFAULT_TIMESTAMP_SQL_QUERY)
	private Date createdTime;

	//*******************eBEGIN COLUMN NAME ********************************************/
	public static final String TABLE_MONTHLY_EXPENSE = "monthly_expense";
	private static final String COL_COLUMN_ID = "id";
	private static final String COL_PAID_FOR = "paid_for";
	private static final String COL_AMOUNT = "amount";
	private static final String COL_CREATED_TIME = "created_time";
	//*******************eEND COLUMN NAME ********************************************/

	//************************************** MSG **************************************/
	private static final String AMUONT_PAID_FOR_MANDATORY_MSG = "Amount paid for is mandatory";
	private static final String AMUONT_PAID_FOR_LENGTH_MSG = "Amount paid for must be between 3 to 100 chars Long";
	private static final String DEFAULT_TIMESTAMP_SQL_QUERY = "DATETIME default CURRENT_TIMESTAMP";

	//************************************** MSG **************************************/

	//Sample Code for setting new Date for CreateTime.
	 @PrePersist protected void onCreatedtime() { createdTime = new Date(); }





}