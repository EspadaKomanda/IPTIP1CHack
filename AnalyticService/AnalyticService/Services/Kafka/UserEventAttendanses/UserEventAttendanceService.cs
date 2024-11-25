using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AnalyticService.Database;
using AnalyticService.Database.Models.Attendance;
using AnalyticService.Models.Requests.TestsRequests;
using AnalyticService.Models.Requests.UserEventAttendanceRequests;
using KafkaAttributesLib.Attributes;
using Microsoft.EntityFrameworkCore;

namespace AnalyticService.Services.UserEventAttendanses
{
    [KafkaServiceName("UserEventAttendanceService")]
    public class UserEventAttendanceService(IApplicationContext context, ILogger<UserEventAttendanceService> logger) : IUserEventAttendanceService
    {
        private readonly IApplicationContext _context = context;
        private readonly ILogger<UserEventAttendanceService> _logger = logger;

        [KafkaMethod("addUserEventAttendance")]
        public async Task<bool> AddUserEventAttendance(UserEventAttendanceRequest userEventAttendanceRequest)
        {
            try
            {
                var userEventAttendance = new UserEventAttendance()
                {
                    UserId = userEventAttendanceRequest.UserId,
                    EventId = userEventAttendanceRequest.EventId,
                    Id = userEventAttendanceRequest.Id,
                    Status = userEventAttendanceRequest.Status
                };
                _context.UserEventAttendances.Add(userEventAttendance);
                return await _context.SaveChangesAsync() >= 0;
            }
            catch (Exception e)
            {
                _logger.LogError(e.Message);
                throw;
            }
        }
        [KafkaMethod("updateUserEventAttendance")]
        public async Task<bool> UpdateUserEventAttendance(UserEventAttendanceRequest userEventAttendanceRequest)
        {
            try
            {
                var userEventAttendance = new UserEventAttendance(){

                    EventId = userEventAttendanceRequest.EventId,
                    UserId = userEventAttendanceRequest.UserId,
                    Id = userEventAttendanceRequest.Id,
                    Status = userEventAttendanceRequest.Status
                };
                _context.UserEventAttendances.Update(userEventAttendance);
                return await _context.SaveChangesAsync() >= 0;
            }
            catch (Exception e)
            {
                _logger.LogError(e.Message);
                throw;
            }
        }
        [KafkaMethod("deleteUserEventAttendance")]
        public async Task<bool> DeleteUserEventAttendance(DeleteRequest deleteRequest)
        {
            try
            {
                var userEventAttendance = await _context.UserEventAttendances.FirstOrDefaultAsync(t => t.Id == deleteRequest.Id);
                if (userEventAttendance == null)
                {
                    return false;
                }
                _context.UserEventAttendances.Remove(userEventAttendance);
                return await _context.SaveChangesAsync() >= 0;
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }
        public async Task<UserEventAttendance> GetUserEventAttendance(long id)
        {
            try
            {
                return await _context.UserEventAttendances.FirstOrDefaultAsync(t => t.Id == id);
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }
        public IQueryable<UserEventAttendance> GetUserEventAttendance()
        {
            try
            {
                return _context.UserEventAttendances;
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }
    }
}