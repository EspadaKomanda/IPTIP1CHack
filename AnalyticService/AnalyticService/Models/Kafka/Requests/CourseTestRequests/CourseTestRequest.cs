using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.Json.Serialization;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace AnalyticService.Models.Requests.CourseTestRequests
{
    public class CourseTestRequest
    {
        [JsonProperty("id")]
        public long Id { get; set; }
        [JsonProperty("name")]
        public string Name { get; set; } = null!;
        [JsonProperty("course_id")]
        public long CourseId { get; set; }
        [JsonProperty("start_time")]
        public long StartTime { get; set; }
        [JsonProperty("end_time")]
        public long EndTime { get; set; }
        [JsonProperty("test_id")]
        public long TestId { get; set; }
        [JsonProperty("attempts")]
        public int Attempts { get; set; }
        [JsonProperty("time")]
        public long Time { get; set; } 
        [JsonProperty("min_score")]
        public double MinScore { get; set; }
        [JsonProperty("max_score")]
        public double MaxScore { get; set; }
    }
}