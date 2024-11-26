using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AnalyticGrpc;
using AnalyticService.Models.Analytic.Requests;
using AnalyticService.Models.Analytic.Requests.Attendance;
using AnalyticService.Models.Analytic.Requests.Question;
using AnalyticService.Models.Analytic.Responses;
using AnalyticService.Services.Analytic.Attendance;
using AnalyticService.Services.Analytic.Questions;
using AnalyticService.Services.Analytic.Tests;
using Grpc.Core;
using Newtonsoft.Json;

namespace AnalyticService.Services.GRPC
{
    public class AnalyticGRPCService(ILogger<AnalyticGRPCService> logger, IAttendanceService attendanceService, IQuestionsAnalyticService questionsAnalyticService, ITestAnalyticService testAnalyticService) : AnalyticGrpc.Analytic.AnalyticBase
    {
        private readonly ILogger<AnalyticGRPCService> _logger = logger;
        private readonly IAttendanceService _attendanceService = attendanceService;
        private readonly IQuestionsAnalyticService _questionsAnalyticService = questionsAnalyticService;
        private readonly ITestAnalyticService _testAnalyticService = testAnalyticService;
        public override Task<Response> GetAttendanceTimePercent(Request request, ServerCallContext context)
        {
            try
            {
                return Task.FromResult(new Response {
                        Content = JsonConvert.SerializeObject(_attendanceService.GetPercentTime(
                            JsonConvert.DeserializeObject<AttendancePercentTimeRequest>(request.Content)
                        )
                    )
                });
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, ex.Message);
                return Task.FromResult(new Response { Content = ex.Message });
            }
        }
        public override Task<Response> GetAttendancePercentSubject(Request request, ServerCallContext context)
        {
            try
            {
                return Task.FromResult(new Response {
                        Content = JsonConvert.SerializeObject(_attendanceService.GetPercentSubject(
                            JsonConvert.DeserializeObject<AttendancePecentSubjectRequest>(request.Content)
                        )
                    )
                });
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, ex.Message);
                return Task.FromResult(new Response { Content = ex.Message });
            }
        }
        public override Task<Response> GetAttendancePercentWeekday(Request request, ServerCallContext context)
        {
            try
            {
                return Task.FromResult(new Response {
                        Content = JsonConvert.SerializeObject(_attendanceService.GetPercentWeekday(
                            JsonConvert.DeserializeObject<AttendancePercentWeekdayRequest>(request.Content))
                        )
                    }
                );
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, ex.Message);
                return Task.FromResult(new Response { Content = ex.Message });
            }
        }
        public override Task<Response> GetQuestionMostPopularAnswer(Request request, ServerCallContext context)
        {
            try
            {
                return Task.FromResult(new Response {
                        Content = JsonConvert.SerializeObject(_questionsAnalyticService.GetMostPopularAnswer(
                            JsonConvert.DeserializeObject<QuestionMostPopularAnswerRequest>(request.Content))
                        )
                    }
                );
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, ex.Message);
                return Task.FromResult(new Response { Content = ex.Message });
            }
        }
        public override Task<Response> GetQuestionsCompletionPercent(Request request, ServerCallContext context)
        {
            try
            {
                return Task.FromResult(new Response {
                        Content = JsonConvert.SerializeObject(_questionsAnalyticService.GetCompletionPercent(
                            JsonConvert.DeserializeObject<QuestionCompletionPercentRequest>(request.Content))
                        )
                    }
                );
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, ex.Message);
                return Task.FromResult(new Response { Content = ex.Message });
            }
        }
        public override Task<Response> GetTestAverageCompletionTime(Request request, ServerCallContext context)
        {
            try
            {
                return Task.FromResult(new Response {
                        Content = JsonConvert.SerializeObject(_testAnalyticService.GetCompletionTime(
                            JsonConvert.DeserializeObject<TestAveregeCompletionTimeRequest>(request.Content))
                        )
                    }
                );
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, ex.Message);
                return Task.FromResult(new Response { Content = ex.Message });
            }
        }
        public override Task<Response> GetTestAverageResult(Request request, ServerCallContext context)
        {
            try
            {
                return Task.FromResult(new Response {
                        Content = JsonConvert.SerializeObject(_testAnalyticService.GetAverageResult(
                            JsonConvert.DeserializeObject<TestAverageResultRequest>(request.Content))
                        )
                    }
                );
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, ex.Message);
                return Task.FromResult(new Response { Content = ex.Message });
            }
        }
        public override Task<Response> GetTestAverageTries(Request request, ServerCallContext context)
        {
            try
            {
                return Task.FromResult(new Response {
                        Content = JsonConvert.SerializeObject(_testAnalyticService.GetAverageTries(
                            JsonConvert.DeserializeObject<TestAverageTriesRequest>(request.Content))
                        )
                    }
                );
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, ex.Message);
                return Task.FromResult(new Response { Content = ex.Message });
            }
        }
        public override Task<Response> GetTestCompletionProgressPercent(Request request, ServerCallContext context)
        {
            try
            {
                return Task.FromResult(new Response {
                        Content = JsonConvert.SerializeObject(_testAnalyticService.GetCompletionProgress(
                            JsonConvert.DeserializeObject<TestCompletionProgressRequest>(request.Content))
                        )
                    }
                );
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, ex.Message);
                return Task.FromResult(new Response { Content = ex.Message });
            }
        }
        public override Task<Response> GetTestExpiredPercent(Request request, ServerCallContext context)
        {
            try
            {
                return Task.FromResult(new Response {
                        Content = JsonConvert.SerializeObject(_testAnalyticService.GetExpiredPercent(
                            JsonConvert.DeserializeObject<TestExpiredPercentRequest>(request.Content))
                        )
                    }
                );
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, ex.Message);
                return Task.FromResult(new Response { Content = ex.Message });
            }
        }
        public override Task<Response> GetTestSuccessAttemptsPercent(Request request, ServerCallContext context)
        {
            try
            {
                return Task.FromResult(new Response {
                        Content = JsonConvert.SerializeObject(_testAnalyticService.GetSuccessAttemptsPercent(
                            JsonConvert.DeserializeObject<TestSuccessAttemptsPercentRequest>(request.Content))
                        )
                    }
                );
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, ex.Message);
                return Task.FromResult(new Response { Content = ex.Message });
            }
        }
    }
}