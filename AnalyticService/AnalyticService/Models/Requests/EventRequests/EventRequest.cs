using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace AnalyticService.Models.Requests.EventRequests
{
    public class EventRequest
    {
        [JsonProperty("id")]
        public long Id { get; set; }
        [JsonProperty("study_group_id")]
        public long StudyGroupId { get; set; }
        [JsonProperty("name")]
        public string Name { get; set; } = null!;
        [JsonProperty("duration")]
        public long Duration { get; set; }
        [JsonProperty("date")]
        public long Date { get; set; }
        [JsonProperty("weekday")]
        public int Weekday { get; set; }
        [JsonProperty("is_week_even")]
        public bool IsWeekEven { get; set; }
        [JsonProperty("begin_date")]
        public long BeginDate { get; set; }
        [JsonProperty("end_date")]
        public long EndDate { get; set; }
    }
}