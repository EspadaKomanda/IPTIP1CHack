using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AnalyticService.Database.Models;
using AnalyticService.Models.Requests.TestsRequests;

namespace AnalyticService.Services.Tests
{
    public interface ITestService
    {
        public Task<bool> AddTest(TestRequest testRequest);
        public Task<bool> UpdateTest(TestRequest testRequest);
        public Task<bool> DeleteTest(DeleteRequest deleteRequest);
        public Task<Test> GetTest(long id);
        public IQueryable<Test> GetTests();
    }
}