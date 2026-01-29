CREATE DATABASE IF NOT EXISTS `course_management`;
USE `course_management`;

CREATE TABLE IF NOT EXISTS `student` (
    `id` VARCHAR(10) NOT NULL COMMENT '學號',
    `name` VARCHAR(50) NOT NULL COMMENT '姓名',
    `email` VARCHAR(100) NOT NULL COMMENT 'Email',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='學生表';

CREATE TABLE IF NOT EXISTS `course` (
    `id` VARCHAR(10) NOT NULL COMMENT '課程代碼',
    `name` VARCHAR(50) NOT NULL COMMENT '課程名稱',
    `credit` INT NOT NULL COMMENT '學分',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='課程表';

CREATE TABLE IF NOT EXISTS `student_course_relation` (
    `student_id` VARCHAR(10) NOT NULL COMMENT '學號',
    `course_id` VARCHAR(10) NOT NULL COMMENT '課程代碼',
    `grade` INT DEFAULT NULL COMMENT '成績',
    `remark` VARCHAR(200) DEFAULT NULL COMMENT '備註',
    `create_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '選課時間',
    PRIMARY KEY (`student_id`, `course_id`),
    CONSTRAINT `fk_student_course_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_student_course_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='選課關聯表';
