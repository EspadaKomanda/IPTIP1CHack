using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AnalyticService.Database;
using AnalyticService.Database.Models;
using AnalyticService.Models.Requests.TestsRequests;
using KafkaAttributesLib.Attributes;
using Microsoft.EntityFrameworkCore;

namespace AnalyticService.Services.Tests
{
    [KafkaServiceName("TestService")]
    public class TestService(IApplicationContext context, ILogger<TestService> logger) : ITestService
    {
        private readonly IApplicationContext _context = context;
        private readonly ILogger<TestService> _logger = logger;

        [KafkaMethod("addTest")]
        public async Task<bool> AddTest(TestRequest testRequest)
        {
            try
            {
                var test = new Test()
                {
                    Id = testRequest.Id,
                    Name = testRequest.Name,
                    Time = TimeSpan.FromMilliseconds(testRequest.Time),
                    Attempts = testRequest.Attempts,
                    MaxScore = testRequest.MaxScore,
                    MinScore = testRequest.MinScore,
                };

                await _context.Tests.AddAsync(test);
               
                return  await _context.SaveChangesAsync() >=0;
            }
            catch(Exception e)
            {
                _logger.LogError(e,"Unhandled error");
                throw;
            }
        }

        [KafkaMethod("updateTest")]
        public async Task<bool> UpdateTest(TestRequest testRequest)
        {
            try
            {
                var test = new Test()
                {
                    Id = testRequest.Id,
                    Name = testRequest.Name,
                    Time = TimeSpan.FromMilliseconds(testRequest.Time),
                    Attempts = testRequest.Attempts,
                    MaxScore = testRequest.MaxScore,
                    MinScore = testRequest.MinScore,
                };

                _context.Tests.Update(test);

                return await _context.SaveChangesAsync() >= 0;
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }

        [KafkaMethod("deleteTest")]
        public async Task<bool> DeleteTest(DeleteRequest deleteRequest)
        {
            try
            {
              

                _context.Tests.Remove(await _context.Tests.FirstOrDefaultAsync(t => t.Id == deleteRequest.Id));

                return await _context.SaveChangesAsync() >= 0;
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }

        public async Task<Test> GetTest(long id)
        {
            try
            {
                return await _context.Tests.FirstOrDefaultAsync(t => t.Id == id);
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }

        public IQueryable<Test> GetTests()
        {
            try
            {
                return _context.Tests.AsQueryable();
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }
    }
}