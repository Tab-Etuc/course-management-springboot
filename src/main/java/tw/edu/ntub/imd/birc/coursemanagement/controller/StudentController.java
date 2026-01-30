package tw.edu.ntub.imd.birc.coursemanagement.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tw.edu.ntub.imd.birc.coursemanagement.dto.StudentBean;

import tw.edu.ntub.imd.birc.coursemanagement.dto.StudentCourseBean;
import tw.edu.ntub.imd.birc.coursemanagement.dto.UpdateCourseRecordRequest;
import tw.edu.ntub.imd.birc.coursemanagement.service.StudentService;
import tw.edu.ntub.imd.birc.coursemanagement.util.http.BindingResultUtils;
import tw.edu.ntub.imd.birc.coursemanagement.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.birc.coursemanagement.util.json.object.ObjectData;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<String> searchAll() {
        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(studentService.searchAll(), (data, student) -> {
                    data.add("id", student.getId());
                    data.add("name", student.getName());
                    data.add("email", student.getEmail());
                })
                .build();
    }

    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody StudentBean studentBean, BindingResult bindingResult) {
        BindingResultUtils.validate(bindingResult);
        studentService.save(studentBean);
        return ResponseEntityBuilder.success()
                .status(HttpStatus.CREATED)
                .message("新增成功")
                .emptyObject()
                .build();
    }

    @GetMapping("/{id}/courses")
    public ResponseEntity<String> getCourses(@PathVariable String id) {
        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(studentService.getCourseList(id), (data, bean) -> {
                    data.add("courseId", bean.getCourseId());
                    data.add("courseName", bean.getCourseName());
                    data.add("courseCredit", bean.getCourseCredit());
                    data.add("grade", bean.getGrade());
                    data.add("remark", bean.getRemark());
                    data.add("createDate", bean.getCreateDate()
                            .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
                })
                .build();
    }

    @PatchMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<String> updateCourseRecord(@PathVariable String studentId,
                                                     @PathVariable String courseId,
                                                     @Valid @RequestBody UpdateCourseRecordRequest bean,
                                                     BindingResult bindingResult) {
        BindingResultUtils.validate(bindingResult);
        StudentCourseBean result = studentService.updateCourseRecord(studentId, courseId, bean);
        ObjectData data = new ObjectData()
                .add("studentId", result.getStudentId())
                .add("courseId", result.getCourseId())
                .add("grade", result.getGrade())
                .add("remark", result.getRemark());

        return ResponseEntityBuilder.success()
                .message("修改成功")
                .data(data)
                .build();
    }
}
