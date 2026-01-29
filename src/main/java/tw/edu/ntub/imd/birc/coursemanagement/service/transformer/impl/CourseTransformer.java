package tw.edu.ntub.imd.birc.coursemanagement.service.transformer.impl;

import org.springframework.stereotype.Component;
import tw.edu.ntub.imd.birc.coursemanagement.databaseconfig.entity.Course;
import tw.edu.ntub.imd.birc.coursemanagement.dto.CourseBean;
import tw.edu.ntub.imd.birc.coursemanagement.service.transformer.BeanEntityTransformer;

import javax.annotation.Nonnull;

@Component
public class CourseTransformer implements BeanEntityTransformer<CourseBean, Course> {
    @Nonnull
    @Override
    public Course transferToEntity(@Nonnull CourseBean courseBean) {
        Course course = new Course();
        course.setId(courseBean.getId());
        course.setName(courseBean.getName());
        course.setCredit(courseBean.getCredit());
        return course;
    }

    @Nonnull
    @Override
    public CourseBean transferToBean(@Nonnull Course course) {
        CourseBean courseBean = new CourseBean();
        courseBean.setId(course.getId());
        courseBean.setName(course.getName());
        courseBean.setCredit(course.getCredit());
        return courseBean;
    }
}
