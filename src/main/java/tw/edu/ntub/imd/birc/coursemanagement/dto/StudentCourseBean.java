package tw.edu.ntub.imd.birc.coursemanagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(name = "StudentCourse", description = "學生選課資料")
public class StudentCourseBean {
    @Schema(description = "學號", example = "11046101")
    private String studentId;

    @Schema(description = "課程代碼", example = "CS101")
    private String courseId;

    @Schema(description = "課程名稱", example = "計算機概論")
    private String courseName;

    @Schema(description = "學分", example = "3")
    private Integer courseCredit;

    @Schema(description = "成績", example = "95")
    private Integer grade;

    @Schema(description = "備註", example = "期末表現優異")
    private String remark;

    @Schema(description = "選課時間", example = "2026-01-25T10:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Taipei")
    private LocalDateTime createDate;
}
