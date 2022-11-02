select
sum(ml.approved_principal) as "Principal",
mcv.code_value AS "Gender",
count(ml.id) AS "Number of Clients" 
from 
m_loan ml 
INNER JOIN m_client mc ON mc.id = ml.client_id
INNER JOIN  m_code_value mcv ON mcv.id = mc.gender_cv_id
GROUP BY Gender