using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AnalyticService.Database;
using AnalyticService.Database.Models;
using AnalyticService.Models.Requests.QuestionRequests;
using AnalyticService.Models.Requests.TestsRequests;
using KafkaAttributesLib.Attributes;
using Microsoft.EntityFrameworkCore;

namespace AnalyticService.Services.Questions
{
    [KafkaServiceName("QuestionService")]
    public class QuestionService(IApplicationContext context, ILogger<QuestionService> logger) : IQuestionService
    {
        private readonly IApplicationContext _context = context;
        private readonly ILogger<QuestionService> _logger = logger;

        [KafkaMethod("addQuestion")]
        public async Task<bool> AddQuestion(QuestionRequest questionRequest)
        {
            try
            {
                var question = new Question()
                {
                    Id = questionRequest.Id,
                    Content = questionRequest.Content,
                    QuestionType = questionRequest.QuestionType,
                    TestId = questionRequest.TestId,
                    Title = questionRequest.Title
                };

                await _context.Questions.AddAsync(question);
                return await _context.SaveChangesAsync() >= 0;
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }

        [KafkaMethod("deleteQuestion")]
        public async Task<bool> DeleteQuestion(DeleteRequest deleteRequest)
        {
            try
            {
                var question = await _context.Questions.FirstOrDefaultAsync(t => t.Id == deleteRequest.Id);
                if (question == null)
                {
                    return false;
                }
                _context.Questions.Remove(question);
                return await _context.SaveChangesAsync() >= 0;
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }

        [KafkaMethod("updateQuestion")]
        public async Task<bool> UpdateQuestion(QuestionRequest questionRequest)
        {
            try
            {
                var question = new Question()
                {
                    Id = questionRequest.Id,
                    Content = questionRequest.Content,
                    QuestionType = questionRequest.QuestionType,
                    TestId = questionRequest.TestId,
                    Title = questionRequest.Title
                };
                _context.Questions.Update(question);
                return await _context.SaveChangesAsync() >= 0;
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }
        public async Task<Question> GetQuestion(long id)
        {
            try
            {
                return await _context.Questions.FirstOrDefaultAsync(t => t.Id == id);
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }

        public IQueryable<Question> GetQuestions()
        {
            try
            {
                return _context.Questions.AsQueryable();
            }
            catch (Exception e)
            {
                _logger.LogError(e, "Unhandled error");
                throw;
            }
        }

        
    }
}