
let host = 'http://localhost:8080';
export default {
    postUserProfile : host + '/user/profile',
    getUserAvatar : host + '/user/avatar',
    postUserAvatar : host + '/user/avatar',
    getUserUser : host + '/user/user',
    getUserInstitutes : host + '/user/user/institutes',
    getUserUserUsernameUsername : host + '/user/user/username/{username}',
    getUserUserIdId : host + '/user/user/id/{id}',
    getUserInstituteInfoUsernameUsername : host + '/user/instituteInfo/username/{username}',
    getUserCourses : host + '/user/courses',
    postUniversityUniversity : host + '/university/university',
    postMajorMajor : host + '/major/major',
    postInstituteInstitute : host + '/institute/institute',
    postFacultyFaculty : host + '/faculty/faculty',
    postCourseTest : host + '/course/test',
    postCourseResourseCategory : host + '/course/resourse/category',
    postCourseCourse : host + '/course/course',
    postCourseAttachUser : host + '/course/attachUser',
    postCourseAttachStudyGroup : host + '/course/attachStudyGroup',
    getCourseId : host + '/course/{id}',
    deleteCourseId : host + '/course/{id}',
    getCourseUserCourseUserId : host + '/course/userCourse/{userId}',
    getCourseResourseCategoriesId : host + '/course/resource/categories/{id}',
    deleteCourseResourseCategoryId : host + '/course/resource/category/{id}',
    deleteCourseDetachUser : host + '/course/detachUser',
    deleteCourseDetachStudyGroup : host + '/course/detachStudyGroup',
    postCourseResourse : host + '/course/resource',
    postCourseResourseLoadFile : host + '/course/resource/load/file',
    postCourseResourseFolder : host + '/course/resource/folder',
    getCourseResourseCategoryId : host + '/course/resource/category/{id}',
    deleteCourseResourseId : host + '/course/resource/{id}',
    postAuthRegister : host + '/auth/register',
    postAuthLogin : host + '/auth/login',
    postAuthAregister : host + '/auth/aregister',
    postAdminUserAddPermission : host + '/admin/user/add-permission',
    getAdminUserUsersPage : host + '/admin/user/users/{page}',
    deleteAdminUser : host + '/admin/user',
    getAuditPage : host + '/audit/{page}'
};