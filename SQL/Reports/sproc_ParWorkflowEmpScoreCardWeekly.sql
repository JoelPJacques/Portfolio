-- =============================================
-- Author:		Joel Jacques
-- Create date: <2018-05-24 07:49:46.433>
-- Description:	return result set used to generate item processed totals by week
-- for local report [rptEmpScorecard.rdlc] in Par_WorkflowApp.exe
-- =============================================
CREATE procedure [dbo].[sproc_ParWorkflowEmpScoreCardWeekly] (@empid as varchar(20), @reportstartdate as date)
AS

set nocount on;
declare @reportenddate as date 
set @reportenddate = dateadd(week, 1, @reportstartdate);
print @reportstartdate;

--generate table with dates and day of the week
;with CteDayOfTheWeek
as
(
select top (1+datediff(day, @reportstartdate, @reportenddate))
	 DateValue = dateadd(day, v.number, @reportstartdate)
	,DayOfTheWeek = datename(DW, convert(date, dateadd(day, v.number, @reportstartdate))) 
from master.dbo.spt_values v
where v.type = 'P'
	and v.number >= 0
),

CteEmpData
as
(
	select DateValue = convert(date, add_datetime_start) 
		 ,DayOfTheWeek = datename(DW, convert(date, add_datetime_start))
		 ,task_indicator, Sum(items_processed) as [items_processed]
	from vw_ParWorkflow w  
	inner join jtblPar_Employee e on w.empid = e.EmpID
	where convert(date, add_datetime_start) between @reportstartdate and @reportenddate
		and e.empid = @empid and status_completed in ('Y', 'P')
	group by convert(date, add_datetime_start), e.EmpFName, e.EmpLName, task_indicator
)


select EmpFName = (select EmpFName from jtblPar_Employee where EmpID = @empid),
EmpLName = (select EmpLName from jtblPar_Employee where EmpID = @empid), 
case when e.task_indicator IS null then '<NO VALUE>' else upper(e.task_indicator) end as [task_indicator]
, c.DateValue, c.DayOfTheWeek
,case when e.items_processed is not null then e.items_processed else 0 end as [items_processed] 
from CteDayOfTheWeek c left join CteEmpData e on c.DayOfTheWeek = e.DayOfTheWeek
order by c.DateValue
GO