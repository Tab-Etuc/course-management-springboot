package tw.edu.ntub.imd.birc.coursemanagement.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.birc.coursemanagement.databaseconfig.entity.StudentCourse;
import tw.edu.ntub.imd.birc.coursemanagement.databaseconfig.entity.StudentCourseId;

import java.util.List;

@Repository
public interface StudentCourseDAO extends BaseDAO<StudentCourse, StudentCourseId> {
    List<StudentCourse> findByStudentId(String studentId);
}
