
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE view [dbo].[vw_ParWorkflowParDeptMap]
AS
	--create a table with the total number of distinct items in each location (one header code = one location)
	with CteItemCount (headercode, item_count)
	as
	(
		select headercode, COUNT(item_no) from rtblDetail group by HeaderCode
	),
	--create a table including the customer names for each location (header code is the pk for the table rtblHeader)
	CteDeptName (cus_name, cus_alt_adr_cd, HeaderCode, Remarks, cus_no)
	as
	(	
		select a.cus_name, h.cus_alt_adr_cd, h.HeaderCode, h.Remarks, h.cus_no as cus_no
		from arcusfil_sql c inner join araltadr_sql a on c.cus_no = a.cus_no
		right outer join rtblHeader h on h.cus_no = c.user_def_fld_1
		and h.cus_alt_adr_cd = a.cus_alt_adr_cd
		where a.cus_name is not null
	)

	select h.cus_no, h.HeaderCode, right(rtrim(h.cus_alt_adr_cd), 3) as cus_code,
	h.cus_alt_adr_cd, h.cus_name as [ship_to_name],
	rtrim(h.cus_alt_adr_cd) + ':  ' + h.cus_name as display_name, e.date_time_added,
	emp.EmpID, emp.EmpLName + ', ' + emp.EmpFName as [EmpName], emp.Activity as status_emp_active, 
	e.status_active_addr, e.status_complete as [status_complete_assignment],
	e.assignment_code, asn.assignment_desc, d.item_count as item_total, h.Remarks, e.batch_date
	from CteDeptName h
	inner join jtblPar_EmpAssignHeader e on e.headercode = h.HeaderCode
	inner join jtblPar_AssignmentCatagory asn on e.assignment_code = asn.assignment_code
	inner join CteItemCount d on d.HeaderCode = h.HeaderCode
	inner join jtblPar_Employee emp on e.empid = emp.EmpID
GO