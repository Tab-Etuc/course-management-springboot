package tw.edu.ntub.imd.birc.coursemanagement.service;

import tw.edu.ntub.imd.birc.coursemanagement.dto.CourseBean;
import tw.edu.ntub.imd.birc.coursemanagement.dto.StudentCourseBean;

public interface CourseService extends BaseService<CourseBean, String> {
    StudentCourseBean addStudent(String courseId, String studentId, StudentCourseBean bean);
}
