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
import tw.edu.ntub.imd.birc.coursemanagement.exception.DuplicateCreateException;
import tw.edu.ntub.imd.birc.coursemanagement.exception.NotFoundException;
import tw.edu.ntub.imd.birc.coursemanagement.service.StudentService;
import tw.edu.ntub.imd.birc.coursemanagement.service.transformer.impl.StudentCourseTransformer;
import tw.edu.ntub.imd.birc.coursemanagement.service.transformer.impl.StudentTransformer;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl extends BaseServiceImpl<StudentBean, Student, String> implements StudentService {
    private final StudentDAO studentDAO;
    private final StudentCourseDAO studentCourseDAO;
    private final StudentTransformer studentTransformer;
    private final StudentCourseTransformer studentCourseTransformer;

    public StudentServiceImpl(StudentDAO studentDAO,
            StudentCourseDAO studentCourseDAO,
            StudentTransformer studentTransformer,
            StudentCourseTransformer studentCourseTransformer) {
        super(studentDAO, studentTransformer);
        this.studentDAO = studentDAO;
        this.studentCourseDAO = studentCourseDAO;
        this.studentTransformer = studentTransformer;
        this.studentCourseTransformer = studentCourseTransformer;
    }

    @Transactional
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
        if (!studentDAO.existsById(studentId)) {
            throw new NotFoundException("找不到學生");
        }
        List<StudentCourse> list = studentCourseDAO.findByStudentId(studentId);
        return list.stream()
                .map(studentCourseTransformer::transferToBean)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public StudentCourseBean updateCourseRecord(String studentId, String courseId, StudentCourseBean bean) {
        if (!studentDAO.existsById(studentId)) {
            throw new NotFoundException("找不到學生");
        }
        StudentCourseId id = new StudentCourseId();
        id.setStudentId(studentId);
        id.setCourseId(courseId);

        return studentCourseDAO.findById(id).map(entity -> {
            if (bean.getGrade() != null)
                entity.setGrade(bean.getGrade());
            if (bean.getRemark() != null)
                entity.setRemark(bean.getRemark());
            studentCourseDAO.update(entity);
            return studentCourseTransformer.transferToBean(entity);
        }).orElseThrow(() -> new NotFoundException("學生未選該課"));
    }
}
