using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace AnalyticService.Models.Requests.UserTestAttemptRequests
{
    public class UserTestAttemptRequest
    {
        [JsonProperty("id")]
        public long Id { get; set; }
        [JsonProperty("user_id")]
        public long UserId { get; set; }
        [JsonProperty("course_test_id")]
        public long CourseTestId { get; set; }
        [JsonProperty("score")]
        public long Score { get; set; }
        [JsonProperty("attempt_number")]
        public int AttemptNumber { get; set; }
        [JsonProperty("date")]
        public long Date { get; set; }
        [JsonProperty("passed")]
        public bool Passed { get; set; }
    }
}