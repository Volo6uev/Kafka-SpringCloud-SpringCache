package ru.itis.coursesservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.coursesservice.dto.CoursesDto;
import ru.itis.coursesservice.services.CoursesService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/courses")
@Slf4j
@RequiredArgsConstructor
public class CoursesController {

    private final CoursesService coursesService;

    private final CacheManager cacheManager;

    @GetMapping
    public ResponseEntity<List<CoursesDto>> getCourses() {
        log.info("Get response for courses");
        return ResponseEntity.ok(Arrays.asList(
                CoursesDto.builder().title("Course 1").id(1L).build(),
                CoursesDto.builder().title("Course 2").id(2L).build(),
                CoursesDto.builder().title("Course 3").id(3L).build(),
                CoursesDto.builder().title("Course 4").id(4L).build())
        );
    }

    @Cacheable(value = "CoursesDto", key = "#courseId")
    @GetMapping("/{course-id}")
    public CoursesDto getCourse(@PathVariable("course-id") Long courseId) {
        return coursesService.getCourse(courseId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CoursesDto addCourse(@RequestBody CoursesDto course) {
        return coursesService.addCourse(course);
    }


    @CachePut(value = "CoursesDto",key="#courseId")
    @PutMapping(value = "/{course-id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CoursesDto updateCourse(@PathVariable("course-id") Long courseId, @RequestBody CoursesDto course) {
        course.setId(courseId);
        return coursesService.updateCourse(courseId, course);
    }

    @DeleteMapping(value = "/{course-id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteCourse(@PathVariable("course-id") Long courseId) {
        coursesService.deleteCourse(courseId);
        Objects.requireNonNull(cacheManager.getCache("cache")).evict(courseId);
    }

}

