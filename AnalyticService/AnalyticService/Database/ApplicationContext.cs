using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AnalyticService.Database.Models;
using AnalyticService.Database.Models.Attendance;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Storage;

namespace AnalyticService.Database
{
    public class ApplicationContext : DbContext , IApplicationContext
    {
        public DbSet<Event> Events { get; set; }
        public DbSet<UserEventAttendance> UserEventAttendances { get; set; }
        public DbSet<CourseTest> CourseTests { get; set;}
        public DbSet<Question> Questions { get; set; }
        public DbSet<Test> Tests { get; set; }
        public DbSet<UserTestAttempt> UserTestAttempts { get; set; }
        public DbSet<UserTestAttemptAnswer> UserTestAttemptAnswers { get; set; }

        public ApplicationContext(DbContextOptions<ApplicationContext> options) : base(options)
        {
            Database.EnsureCreated();
        }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseLazyLoadingProxies();
        }
        public async Task<IDbContextTransaction> BeginTransactionAsync(CancellationToken cancellationToken = default)
        {
            return await Database.BeginTransactionAsync(cancellationToken);
        }
        public async Task<int> SaveChangesAsync()
        {
            return await base.SaveChangesAsync();
        }
    }
   
}