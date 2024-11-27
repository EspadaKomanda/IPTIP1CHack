using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace AnalyticService.Models.Analytic.Requests.Question
{
    public class QuestionMostPopularAnswerRequest
    {
        public long QuestionId { get; set; }
        public List<long> UserIds { get; set; }
    }
}