using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AnalyticService.Models.Analytic.Requests.Question;
using AnalyticService.Models.Analytic.Responses;
using AnalyticService.Services.CourseTests;
using AnalyticService.Services.Questions;
using AnalyticService.Services.Tests;
using AnalyticService.Services.UserTestAttemptAnswers;
using AnalyticService.Services.UserTestAttempts;
using Microsoft.EntityFrameworkCore;

namespace AnalyticService.Services.Analytic.Questions
{
    public class QuestionsAnalyticService(ITestService testService, IUserTestAttemptAnswerService userTestAttemptAnswerService, ICourseService courseService, IUserTestAttemptService userTestAttemptService, ILogger<QuestionsAnalyticService> logger) : IQuestionsAnalyticService
    {
        private readonly ITestService _testService = testService;
        private readonly IUserTestAttemptAnswerService _userTestAttemptAnswerService = userTestAttemptAnswerService;
        private readonly ICourseService _courseService = courseService;
        private readonly IUserTestAttemptService _userTestAttemptService = userTestAttemptService;
        private readonly ILogger<QuestionsAnalyticService> _logger = logger;

        public PercentResponse GetCompletionPercent(QuestionCompletionPercentRequest request)
        {
            try
            {
                
                var tests = _userTestAttemptAnswerService.GetUserTestAttemptAnswers()
                    .Include(x=>x.UserTestAttempt);
                return new PercentResponse() {
                    Percent = (double)(tests.Where(t=>t.IsCorrect).Count() / tests.Count())
                };
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }
        public ContentResponse GetMostPopularAnswer(QuestionMostPopularAnswerRequest request)
        {
            try
            {
                var answers = _userTestAttemptAnswerService.GetUserTestAttemptAnswers()
                    .Include(x => x.UserTestAttempt);
                return new ContentResponse()
                {
                    Content = answers.GroupBy(x => x.Content).OrderByDescending(x => x.Count()).First().Key
                };
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }
    }
}