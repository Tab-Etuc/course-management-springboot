package tw.edu.ntub.imd.birc.coursemanagement.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.edu.ntub.imd.birc.coursemanagement.databaseconfig.dao.StudentCourseDAO;
import tw.edu.ntub.imd.birc.coursemanagement.databaseconfig.dao.StudentDAO;
import tw.edu.ntub.imd.birc.coursemanagement.databaseconfig.entity.Student;
import tw.edu.ntub.imd.birc.coursemanagement.databaseconfig.entity.StudentCourse;
import tw.edu.ntub.imd.birc.coursemanagement.databaseconfig.entity.StudentCourseId;
import tw.edu.ntub.imd.birc.coursemanagement.dto.StudentBean;
import tw.edu.ntub.imd.birc.coursemanagement.dto.StudentCourseBean;
import tw.edu.ntub.imd.birc.coursemanagement.dto.UpdateCourseRecordRequest;
import tw.edu.ntub.imd.birc.coursemanagement.exception.DuplicateCreateException;
import tw.edu.ntub.imd.birc.coursemanagement.exception.NotFoundException;
import tw.edu.ntub.imd.birc.coursemanagement.service.StudentService;
import tw.edu.ntub.imd.birc.coursemanagement.service.transformer.impl.StudentCourseTransformer;
import tw.edu.ntub.imd.birc.coursemanagement.service.transformer.impl.StudentTransformer;
import tw.edu.ntub.imd.birc.coursemanagement.validation.EntityExistenceValidator;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl extends BaseServiceImpl<StudentBean, Student, String> implements StudentService {
    private final StudentDAO studentDAO;
    private final StudentCourseDAO studentCourseDAO;
    private final StudentTransformer studentTransformer;
    private final StudentCourseTransformer studentCourseTransformer;
    private final EntityExistenceValidator entityExistenceValidator;

    public StudentServiceImpl(StudentDAO studentDAO,
            StudentCourseDAO studentCourseDAO,
            StudentTransformer studentTransformer,
            StudentCourseTransformer studentCourseTransformer,
            EntityExistenceValidator entityExistenceValidator) {
        super(studentDAO, studentTransformer);
        this.studentDAO = studentDAO;
        this.studentCourseDAO = studentCourseDAO;
        this.studentTransformer = studentTransformer;
        this.studentCourseTransformer = studentCourseTransformer;
        this.entityExistenceValidator = entityExistenceValidator;
    }

    @Override
    public StudentBean save(StudentBean studentBean) {
        if (studentDAO.existsById(studentBean.getId())) {
            throw new DuplicateCreateException("學號重複");
        }
        Student student = studentTransformer.transferToEntity(studentBean);
        return studentTransformer.transferToBean(studentDAO.save(student));
    }

    @Override
    public List<StudentCourseBean> getCourseList(String studentId) {
        entityExistenceValidator.validateStudentExists(studentId);
        List<StudentCourse> list = studentCourseDAO.findByStudentId(studentId);
        return list.stream()
                .map(studentCourseTransformer::transferToBean)
                .collect(Collectors.toList());
    }

    @Override
    public StudentCourseBean updateCourseRecord(String studentId, String courseId, UpdateCourseRecordRequest bean) {
        entityExistenceValidator.validateStudentExists(studentId);
        
        StudentCourseId id = new StudentCourseId();
        id.setStudentId(studentId);
        id.setCourseId(courseId);

        return studentCourseDAO.findById(id).map(entity -> {
            entity.setGrade(bean.getGrade());
            entity.setRemark(bean.getRemark());
            studentCourseDAO.update(entity);
            return studentCourseTransformer.transferToBean(entity);
        }).orElseThrow(() -> new NotFoundException("學生未選該課"));
    }
}
