using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace AnalyticService.Models.Analytic.Requests
{
    public class TestAverageResultRequest
    {
        public long TestId { get; set; }
        public List<long> UserIds { get; set; }
    }
}