using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AnalyticService.Database.Models;
using AnalyticService.Models.Requests.CourseTestRequests;
using AnalyticService.Models.Requests.TestsRequests;

namespace AnalyticService.Services.CourseTests
{
    public interface ICourseService
    {
        public Task<bool> AddCourseTest(CourseTestRequest courseTestRequest);
        public Task<bool> UpdateCourseTest(CourseTestRequest courseTestRequest);
        public Task<bool> DeleteCourseTest(DeleteRequest deleteRequest);
        public Task<CourseTest> GetCourseTest(long id);
        public IQueryable<CourseTest> GetCourseTests();
    }
}