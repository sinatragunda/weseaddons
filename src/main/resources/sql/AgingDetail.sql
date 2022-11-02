





SELECT

ifnull(cur.display_symbol, ml.currency_code) as Currency,
mc.account_no as "Client Account No.",
 	mc.display_name AS "Client Name",
 	ml.account_no AS "Account Number",
 	ifnull(ml.principal_amount,0) AS "Loan Amount",
 ifnull(ml.principal_disbursed_derived,0) AS "Original Principal",
 ifnull(ml.interest_charged_derived,0) AS "Original Interest",
 ifnull(ml.principal_repaid_derived,0) AS "Principal Paid",
 ifnull(ml.interest_repaid_derived,0) AS "Interest Paid",
 ifnull(laa.principal_overdue_derived,0) AS "Principal Overdue",
 ifnull(laa.interest_overdue_derived,0) AS "Interest Overdue",
DATEDIFF(CURDATE(), laa.overdue_since_date_derived) as "Days in Arrears",

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
 	IF(DATEDIFF(CURDATE(), laa.overdue_since_date_derived)<85, '12', '12+')))))))))))) )AS "Weeks In Arrears Band",

		IF(DATEDIFF(CURDATE(),  laa.overdue_since_date_derived)<31, '0 - 30',
		IF(DATEDIFF(CURDATE(),  laa.overdue_since_date_derived)<61, '30 - 60',
		IF(DATEDIFF(CURDATE(),  laa.overdue_since_date_derived)<91, '60 - 90',
		IF(DATEDIFF(CURDATE(),  laa.overdue_since_date_derived)<181, '90 - 180',
		IF(DATEDIFF(CURDATE(),  laa.overdue_since_date_derived)<361, '180 - 360',
				 '> 360'))))) AS "Days in Arrears Band"

	FROM m_office mo
    INNER JOIN m_client mc ON mc.office_id=mo.id
	    INNER JOIN m_loan ml ON ml.client_id = mc.id
	    INNER JOIN r_enum_value rev ON rev.enum_id=ml.loan_status_id AND rev.enum_name = 'loan_status_id'
    INNER JOIN m_loan_arrears_aging laa ON laa.loan_id=ml.id
    left join m_currency cur on cur.code = ml.currency_code
	WHERE ml.loan_status_id=300
    AND mo.id=${branch}