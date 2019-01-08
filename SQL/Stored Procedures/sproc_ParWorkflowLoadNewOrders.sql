/****** Object:  StoredProcedure [dbo].[sproc_ParWorkflowLoadNewOrders] 
 
 The procedure has two functions 
 1. insert most recent orders by location into the parworkflow header table for processing.
 2. if an more recent order with the SAME location is inserted into the header table it will
  flagged as incompleted 'I' in the header table

******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE procedure [dbo].[sproc_ParWorkflowLoadNewOrders]

AS

set nocount on;

--insert orders into the header table 
insert into jtblPar_WorkflowHeader(ord_no, HeaderCode, cus_no, ship_to_name,  cus_alt_adr_cd, ord_dt, items_ordered, status_completed)

select 
	o.ord_no
	,v.HeaderCode
	,v.cus_no
	,o.ship_to_name
	,o.cus_alt_adr_cd
	,o.ord_dt
	,COUNT(l.item_no) as items_ordered
	,'N' --unprocessed
from oeordhdr_sql o 
inner join oeordlin_sql l
	on o.ord_no = l.ord_no
inner join vw_ParWorkflowParDeptMap v
	on v.cus_alt_adr_cd = o.cus_alt_adr_cd
where convert(datetime, o.ord_dt) = CONVERT(date, GETDATE()) --todays orders
and o.ord_no not in (select ord_no from jtblPar_WorkflowHeader) --exclude orders already inserted into workflow header
group by 
	o.ord_no
	,v.HeaderCode
	,v.cus_no
	,o.ship_to_name
	,o.cus_alt_adr_cd
	,o.ship_to_name
	,o.ord_dt

----remove 'old' orders that have not been processed making them invisible to the user
----to prevent overflow in the par workflow application
update jtblPar_WorkflowHeader
set status_completed = 'I' --incomplete
where ord_no in --subquery to filter for orders that have been left unprocessed
( 
	select distinct w.ord_no
	from jtblPar_WorkflowHeader w
	inner join 
	(
	--dense rank used to assign distinct number for orders using the same location ordered on the same day 
		select ord_no,		
		dense_rank() over(partition by cus_alt_adr_cd order by ord_dt desc) seq
		from jtblPar_WorkflowHeader i where status_completed = 'N'
	) as t
	--exclude all orders in department left unprocessed	
	on w.ord_no=t.ord_no
	where t.seq <> 1
)

GO