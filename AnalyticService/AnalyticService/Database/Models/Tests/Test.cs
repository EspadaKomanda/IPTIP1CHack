using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace AnalyticService.Database.Models
{
    public class Test
    {
        [Key]
        public long Id { get; set; }
        public int Attempts { get; set; } = 1;
        public string Name { get; set; } = null!;
        public TimeSpan Time { get; set; }
        public double MinScore { get; set; }
        public double MaxScore { get; set; }
    }
}