
select
ml.loanpurpose_cv_id ,
mcv.code_value as "Loan Purpose",
sum(ml.approved_principal) as "Principal"
from
m_loan ml
INNER JOIN m_code_value mcv ON mcv.id = ml.loanpurpose_cv_id
GROUP BY ml.loanpurpose_cv_id