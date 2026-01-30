package tw.edu.ntub.imd.birc.coursemanagement.service;

import tw.edu.ntub.imd.birc.coursemanagement.dto.CourseBean;
import tw.edu.ntub.imd.birc.coursemanagement.dto.StudentCourseBean;
import tw.edu.ntub.imd.birc.coursemanagement.dto.UpdateCourseRecordRequest;

public interface CourseService extends BaseService<CourseBean, String> {
    StudentCourseBean addStudent(String courseId, String studentId, StudentCourseBean bean);

    StudentCourseBean updateCourseRecord(String courseId, String studentId, UpdateCourseRecordRequest bean);
}
