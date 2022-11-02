select
details.edate entry_date,
details.acid1,
details.report_header,
details.reportid,
details.account_name,
details.debit_amount,
details.credit_amount,
details.aftertxn,
details.description,
opb.openingbalance,
branch.branchname,
details.transactionid
from
(
select je.account_id acid1,
 # concat(gl.gl_code,"-",gl.name) as report_header,
 concat(gl.gl_code,"-",gl.name) as report_header,
 gl.gl_code as reportid,
 je2.entry_date edate,
 concat(gl2.gl_code,"-",gl2.name) as account_name,
if (je2.type_enum=1, je2.amount, 0) as debit_amount,
if (je2.type_enum=2, je2.amount, 0) as credit_amount,
je2.id,
je2.office_id,
je2.transaction_id,
 je2.type_enum,
je.office_running_balance as aftertxn,
je2.description as description,
je2.transaction_id as transactionid
from acc_gl_journal_entry je
left join acc_gl_account gl on gl.id = je.account_id
left join acc_gl_journal_entry je2
on je2.transaction_id = je.transaction_id and je2.account_id <>${GLAccountNO}
left join acc_gl_account gl2 on gl2.id = je2.account_id
where je.account_id =${GLAccountNO}
and je.office_id = ${officeId}
and je.reversed = 0
and je.entry_date between "${startDate}" and "${endDate}"
order by je.entry_date, je.id) details
left join
(select
je.account_id acid2,
je.office_running_balance as openingbalance
from acc_gl_journal_entry je
where je.account_id =${GLAccountNO}
and je.office_id = ${officeId}
and je.reversed = 0
and je.entry_date <= "${startDate}"
and id =
(select max(id) from acc_gl_journal_entry je
where je.account_id =${GLAccountNO}
and je.office_id = ${officeId}
and je.reversed = 0
and je.entry_date <= "${startDate}"
)      )opb
on opb.acid2=details.acid1
left join
(
select name branchname
from m_office mo
where
mo.id=${officeId}
)branch
on details.office_id=${officeId}
order by details.edate,details.reportid