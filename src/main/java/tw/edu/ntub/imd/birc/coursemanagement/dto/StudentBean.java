package tw.edu.ntub.imd.birc.coursemanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Schema(name = "Student", description = "學生資料")
public class StudentBean {
    @Schema(description = "學號", example = "11046001")
    @NotBlank(message = "缺少必要欄位：id")
    @Size(max = 10, message = "學號長度不可超過10")
    private String id;

    @Schema(description = "姓名", example = "王小明")
    @NotBlank(message = "缺少必要欄位：name")
    @Size(max = 50, message = "姓名長度不可超過50")
    private String name;

    @Schema(description = "Email", example = "xiaoming@example.com")
    @NotBlank(message = "缺少必要欄位：email")
    @Email(message = "信箱格式錯誤")
    @Size(max = 100, message = "信箱長度不可超過100")
    private String email;
}
