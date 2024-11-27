using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace AnalyticService.Models.Analytic.Requests.Attendance
{
    public class AttendancePercentTimeRequest
    {
        public long StartTime { get; set; }
        public long EndTime { get; set; }
        public long StartDate { get; set; }
        public long EndDate { get; set; }
        public List<long> UserIds { get; set; } 
    }
}