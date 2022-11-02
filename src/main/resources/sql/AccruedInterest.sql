
select
if(transaction_type_enum = 10 ,interest_portion_derived ,0) as "Accrued Interest"
from
m_loan_transaction
where
loan_id = ${loanId}