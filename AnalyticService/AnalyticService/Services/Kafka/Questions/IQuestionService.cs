using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AnalyticService.Database.Models;
using AnalyticService.Models.Requests.QuestionRequests;
using AnalyticService.Models.Requests.TestsRequests;

namespace AnalyticService.Services.Questions
{
    public interface IQuestionService
    {
        public Task<bool> AddQuestion(QuestionRequest questionRequest);
        public Task<bool> UpdateQuestion(QuestionRequest questionRequest);
        public Task<bool> DeleteQuestion(DeleteRequest deleteRequest);
        public Task<Question> GetQuestion(long id);
        public IQueryable<Question> GetQuestions();
    }
}