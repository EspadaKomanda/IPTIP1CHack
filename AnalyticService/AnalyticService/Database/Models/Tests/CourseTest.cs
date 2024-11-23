using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace AnalyticService.Database.Models
{
    public class CourseTest
    {
        [Key]
        public long Id { get; set; }
        public long CourseId { get; set; }
        public DateTime StartTime { get; set; }
        public DateTime EndTime { get; set; }
        public long TestId { get; set; }
        [ForeignKey("TestId")]
        public Test Test { get; set; } = null!;
        public int Attempts { get; set; }
        public TimeSpan Time { get; set; }
        public double MinScore { get; set; }
        public double MaxScore { get; set; }
    }
}