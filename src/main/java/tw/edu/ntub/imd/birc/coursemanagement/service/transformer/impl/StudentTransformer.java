package tw.edu.ntub.imd.birc.coursemanagement.service.transformer.impl;

import org.springframework.stereotype.Component;
import tw.edu.ntub.imd.birc.coursemanagement.databaseconfig.entity.Student;
import tw.edu.ntub.imd.birc.coursemanagement.dto.StudentBean;
import tw.edu.ntub.imd.birc.coursemanagement.service.transformer.BeanEntityTransformer;

import javax.annotation.Nonnull;

@Component
public class StudentTransformer implements BeanEntityTransformer<StudentBean, Student> {
    @Nonnull
    @Override
    public Student transferToEntity(@Nonnull StudentBean studentBean) {
        Student student = new Student();
        student.setId(studentBean.getId());
        student.setName(studentBean.getName());
        student.setEmail(studentBean.getEmail());
        return student;
    }

    @Nonnull
    @Override
    public StudentBean transferToBean(@Nonnull Student student) {
        StudentBean studentBean = new StudentBean();
        studentBean.setId(student.getId());
        studentBean.setName(student.getName());
        studentBean.setEmail(student.getEmail());
        return studentBean;
    }
}
