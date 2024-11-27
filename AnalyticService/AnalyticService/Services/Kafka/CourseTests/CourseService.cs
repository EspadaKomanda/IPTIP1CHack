using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AnalyticService.Database;
using AnalyticService.Database.Models;
using AnalyticService.Models.Requests.CourseTestRequests;
using AnalyticService.Models.Requests.TestsRequests;
using KafkaAttributesLib.Attributes;
using Microsoft.EntityFrameworkCore;

namespace AnalyticService.Services.CourseTests
{
    [KafkaServiceName("CourseService")]
    public class CourseService(IApplicationContext context, ILogger<CourseService> logger) : ICourseService
    {
        private readonly IApplicationContext _context = context;
        private readonly ILogger<CourseService> _logger = logger;
        
        [KafkaMethod("addCourseTest")]
        public async Task<bool> AddCourseTest(CourseTestRequest request)
        {
            try
            {
                var courseTest = new CourseTest()
                {
                    Id = request.Id,
                    CourseId = request.CourseId,
                    TestId = request.TestId,
                    Attempts = request.Attempts,
                    EndTime = DateTime.FromBinary(request.EndTime),
                    MaxScore = request.MaxScore,
                    MinScore = request.MinScore,
                    StartTime = DateTime.FromBinary(request.StartTime),
                    Time = TimeSpan.FromMilliseconds(request.Time)
                };
                await _context.CourseTests.AddAsync(courseTest);
                return await _context.SaveChangesAsync() >= 0;
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }

        [KafkaMethod("updateCourseTest")]
        public async Task<bool> UpdateCourseTest(CourseTestRequest request)
        {
            try
            {
                var courseTest = new CourseTest()
                {
                    Id = request.Id,
                    CourseId = request.CourseId,
                    TestId = request.TestId,
                    Attempts = request.Attempts,
                    EndTime = DateTime.FromBinary(request.EndTime),
                    MaxScore = request.MaxScore,
                    MinScore = request.MinScore,
                    StartTime = DateTime.FromBinary(request.StartTime),
                    Time = TimeSpan.FromMilliseconds(request.Time)
                };
                
                return await _context.SaveChangesAsync() >= 0;
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }

        [KafkaMethod("deleteCourseTest")]
        public async Task<bool> DeleteCourseTest(DeleteRequest deleteRequest)
        {
            try
            {
                var courseTest = await _context.CourseTests.FirstOrDefaultAsync(t => t.Id == deleteRequest.Id);
                if (courseTest == null)
                {
                    return false;
                }
                _context.CourseTests.Remove(courseTest);
                return await _context.SaveChangesAsync() >= 0;
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }

        public async Task<CourseTest> GetCourseTest(long id)
        {
            try
            {
                return await _context.CourseTests.FirstOrDefaultAsync(t => t.Id == id);
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }

        public IQueryable<CourseTest> GetCourseTests()
        {
            try
            {
                return _context.CourseTests.AsQueryable();
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }
    }
}