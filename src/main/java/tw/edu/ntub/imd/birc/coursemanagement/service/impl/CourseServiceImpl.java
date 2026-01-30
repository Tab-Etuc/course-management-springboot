package tw.edu.ntub.imd.birc.coursemanagement.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.edu.ntub.imd.birc.coursemanagement.databaseconfig.dao.CourseDAO;
import tw.edu.ntub.imd.birc.coursemanagement.databaseconfig.dao.StudentCourseDAO;
import tw.edu.ntub.imd.birc.coursemanagement.databaseconfig.dao.StudentDAO;
import tw.edu.ntub.imd.birc.coursemanagement.databaseconfig.entity.Course;
import tw.edu.ntub.imd.birc.coursemanagement.databaseconfig.entity.StudentCourse;
import tw.edu.ntub.imd.birc.coursemanagement.databaseconfig.entity.StudentCourseId;
import tw.edu.ntub.imd.birc.coursemanagement.dto.CourseBean;
import tw.edu.ntub.imd.birc.coursemanagement.dto.StudentCourseBean;
import tw.edu.ntub.imd.birc.coursemanagement.exception.ConflictException;
import tw.edu.ntub.imd.birc.coursemanagement.exception.DuplicateCreateException;
import tw.edu.ntub.imd.birc.coursemanagement.exception.NotFoundException;
import tw.edu.ntub.imd.birc.coursemanagement.service.CourseService;
import tw.edu.ntub.imd.birc.coursemanagement.service.transformer.impl.CourseTransformer;
import tw.edu.ntub.imd.birc.coursemanagement.service.transformer.impl.StudentCourseTransformer;

import java.time.LocalDateTime;

@Service
public class CourseServiceImpl extends BaseServiceImpl<CourseBean, Course, String> implements CourseService {
    private final CourseDAO courseDAO;
    private final StudentDAO studentDAO;
    private final StudentCourseDAO studentCourseDAO;
    private final CourseTransformer courseTransformer;
    private final StudentCourseTransformer studentCourseTransformer;

    public CourseServiceImpl(CourseDAO courseDAO,
            StudentDAO studentDAO,
            StudentCourseDAO studentCourseDAO,
            CourseTransformer courseTransformer,
            StudentCourseTransformer studentCourseTransformer) {
        super(courseDAO, courseTransformer);
        this.courseDAO = courseDAO;
        this.studentDAO = studentDAO;
        this.studentCourseDAO = studentCourseDAO;
        this.courseTransformer = courseTransformer;
        this.studentCourseTransformer = studentCourseTransformer;
    }

    @Transactional
    @Override
    public CourseBean save(CourseBean courseBean) {
        if (courseDAO.existsById(courseBean.getId())) {
            throw new DuplicateCreateException("課程代碼重複");
        }
        Course course = courseTransformer.transferToEntity(courseBean);
        return courseTransformer.transferToBean(courseDAO.save(course));
    }

    @Transactional
    @Override
    public StudentCourseBean addStudent(String courseId, String studentId, StudentCourseBean bean) {
        if (!studentDAO.existsById(studentId)) {
            throw new NotFoundException("找不到學生");
        }
        if (!courseDAO.existsById(courseId)) {
            throw new NotFoundException("找不到課程");
        }

        StudentCourseId id = new StudentCourseId();
        id.setStudentId(studentId);
        id.setCourseId(courseId);

        if (studentCourseDAO.existsById(id)) {
            throw new ConflictException("學生已選修該課程，請勿重複選課");
        }

        StudentCourse entity = new StudentCourse();
        entity.setStudentId(studentId);
        entity.setCourseId(courseId);
        entity.setGrade(bean.getGrade());
        entity.setRemark(bean.getRemark());
        entity.setCreateDate(LocalDateTime.now(java.time.ZoneId.of("Asia/Taipei")));

        studentCourseDAO.save(entity);

        return studentCourseTransformer.transferToBean(entity);
    }
}
