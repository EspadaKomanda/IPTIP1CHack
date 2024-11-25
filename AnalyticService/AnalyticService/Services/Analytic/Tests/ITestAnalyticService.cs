using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AnalyticService.Models.Analytic.Requests;
using AnalyticService.Models.Analytic.Responses;

namespace AnalyticService.Services.Analytic.Tests
{
    public interface ITestAnalyticService
    {
        PercentResponse GetCompletionProgress(TestCompletionProgressRequest request);
        TimeResponse GetCompletionTime(TestAveregeCompletionTimeRequest request);
        PercentResponse GetExpiredPercent(TestExpiredPercentRequest request);
        ResultResponse GetAverageResult(TestAverageResultRequest request);
        TriesResponse GetAverageTries(TestAverageTriesRequest request);
        PercentResponse GetSuccessAttemptsPercent(TestSuccessAttemptsPercentRequest request);
    }
}