package tw.edu.ntub.imd.birc.coursemanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Schema(name = "UpdateCourseRecordRequest", description = "更新課程紀錄請求")
public class UpdateCourseRecordRequest {
    @NotNull(message = "成績不能為空")
    @Schema(description = "成績", example = "80")
    private Integer grade;

    @NotNull(message = "備註不能為空")
    @Schema(description = "備註", example = "表現良好")
    private String remark;
}
