CREATE VIEW [dbo].[vw_ParWorkflow]
AS
SELECT     dbo.jtblPar_WorkflowDetail.task_indicator, dbo.jtblPar_WorkflowDetail.empid, dbo.jtblPar_Employee.EmpLName, dbo.jtblPar_Employee.EmpFName, 
                      dbo.jtblPar_WorkflowDetail.add_datetime_start, dbo.jtblPar_WorkflowDetail.add_datetime_end, dbo.jtblPar_WorkflowHeader.items_ordered, dbo.jtblPar_WorkflowDetail.items_processed,
                      dbo.jtblPar_WorkflowDetail.status_trans, dbo.jtblPar_WorkflowHeader.ord_no, dbo.jtblPar_WorkflowHeader.cus_no, 
                      dbo.jtblPar_WorkflowHeader.ord_dt, dbo.jtblPar_WorkflowHeader.ship_to_name, dbo.jtblPar_WorkflowHeader.status_completed, 
                      right(rtrim(dbo.jtblPar_WorkflowHeader.cus_alt_adr_cd),3) as cus_code, dbo.jtblPar_WorkflowHeader.cus_alt_adr_cd, dbo.jtblPar_WorkflowDetail.benchmark, dbo.jtblPar_WorkflowDetail.cart_count, DATEDIFF(MINUTE, 
                      dbo.jtblPar_WorkflowDetail.add_datetime_start, dbo.jtblPar_WorkflowDetail.add_datetime_end) AS calc_time_in_mins
FROM         dbo.jtblPar_Employee INNER JOIN
                      dbo.jtblPar_WorkflowDetail ON dbo.jtblPar_Employee.EmpID = dbo.jtblPar_WorkflowDetail.empid RIGHT OUTER JOIN
                      dbo.jtblPar_WorkflowHeader ON dbo.jtblPar_WorkflowDetail.ord_no = dbo.jtblPar_WorkflowHeader.ord_no
WHERE 
			dbo.jtblPar_WorkflowDetail.status_trans is not null
GO