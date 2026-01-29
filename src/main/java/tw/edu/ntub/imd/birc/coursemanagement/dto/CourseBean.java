package tw.edu.ntub.imd.birc.coursemanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Schema(name = "Course", description = "課程資料")
public class CourseBean {
    @Schema(description = "課程代碼", example = "CS101")
    @NotBlank(message = "缺少必要欄位：id")
    @Size(max = 10, message = "課程代碼長度不可超過10")
    private String id;

    @Schema(description = "課程名稱", example = "計算機概論")
    @NotBlank(message = "缺少必要欄位：name")
    @Size(max = 50, message = "課程名稱長度不可超過50")
    private String name;

    @Schema(description = "學分", example = "3")
    @NotNull(message = "缺少必要欄位：credit")
    @Min(value = 0, message = "credit 不可為負數")
    private Integer credit;
}
