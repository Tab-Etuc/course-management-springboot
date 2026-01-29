package tw.edu.ntub.imd.birc.coursemanagement.service;

import tw.edu.ntub.imd.birc.coursemanagement.dto.StudentBean;
import tw.edu.ntub.imd.birc.coursemanagement.dto.StudentCourseBean;

import java.util.List;

public interface StudentService extends BaseService<StudentBean, String> {
    List<StudentCourseBean> getCourseList(String studentId);

    StudentCourseBean updateCourseRecord(String studentId, String courseId, StudentCourseBean bean);
}
