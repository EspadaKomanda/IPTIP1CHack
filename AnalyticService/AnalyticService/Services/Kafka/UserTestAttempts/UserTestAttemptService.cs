using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AnalyticService.Database;
using AnalyticService.Database.Models;
using AnalyticService.Models.Requests.TestsRequests;
using AnalyticService.Models.Requests.UserTestAttemptRequests;
using AnalyticService.Services.Tests;
using KafkaAttributesLib.Attributes;
using Microsoft.EntityFrameworkCore;

namespace AnalyticService.Services.UserTestAttempts
{
    [KafkaServiceName("UserTestAttemptService")]
    public class UserTestAttemptService(IApplicationContext context, ILogger<TestService> logger) : IUserTestAttemptService
    {
        private readonly IApplicationContext _context = context;
        private readonly ILogger<TestService> _logger = logger;

        [KafkaMethod("addUserTestAttempt")]
        public async Task<bool> AddUserTestAttempt(UserTestAttemptRequest userTestAttemptRequest)
        {
            try
            {
                var userTestAttempt = new UserTestAttempt()
                {
                    Id = userTestAttemptRequest.Id,
                    UserId = userTestAttemptRequest.UserId,
                    AttemptNumber = userTestAttemptRequest.AttemptNumber,
                    Score = userTestAttemptRequest.Score,
                    CourseTestId = userTestAttemptRequest.CourseTestId,
                    Date = DateTime.FromBinary(userTestAttemptRequest.Date),
                    Passed = userTestAttemptRequest.Passed,
                };

                await _context.UserTestAttempts.AddAsync(userTestAttempt);
                return await _context.SaveChangesAsync()>=0;
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }
        [KafkaMethod("deleteUserTestAttempt")]
        public async Task<bool> DeleteUserTestAttempt(DeleteRequest deleteRequest)
        {
            try
            {
                var userTestAttempt = await _context.UserTestAttempts.FirstOrDefaultAsync(t => t.Id == deleteRequest.Id);
                if (userTestAttempt == null)
                {
                    return false;
                }
                _context.UserTestAttempts.Remove(userTestAttempt);
                return await _context.SaveChangesAsync() >= 0;
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }

        public async Task<UserTestAttempt> GetUserTestAttempt(long id)
        {
            try
            {
                return await _context.UserTestAttempts.FirstOrDefaultAsync(t => t.Id == id);
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }

        public IQueryable<UserTestAttempt> GetUserTestAttempts()
        {
            try
            {
                return _context.UserTestAttempts.AsQueryable();
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }
        [KafkaMethod("updateUserTestAttempt")]
        public async Task<bool> UpdateUserTestAttempt(UserTestAttemptRequest userTestAttemptRequest)
        {
            try
            {
                var userTestAttempt = new UserTestAttempt()
                {
                    Id = userTestAttemptRequest.Id,
                    UserId = userTestAttemptRequest.UserId,
                    AttemptNumber = userTestAttemptRequest.AttemptNumber,
                    Score = userTestAttemptRequest.Score,
                    CourseTestId = userTestAttemptRequest.CourseTestId,
                    Date = DateTime.FromBinary(userTestAttemptRequest.Date),
                    Passed = userTestAttemptRequest.Passed,
                };
                _context.UserTestAttempts.Update(userTestAttempt);
                return await _context.SaveChangesAsync() >=0 ;
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }
    }
}