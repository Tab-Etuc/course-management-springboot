package tw.edu.ntub.imd.birc.coursemanagement.databaseconfig.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
public class StudentCourseId implements Serializable {
    private String studentId;
    private String courseId;
}
