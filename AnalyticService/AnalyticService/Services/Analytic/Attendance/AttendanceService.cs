using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AnalyticService.Database.Models.Attendance;
using AnalyticService.Models.Analytic.Requests.Attendance;
using AnalyticService.Models.Analytic.Responses;
using AnalyticService.Models.Requests.TestsRequests;
using AnalyticService.Models.Requests.UserEventAttendanceRequests;
using AnalyticService.Services.Events;
using AnalyticService.Services.UserEventAttendanses;
using Microsoft.EntityFrameworkCore;

namespace AnalyticService.Services.Analytic.Attendance
{
    public class AttendanceService(IEventService eventService, IUserEventAttendanceService userEventAttendanceService, ILogger<AttendanceService> logger) : IAttendanceService
    {
        private readonly IEventService _eventService = eventService;
        private readonly IUserEventAttendanceService _userEventAttendanceService = userEventAttendanceService;
        private readonly ILogger<AttendanceService> _logger = logger;

        public PercentResponse GetPercentTime(AttendancePercentTimeRequest request)
        {
            try
            {
                var time = _userEventAttendanceService.GetUserEventAttendances().Include(x=>x.Event)
                    .Where(t => request.UserIds.Contains(t.UserId))
                    .Where(t=>t.Event.Date>DateTime.FromBinary(request.StartTime) && t.Event.Date<DateTime.FromBinary(request.EndTime)
                    && t.Event.BeginDate>DateTime.FromBinary(request.StartDate) && t.Event.BeginDate<DateTime.FromBinary(request.EndDate));
                return new PercentResponse() {
                    Percent =- (double)(time.Where(x=>x.Status).Count() / time.Count())
                };
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }
        public PercentResponse GetPercentSubject(AttendancePecentSubjectRequest request)
        {
            try
            {
                var time = _userEventAttendanceService.GetUserEventAttendances().Include(x=>x.Event)
                    .Where(t => request.UserIds.Contains(t.UserId))
                    .Where(t=>t.EventId==request.EventId);
                return new PercentResponse() {
                    Percent = (double)(time.Where(x=>x.Status).Count() / time.Count())
                };
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }
        public PercentResponse GetPercentWeekday(AttendancePercentWeekdayRequest request)
        {
            try
            {
                var time = _userEventAttendanceService.GetUserEventAttendances().Include(x=>x.Event)
                    .Where(t => request.UserIds.Contains(t.UserId))
                    .Where(t=>t.Event.DayOfWeek==GetDayOfWeek(request.WeekDay));
                return new PercentResponse() {
                    Percent = (double)(time.Where(x=>x.Status).Count() / time.Count())
                };
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }
        private DayOfWeek GetDayOfWeek(int day)
        {
            if(day == 1)
            {
                return DayOfWeek.Monday;
            }
            else if(day == 2)
            {
                return DayOfWeek.Tuesday;
            }
            else if(day == 3)
            {
                return DayOfWeek.Wednesday;
            }
            else if(day == 4)
            {
                return DayOfWeek.Thursday;
            }
            else if(day == 5)
            {
                return DayOfWeek.Friday;
            }
            else if(day == 6)
            {
                return DayOfWeek.Saturday;
            }
            else if(day == 7)
            {
                return DayOfWeek.Sunday;
            }
            return DayOfWeek.Monday;
        }

    }
}