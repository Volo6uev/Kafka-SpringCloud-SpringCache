package ru.itis.coursesservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.coursesservice.models.Course;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoursesDto {
    private Long id;
    private String title;

    public static CoursesDto from(Course course) {
        return CoursesDto.builder()
                .id(course.getId())
                .title(course.getTitle())
                .build();
    }

    public static List<CoursesDto> from(List<Course> courses) {
        return courses.stream().map(CoursesDto::from).collect(Collectors.toList());
    }

}
