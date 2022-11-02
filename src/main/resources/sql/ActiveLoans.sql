select
o.name as "Office Name",
l.id AS "Loan Id",
ifnull(cur.display_symbol, l.currency_code) as Currency,
ifnull(lo.`display_name`,'-') as "Loan Officer",
ifnull(en.`code_value`,'-') AS "Employer Name",
ifnull(ed.`Employee_id`,'-') AS "Employee Id",
ifnull(cv.`code_value`,'-') AS "Sector Categorisation",
c.display_name as "Client",
ifnull(c.`date_of_birth`,'-') as "Date Of Birth",
ifnull(c.`account_no`,'-') AS "Customer No",
l.account_no as "Loan Account No",
ifnull(ci.`document_key`,'-') AS "ID Document",
ifnull(v.`code_value`,'-') as "Gender",
ifnull(pl.`name`,'-') as "Product",
ifnull(d.`description`,'-') as "Collateral",
ifnull(c.`mobile_no`,'-') AS "Mobile No",
ifnull(f.`name`,'-') as Fund,
ifnull(l.`principal_amount`,0) as "Loan Amount",
ifnull(l.`annual_nominal_interest_rate`,0)as "Annual Nominal Interest Rate",
date(l.disbursedon_date) as "Disbursed Date",
date(l.expected_maturedon_date) as "Expected Matured On",
l.`term_frequency` as "Tenor",

ifnull(l.`principal_repaid_derived`,0) as "Principal Repaid",
ifnull(l.`principal_outstanding_derived`,0) as "Principal Outstanding",
ifnull(laa.`principal_overdue_derived`,0) as "Principal Overdue",

ifnull(l.`interest_repaid_derived`,0.00) as "Interest Repaid",
ifnull(l.`interest_outstanding_derived`,0) as "Interest Outstanding",
ifnull(laa.`interest_overdue_derived`,0) as "Interest Overdue",
ifnull(l.`fee_charges_repaid_derived`,0) as "Fees Repaid",
ifnull(l.`fee_charges_outstanding_derived`,0)  as "Fees Outstanding",
ifnull(laa.`fee_charges_overdue_derived`,0) as "Fees Overdue",

ifnull(l.`penalty_charges_repaid_derived`,0) as "Penalties Repaid",
ifnull(l.`penalty_charges_outstanding_derived` ,0)as "Penalties Outstanding",
ifnull(penalty_charges_overdue_derived,0) as "Penalties Overdue",
ifnull(DATEDIFF(CURDATE(), laa.overdue_since_date_derived),0) as "Total Due",

 	IF(DATEDIFF(CURDATE(), laa.overdue_since_date_derived)<7, '<1',
 	IF(DATEDIFF(CURDATE(), laa.overdue_since_date_derived)<8, ' 1',
 	IF(DATEDIFF(CURDATE(), laa.overdue_since_date_derived)<15,  '2',
 	IF(DATEDIFF(CURDATE(), laa.overdue_since_date_derived)<22, ' 3',
 	IF(DATEDIFF(CURDATE(), laa.overdue_since_date_derived)<29, ' 4',
 	IF(DATEDIFF(CURDATE(), laa.overdue_since_date_derived)<36, ' 5',
 	IF(DATEDIFF(CURDATE(), laa.overdue_since_date_derived)<43, ' 6',
 	IF(DATEDIFF(CURDATE(), laa.overdue_since_date_derived)<50, ' 7',
 	IF(DATEDIFF(CURDATE(), laa.overdue_since_date_derived)<57, ' 8',
 	IF(DATEDIFF(CURDATE(), laa.overdue_since_date_derived)<64, ' 9',
 	IF(DATEDIFF(CURDATE(), laa.overdue_since_date_derived)<71, '10',
 	IF(DATEDIFF(CURDATE(), laa.overdue_since_date_derived)<78, '11',
 	IF(DATEDIFF(CURDATE(), laa.overdue_since_date_derived)<85, '12', '12+')))))))))))) )AS "Past Due",

		IF(DATEDIFF(CURDATE(),  laa.overdue_since_date_derived)<31, '0 - 30',
		IF(DATEDIFF(CURDATE(),  laa.overdue_since_date_derived)<61, '30 - 60',
		IF(DATEDIFF(CURDATE(),  laa.overdue_since_date_derived)<91, '60 - 90',
		IF(DATEDIFF(CURDATE(),  laa.overdue_since_date_derived)<181, '90 - 180',
		IF(DATEDIFF(CURDATE(),  laa.overdue_since_date_derived)<361, '180 - 360',
				 '> 360'))))) AS "Aging Brack",
		if(lsl.GRZ_loan=1, 'Yes','No') as "GRZ Loan",
IFNULL (lsl.`Description`,'-') as "Description"

from m_office o
join m_client c on c.office_id = o.id
join m_loan l on l.client_id = c.id
left join m_loan_collateral d on d.loan_id = l.id
left join m_code_value v on v.id = c.gender_cv_id
left join m_code_value cv on cv.id =c.client_classification_cv_id

left join m_product_loan pl on pl.id = l.product_id
left join m_staff lo on lo.id = l.loan_officer_id
left join Employment_detailz ed on ed.client_id = c.id
left join m_code_value en on en.id = ed.Employer_cd_EmployerName
left join m_client_identifier ci on ci.client_id = c.id
left join Late_Start_Loan lsl on lsl.loan_id = l.id

left join m_currency cur on cur.code = l.currency_code
left join m_fund f on f.id = l.fund_id
left join m_loan_arrears_aging laa on laa.loan_id = l.id
left join m_loan_repayment_schedule lrs on lrs.loan_id = l.id

where o.id = ${officeId}
and l.currency_code = "${currencyId}"
and l.loan_status_id = 300

group by l.id