package tw.edu.ntub.imd.birc.coursemanagement.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tw.edu.ntub.imd.birc.coursemanagement.dto.CourseBean;
import tw.edu.ntub.imd.birc.coursemanagement.dto.StudentCourseBean;
import tw.edu.ntub.imd.birc.coursemanagement.service.CourseService;
import tw.edu.ntub.imd.birc.coursemanagement.util.http.BindingResultUtils;
import tw.edu.ntub.imd.birc.coursemanagement.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.birc.coursemanagement.util.http.ResponseUtils;
import tw.edu.ntub.imd.birc.coursemanagement.util.json.object.ObjectData;

import javax.validation.Valid;
import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<String> searchAll() {
        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(courseService.searchAll(), (data, course) -> {
                    data.add("id", course.getId());
                    data.add("name", course.getName());
                    data.add("credit", course.getCredit());
                })
                .build();
    }

    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody CourseBean courseBean, BindingResult bindingResult) {
        BindingResultUtils.validate(bindingResult);
        CourseBean saved = courseService.save(courseBean);
        try {
            String json = ResponseUtils.createMapper().writeValueAsString(saved);
            return ResponseEntityBuilder.success()
                    .message("新增成功")
                    .data(new ObjectData(json))
                    .build();
        } catch (IOException e) {
            return ResponseEntityBuilder.error().message(e.getMessage()).build();
        }
    }

    @PostMapping("/{courseId}/students/{studentId}")
    public ResponseEntity<String> addStudent(@PathVariable String courseId,
                                             @PathVariable String studentId,
                                             @Valid @RequestBody UpdateCourseRecordRequest bean) {
        StudentCourseBean result = courseService.updateCourseRecord(studentId, courseId, bean);
        ObjectData data = new ObjectData()
                .add("studentId", result.getStudentId())
                .add("courseId", result.getCourseId())
                .add("grade", result.getGrade())
                .add("remark", result.getRemark())
                .add("createDate", result.getCreateDate()
                        .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));

        return ResponseEntityBuilder.success()
                .message("選課成功")
                .data(data)
                .build();
    }
}
