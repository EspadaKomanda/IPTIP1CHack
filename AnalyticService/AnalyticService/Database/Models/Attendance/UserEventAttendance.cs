using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace AnalyticService.Database.Models.Attendance
{
    public class UserEventAttendance
    {
        [Key]
        public long Id { get; set; }
        public long UserId { get; set; }
        public long EventId { get; set; }
        [ForeignKey("EventId")]
        public Event Event { get; set; } = null!;
        public bool Status { get; set; }
    }
}