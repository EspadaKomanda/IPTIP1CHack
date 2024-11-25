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
        public Status Status { get; set; }
        public DateTime StatusModifiedAt { get; set; }
    }
    public enum Status 
    {
        ON_VERIFICATION,
        CORRECT,
        WRONG
    }
}