using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AnalyticService.Database.Models;
using AnalyticService.Models.Requests.TestsRequests;
using AnalyticService.Models.Requests.UserTestAttemptAnswerRequests;

namespace AnalyticService.Services.UserTestAttemptAnswers
{
    public interface IUserTestAttemptAnswerService
    {
        public Task<bool> AddUserTestAttemptAnswer(UserTestAttemptAnswerRequest userTestAttemptAnswerRequest);
        public Task<bool> UpdateUserTestAttemptAnswer(UserTestAttemptAnswerRequest userTestAttemptAnswerRequest);
        public Task<bool> DeleteUserTestAttemptAnswer(DeleteRequest deleteRequest);
        public Task<UserTestAttemptAnswer> GetUserTestAttemptAnswer(long id);
        public IQueryable<UserTestAttemptAnswer> GetUserTestAttemptAnswers();
    }
}