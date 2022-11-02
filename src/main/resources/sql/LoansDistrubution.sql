

select
mlp.id as "Loan Product Id" ,
mlp.name as "Loan Product Name",
sum(ml.approved_principal) as "Principal"
from
m_loan ml
INNER JOIN m_product_loan mlp ON mlp.id = ml.product_id
GROUP BY mlp.id