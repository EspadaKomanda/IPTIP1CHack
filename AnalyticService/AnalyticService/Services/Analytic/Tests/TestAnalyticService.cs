using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AnalyticService.Models.Analytic.Requests;
using AnalyticService.Models.Analytic.Responses;
using AnalyticService.Services.CourseTests;
using AnalyticService.Services.Tests;
using AnalyticService.Services.UserTestAttemptAnswers;
using AnalyticService.Services.UserTestAttempts;
using Microsoft.EntityFrameworkCore;

namespace AnalyticService.Services.Analytic.Tests
{
    public class TestAnalyticService(ITestService testService, IUserTestAttemptAnswerService userTestAttemptAnswerService, ICourseService courseService, IUserTestAttemptService userTestAttemptService, ILogger<TestAnalyticService> logger) : ITestAnalyticService
    {
        private readonly ITestService _testService = testService;
        private readonly ICourseService _courseService = courseService;
        private readonly IUserTestAttemptAnswerService _userTestAttemptAnswerService = userTestAttemptAnswerService;
        private readonly ILogger<TestAnalyticService> _logger = logger;
        private readonly IUserTestAttemptService _userTestAttemptService = userTestAttemptService;
        public ResultResponse GetAverageResult(TestAverageResultRequest request)
        {
            try
            {
                var tests = _userTestAttemptService.GetUserTestAttempts()
                    .Include(x=>x.CourseTest)
                    .Where(t => t.CourseTest.TestId == request.TestId)
                    .Where(t=>request.UserIds.Contains(t.UserId)).ToList();
                

                return new ResultResponse()
                {
                    Result = tests.Select(x=>x.Score).Average()
                };
                
                
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }

        public TriesResponse GetAverageTries(TestAverageTriesRequest request)
        {
            try
            {
                var tests = _userTestAttemptService.GetUserTestAttempts()
                    .Include(x=>x.CourseTest)
                    .Where(t => t.CourseTest.TestId == request.TestId)
                    .Where(t=>request.UserIds.Contains(t.UserId)).ToList();
                return new TriesResponse()
                {
                    Tries = tests.Count()/request.UserIds.Count()
                };
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }

        public PercentResponse GetCompletionProgress(TestCompletionProgressRequest request)
        {
            try
            {
                var tests = _userTestAttemptService.GetUserTestAttempts()
                    .Include(x=>x.CourseTest)
                    .Where(t => t.CourseTest.TestId == request.TestId)
                    .Where(t=>request.UserIds.Contains(t.UserId));
                int successCount = 0;
                foreach (var user in request.UserIds)
                {
                    if(tests.Where(t=>t.UserId == user).Any(t=>t.Passed))
                    {
                        successCount++;
                    }
                }
                return new PercentResponse() {
                    Percent = (double)successCount / request.UserIds.Count()
                    };
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }

        public TimeResponse GetCompletionTime(TestAveregeCompletionTimeRequest request)
        {
            try
            {
                var tests = _userTestAttemptService.GetUserTestAttempts()
                    .Include(x=>x.CourseTest)
                    .Where(t => t.CourseTest.TestId == request.TestId)
                    .Where(t=>request.UserIds.Contains(t.UserId));
                var times = new List<long>();
                foreach (var user in request.UserIds)
                {
                    var time = tests.Where(t=>t.UserId == user).OrderByDescending(t=>t.Date);
                    times.Add((time.First().Date-time.Last().Date).Ticks);  
                }
                return new TimeResponse()
                {
                    Time = (long)times.Average()
                };
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }

        public PercentResponse GetExpiredPercent(TestExpiredPercentRequest request)
        {
            try
            {
                var tests = _userTestAttemptService.GetUserTestAttempts()
                    .Include(x=>x.CourseTest)
                    .Where(t => t.CourseTest.TestId == request.TestId)
                    .Where(t=>request.UserIds.Contains(t.UserId));
                var time = _courseService.GetCourseTests().Where(t => t.TestId == request.TestId).First().EndTime;
                return new PercentResponse() {
                    Percent = (double)(tests.Count()-tests.Where(t=>t.Date<time && t.Passed).Count()) / request.UserIds.Count()
                    };
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }

        public PercentResponse GetSuccessAttemptsPercent(TestSuccessAttemptsPercentRequest request)
        {
            try
            {
                var tests = _userTestAttemptService.GetUserTestAttempts()
                    .Include(x=>x.CourseTest)
                    .Where(t => t.CourseTest.TestId == request.TestId)
                    .Where(t=>request.UserIds.Contains(t.UserId));
                return new PercentResponse() {
                    Percent = (double)tests.Where(t=>t.Passed).Count() / tests.Count()
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