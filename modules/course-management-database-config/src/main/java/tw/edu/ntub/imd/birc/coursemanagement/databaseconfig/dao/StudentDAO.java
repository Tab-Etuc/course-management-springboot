package tw.edu.ntub.imd.birc.coursemanagement.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.birc.coursemanagement.databaseconfig.entity.Student;

@Repository
public interface StudentDAO extends BaseDAO<Student, String> {
}
