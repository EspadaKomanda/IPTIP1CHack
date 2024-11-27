using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace AnalyticService.Database.Models
{
    public class Question
    {
        [Key]
        public long Id { get; set; }
        public long TestId { get; set; }

        [ForeignKey("TestId")]
        public Test Test{ get; set; } = null!;
        public string Title { get; set; } = "";
        public string Content { get; set; } = "";
        public QuestionType QuestionType { get; set; }
    }
    public enum QuestionType
    {
        TEXT,
        CHECKBOX,
        CHECKBOXES,
        CODE,
        URL,
        FILE
    }
}