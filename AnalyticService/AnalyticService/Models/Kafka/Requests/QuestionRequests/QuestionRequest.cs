using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.Json.Serialization;
using System.Threading.Tasks;
using AnalyticService.Database.Models;
using Newtonsoft.Json;

namespace AnalyticService.Models.Requests.QuestionRequests
{
    public class QuestionRequest
    {
        [JsonProperty("id")]
        public long Id { get; set; }
        [JsonProperty("test_id")]
        public long TestId { get; set; }
        [JsonProperty("title")]
        public string Title { get; set; } = null!;
        [JsonProperty("position")]
        public int Position { get; set; }
        [JsonProperty("content")]
        public string Content { get; set; } = null!;
        [JsonProperty("question_type")]
        public QuestionType QuestionType { get; set; }
    }
}