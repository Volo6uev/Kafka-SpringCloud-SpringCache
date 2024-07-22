package ru.itis.coursesservice.services;

import ru.itis.coursesservice.dto.CoursesDto;

public interface CoursesService {

    CoursesDto addCourse(CoursesDto coursesDto);

    CoursesDto updateCourse(Long courseId, CoursesDto coursesDto);

    void deleteCourse(Long courseId);

    CoursesDto getCourse(Long courseId);

}
