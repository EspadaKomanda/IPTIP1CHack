using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace AnalyticService.Database.Models
{
    public class UserTestAttemptAnswer
    {
        [Key]
        public long Id { get; set; }
        public long UserTestAttemptId { get; set; }
        [ForeignKey("UserTestAttemptId")]
        public UserTestAttempt UserTestAttempt { get; set; } = null!;
        public long QuestionId { get; set; }
        public string Content { get; set; } = "";
        public bool IsCorrect { get; set; }
    }
}