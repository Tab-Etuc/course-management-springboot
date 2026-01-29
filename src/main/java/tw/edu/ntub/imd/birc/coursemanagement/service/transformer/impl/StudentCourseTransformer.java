package tw.edu.ntub.imd.birc.coursemanagement.service.transformer.impl;

import org.springframework.stereotype.Component;
import tw.edu.ntub.imd.birc.coursemanagement.databaseconfig.entity.StudentCourse;
import tw.edu.ntub.imd.birc.coursemanagement.dto.StudentCourseBean;
import tw.edu.ntub.imd.birc.coursemanagement.service.transformer.BeanEntityTransformer;

import javax.annotation.Nonnull;

@Component
public class StudentCourseTransformer implements BeanEntityTransformer<StudentCourseBean, StudentCourse> {
    @Nonnull
    @Override
    public StudentCourse transferToEntity(@Nonnull StudentCourseBean bean) {
        StudentCourse entity = new StudentCourse();
        entity.setStudentId(bean.getStudentId());
        entity.setCourseId(bean.getCourseId());
        entity.setGrade(bean.getGrade());
        entity.setRemark(bean.getRemark());
        // createDate is usually handled by Auditing or Database default, but setting it
        // explicitly if provided
        if (bean.getCreateDate() != null) {
            entity.setCreateDate(bean.getCreateDate());
        }
        return entity;
    }

    @Nonnull
    @Override
    public StudentCourseBean transferToBean(@Nonnull StudentCourse entity) {
        StudentCourseBean bean = new StudentCourseBean();
        bean.setStudentId(entity.getStudentId());
        bean.setCourseId(entity.getCourseId());
        bean.setGrade(entity.getGrade());
        bean.setRemark(entity.getRemark());
        bean.setCreateDate(entity.getCreateDate());
        if (entity.getCourse() != null) {
            bean.setCourseName(entity.getCourse().getName());
            bean.setCourseCredit(entity.getCourse().getCredit());
        }
        return bean;
    }
}
