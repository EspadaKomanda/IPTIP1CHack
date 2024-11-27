using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AnalyticService.Models.Analytic.Requests.Question;
using AnalyticService.Models.Analytic.Responses;

namespace AnalyticService.Services.Analytic.Questions
{
    public interface IQuestionsAnalyticService
    {
        PercentResponse GetCompletionPercent(QuestionCompletionPercentRequest request);
        ContentResponse GetMostPopularAnswer(QuestionMostPopularAnswerRequest request);
    }
}