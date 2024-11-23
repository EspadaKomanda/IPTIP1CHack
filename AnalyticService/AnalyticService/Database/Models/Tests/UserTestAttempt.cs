using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace AnalyticService.Database.Models
{
    public class UserTestAttempt
    {
        [Key]
        public long Id { get; set; }
        public long UserId { get; set; }
        public int AttemptNumber { get; set; }
        public double Score { get; set; }
        public DateTime Date { get; set; }
        public bool Passed { get; set; }
        public long CourseTestId { get; set; }
        [ForeignKey("CourseTestId")]
        public CourseTest CourseTest { get; set; } = null!;
    }
}