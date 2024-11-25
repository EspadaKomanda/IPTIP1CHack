using System;
using System.Collections.Generic;
using System.Linq;
using System.Text.Json.Serialization;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace AnalyticService.Models.Requests.TestsRequests
{
    public class DeleteRequest
    {
        [JsonProperty("id")]
        public long Id { get; set; }
    }
}