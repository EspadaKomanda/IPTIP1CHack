using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace AnalyticService.Models.Requests.UserEventAttendanceRequests
{
    public class UserEventAttendanceRequest
    {
        [JsonProperty("id")]
        public long Id { get; set; }
        [JsonProperty("event_id")]
        public long EventId { get; set; }
        [JsonProperty("user_id")]
        public long UserId { get; set; }
        [JsonProperty("status")]
        public bool Status { get; set; }
    }
}