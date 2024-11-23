using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace AnalyticService.Database.Models.Attendance
{
    public class Event
    {
        [Key]
        public long Id { get; set; }
        public long StudyGroupId { get; set; }
        public string Name { get; set; } = "";
        public DateTime Date { get; set; }
        public DayOfWeek DayOfWeek { get; set; }
        public bool IsWeekEven { get; set; }
        public DateTime BeginDate { get; set; }
        public DateTime EndDate { get; set; }
    }
}