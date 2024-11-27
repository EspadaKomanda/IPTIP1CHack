using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AnalyticService.Database.Models.Attendance;
using AnalyticService.Models.Requests.TestsRequests;
using AnalyticService.Models.Requests.UserEventAttendanceRequests;

namespace AnalyticService.Services.UserEventAttendanses
{
    public interface IUserEventAttendanceService
    {
        public Task<bool> AddUserEventAttendance(UserEventAttendanceRequest userEventAttendanceRequest);
        public Task<bool> UpdateUserEventAttendance(UserEventAttendanceRequest userEventAttendanceRequest);
        public Task<bool> DeleteUserEventAttendance(DeleteRequest deleteRequest);
        public Task<UserEventAttendance> GetUserEventAttendance(long id);
        public IQueryable<UserEventAttendance> GetUserEventAttendances();
    }
}