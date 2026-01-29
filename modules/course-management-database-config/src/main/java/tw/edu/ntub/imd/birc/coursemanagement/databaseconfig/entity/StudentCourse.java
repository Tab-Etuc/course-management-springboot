package tw.edu.ntub.imd.birc.coursemanagement.databaseconfig.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "student_course_relation")
@IdClass(StudentCourseId.class)
@EntityListeners(AuditingEntityListener.class)
public class StudentCourse {
    @Id
    @Column(name = "student_id", length = 10, nullable = false)
    private String studentId;

    @Id
    @Column(name = "course_id", length = 10, nullable = false)
    private String courseId;

    @Column(name = "grade")
    private Integer grade;

    @Column(name = "remark", length = 200)
    private String remark;

    @CreatedDate
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", insertable = false, updatable = false)
    private Course course;
}
