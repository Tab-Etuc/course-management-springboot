package tw.edu.ntub.imd.birc.coursemanagement.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tw.edu.ntub.imd.birc.coursemanagement.databaseconfig.dao.CourseDAO;
import tw.edu.ntub.imd.birc.coursemanagement.databaseconfig.dao.StudentDAO;
import tw.edu.ntub.imd.birc.coursemanagement.exception.NotFoundException;

@Component
public class EntityExistenceValidator {
    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private CourseDAO courseDAO;

    public void validateStudentExists(String studentId) {
        if (!studentDAO.existsById(studentId)) {
            throw new NotFoundException("找不到學生");
        }
    }

    public void validateCourseExists(String courseId) {
        if (!courseDAO.existsById(courseId)) {
            throw new NotFoundException("找不到課程");
        }
    }
}
