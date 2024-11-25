using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.Json.Serialization;
using System.Threading.Tasks;
using AnalyticService.Database.Models;
using Newtonsoft.Json;

namespace AnalyticService.Models.Requests.UserTestAttemptAnswerRequests
{
    public class UserTestAttemptAnswerRequest
    {
        [JsonProperty("id")]
        public long Id { get; set; }
        [JsonProperty("user_test_attempt_id")]
        public long UserTestAttemptId { get; set; }
        [JsonProperty("question_id")]
        public long QuestionId { get; set; }
        [JsonProperty("content")]
        public string Content { get; set; } = null!;
        [JsonProperty("status")]
        public Status Status { get; set; }
        [JsonProperty("is_correct")]
        public bool IsCorrect { get; set; }
        [JsonProperty("status_modified_at")]
        public long StatusModifiedAt { get; set; }
    }
}