using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.Json.Serialization;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace AnalyticService.Models.Requests.TestsRequests
{
    public class TestRequest
    {
        [JsonProperty("id")]
        public long Id { get; set; }
        [JsonProperty("name")]
        public string Name { get; set; } = null!;
        [JsonProperty("attempts")]
        public int Attempts { get; set; }
        [JsonProperty("time")]
        public long Time { get; set; }
        [JsonProperty("min_score")]
        public int MinScore { get; set; }
        [JsonProperty("max_score")]
        public int MaxScore { get; set; }
    }
}