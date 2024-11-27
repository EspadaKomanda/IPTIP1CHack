using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AnalyticService.Models.Analytic.Requests.Attendance;
using AnalyticService.Models.Analytic.Responses;

namespace AnalyticService.Services.Analytic.Attendance
{
    public interface IAttendanceService
    {
        PercentResponse GetPercentTime(AttendancePercentTimeRequest request);
        PercentResponse GetPercentSubject(AttendancePecentSubjectRequest request);
        PercentResponse GetPercentWeekday(AttendancePercentWeekdayRequest request);
    }
}