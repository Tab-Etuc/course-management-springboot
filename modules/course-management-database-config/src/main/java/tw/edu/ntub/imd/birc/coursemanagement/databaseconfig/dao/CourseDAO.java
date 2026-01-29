package tw.edu.ntub.imd.birc.coursemanagement.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.birc.coursemanagement.databaseconfig.entity.Course;

@Repository
public interface CourseDAO extends BaseDAO<Course, String> {
}
