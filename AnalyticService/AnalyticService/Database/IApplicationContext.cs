using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AnalyticService.Database.Models;
using AnalyticService.Database.Models.Attendance;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.ChangeTracking;
using Microsoft.EntityFrameworkCore.Storage;

namespace AnalyticService.Database
{
    public interface IApplicationContext
    {
        public DbSet<Event> Events { get; set; }
        public DbSet<UserEventAttendance> UserEventAttendances { get; set; }
        public DbSet<CourseTest> CourseTests { get; set;}
        public DbSet<Question> Questions { get; set; }
        public DbSet<Test> Tests { get; set; }
        public DbSet<UserTestAttempt> UserTestAttempts { get; set; }
        public DbSet<UserTestAttemptAnswer> UserTestAttemptAnswers { get; set; }
        Task<int> SaveChangesAsync();
        EntityEntry Entry(object entity);

        Task<IDbContextTransaction> BeginTransactionAsync(CancellationToken cancellationToken = default);
    }
}