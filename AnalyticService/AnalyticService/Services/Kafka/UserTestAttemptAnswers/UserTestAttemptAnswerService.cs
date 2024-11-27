using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AnalyticService.Database;
using AnalyticService.Database.Models;
using AnalyticService.Models.Requests.TestsRequests;
using AnalyticService.Models.Requests.UserTestAttemptAnswerRequests;
using KafkaAttributesLib.Attributes;
using Microsoft.EntityFrameworkCore;

namespace AnalyticService.Services.UserTestAttemptAnswers
{
    [KafkaServiceName("UserTestAttemptAnswerService")]
    public class UserTestAttemptAnswerService(IApplicationContext context, ILogger<UserTestAttemptAnswerService> logger) : IUserTestAttemptAnswerService
    {
        private readonly IApplicationContext _context = context;
        private readonly ILogger<UserTestAttemptAnswerService> _logger = logger;

        [KafkaMethod("addUserTestAttemptAnswer")]
        public async Task<bool> AddUserTestAttemptAnswer(UserTestAttemptAnswerRequest userTestAttemptAnswerRequest)
        {
            try
            {
                var userTestAttemptAnswer = new UserTestAttemptAnswer
                {
                    Id = userTestAttemptAnswerRequest.Id,
                    UserTestAttemptId = userTestAttemptAnswerRequest.UserTestAttemptId,
                    QuestionId = userTestAttemptAnswerRequest.QuestionId,
                    Content = userTestAttemptAnswerRequest.Content,
                    IsCorrect = userTestAttemptAnswerRequest.IsCorrect,
                    Status = userTestAttemptAnswerRequest.Status,
                    StatusModifiedAt = DateTime.FromBinary(userTestAttemptAnswerRequest.StatusModifiedAt),
                    
                };
                _context.UserTestAttemptAnswers.Add(userTestAttemptAnswer);
                return await _context.SaveChangesAsync() >= 0;
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }

        [KafkaMethod("deleteUserTestAttemptAnswer")]
        public async Task<bool> DeleteUserTestAttemptAnswer(DeleteRequest deleteRequest)
        {
            try
            {
                var userTestAttemptAnswer = await _context.UserTestAttemptAnswers.FirstOrDefaultAsync(t => t.Id == deleteRequest.Id);
                if (userTestAttemptAnswer == null)
                {
                    return false;
                }
                _context.UserTestAttemptAnswers.Remove(userTestAttemptAnswer);
                return await _context.SaveChangesAsync() >= 0;
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }

        public async Task<UserTestAttemptAnswer> GetUserTestAttemptAnswer(long id)
        {
            try
            {
                return await _context.UserTestAttemptAnswers.FirstOrDefaultAsync(t => t.Id == id);
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }

        public IQueryable<UserTestAttemptAnswer> GetUserTestAttemptAnswers()
        {
            try
            {
                return _context.UserTestAttemptAnswers.AsQueryable();
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }

        [KafkaMethod("updateUserTestAttemptAnswer")]
        public async Task<bool> UpdateUserTestAttemptAnswer(UserTestAttemptAnswerRequest userTestAttemptAnswerRequest)
        {
            try
            {
                var userTestAttemptAnswer = new UserTestAttemptAnswer
                {
                    Id = userTestAttemptAnswerRequest.Id,
                    UserTestAttemptId = userTestAttemptAnswerRequest.UserTestAttemptId,
                    QuestionId = userTestAttemptAnswerRequest.QuestionId,
                    Content = userTestAttemptAnswerRequest.Content,
                    IsCorrect = userTestAttemptAnswerRequest.IsCorrect,
                    Status = userTestAttemptAnswerRequest.Status,
                    StatusModifiedAt = DateTime.FromBinary(userTestAttemptAnswerRequest.StatusModifiedAt),
                };
                _context.UserTestAttemptAnswers.Update(userTestAttemptAnswer);
                return await _context.SaveChangesAsync() >= 0;
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }
    }
}