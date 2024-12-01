using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace AnalyticService.Models.Analytic.Requests.Attendance
{
    public class AttendancePercentWeekdayRequest
    {
        public int WeekDay { get; set; }
        public List<long> UserIds { get; set; }
    }
}