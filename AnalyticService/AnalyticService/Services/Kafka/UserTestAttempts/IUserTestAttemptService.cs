using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AnalyticService.Database.Models;
using AnalyticService.Models.Requests.TestsRequests;
using AnalyticService.Models.Requests.UserTestAttemptRequests;

namespace AnalyticService.Services.UserTestAttempts
{
    public interface IUserTestAttemptService
    {
        public Task<bool> AddUserTestAttempt(UserTestAttemptRequest userTestAttemptRequest);
        public Task<bool> UpdateUserTestAttempt(UserTestAttemptRequest userTestAttemptRequest);
        public Task<bool> DeleteUserTestAttempt(DeleteRequest deleteRequest);
        public Task<UserTestAttempt> GetUserTestAttempt(long id);
        public IQueryable<UserTestAttempt> GetUserTestAttempts();
    }
}