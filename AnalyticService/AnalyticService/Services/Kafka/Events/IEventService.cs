using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AnalyticService.Database.Models.Attendance;
using AnalyticService.Models.Requests.EventRequests;

namespace AnalyticService.Services.Events
{
    public interface IEventService
    {
        public Task<bool> AddEvent(EventRequest eventRequest);
        public Task<bool> UpdateEvent(EventRequest eventRequest);
        public Task<bool> DeleteEvent(EventRequest eventRequest);
        public Task<Event> GetEvent(long id);
        public IQueryable<Event> GetEvents();
    }
}