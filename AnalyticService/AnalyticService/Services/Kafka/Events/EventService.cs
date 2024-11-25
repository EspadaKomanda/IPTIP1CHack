using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AnalyticService.Database;
using AnalyticService.Database.Models.Attendance;
using AnalyticService.Models.Requests.EventRequests;
using KafkaAttributesLib.Attributes;
using Microsoft.EntityFrameworkCore;

namespace AnalyticService.Services.Events
{
    [KafkaServiceName("EventService")]
    public class EventService(IApplicationContext context, ILogger<EventService> logger) : IEventService
    {
        private readonly IApplicationContext _context = context;
        private readonly ILogger<EventService> _logger = logger;

        [KafkaMethod("addEvent")]
        public async Task<bool> AddEvent(EventRequest eventRequest)
        {
            try
            {
                
                var eventModel = new Event()
                {
                    BeginDate = DateTime.FromBinary(eventRequest.BeginDate),
                    Date = DateTime.FromBinary(eventRequest.Date),
                    EndDate = DateTime.FromBinary(eventRequest.EndDate),
                    Name = eventRequest.Name,
                    DayOfWeek = GetDayOfWeek(eventRequest.Weekday),
                    Duration = TimeSpan.FromMilliseconds(eventRequest.Duration),
                    Id = eventRequest.Id,
                    IsWeekEven = eventRequest.IsWeekEven,
                    StudyGroupId = eventRequest.StudyGroupId
                };
                _context.Events.Add(eventModel);
                return await _context.SaveChangesAsync() >= 0;
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }
        public async Task<bool> UpdateEvent(EventRequest eventRequest)
        {
            try
            {
                var eventModel = new Event()
                {
                    BeginDate = DateTime.FromBinary(eventRequest.BeginDate),
                    Date = DateTime.FromBinary(eventRequest.Date),
                    EndDate = DateTime.FromBinary(eventRequest.EndDate),
                    Name = eventRequest.Name,
                    DayOfWeek = GetDayOfWeek(eventRequest.Weekday),
                    Duration = TimeSpan.FromMilliseconds(eventRequest.Duration),
                    Id = eventRequest.Id,
                    IsWeekEven = eventRequest.IsWeekEven,
                    StudyGroupId = eventRequest.StudyGroupId,
                };
                _context.Events.Update(eventModel);
                return await _context.SaveChangesAsync() >= 0;
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
                
        }
        public async Task<bool> DeleteEvent(EventRequest eventRequest)
        {
            try
            {
                var eventModel = await _context.Events.FirstOrDefaultAsync(t => t.Id == eventRequest.Id);
                if (eventModel == null)
                {
                    return false;
                }
                _context.Events.Remove(eventModel);
                return await _context.SaveChangesAsync() >= 0;
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }
        public async Task<Event> GetEvent(long id)
        {
            try
            {
                return await _context.Events.FirstOrDefaultAsync(t => t.Id == id);
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }
        public IQueryable<Event> GetEvents()
        {
            try
            {
                return _context.Events.AsQueryable();
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