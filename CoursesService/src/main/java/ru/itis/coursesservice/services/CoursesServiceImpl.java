package ru.itis.coursesservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.coursesservice.dto.CoursesDto;
import ru.itis.coursesservice.models.Course;
import ru.itis.coursesservice.repository.CoursesRepository;

import static ru.itis.coursesservice.dto.CoursesDto.from;

@Service
@RequiredArgsConstructor
public class CoursesServiceImpl implements CoursesService{

    private final CoursesRepository coursesRepository;

    @Override
    public CoursesDto addCourse(CoursesDto coursesDto) {
        Course newCourse = Course.builder()
                .title(coursesDto.getTitle())
                .build();

        coursesRepository.save(newCourse);

        return CoursesDto.from(newCourse);

    }

    @Override
    public CoursesDto updateCourse(Long courseId, CoursesDto coursesDto) {
        Course existedCourse = coursesRepository.findById(courseId).orElseThrow(() -> new NullPointerException("No course with id: " + courseId));
        existedCourse.setTitle(coursesDto.getTitle());
        coursesRepository.save(existedCourse);
        return CoursesDto.from(existedCourse);
    }

    @Override
    public void deleteCourse(Long courseId) {
        coursesRepository.deleteById(courseId);
    }

    @Override
    public CoursesDto getCourse(Long courseId) {
        return CoursesDto.from(coursesRepository.findById(courseId).orElseThrow(() -> new NullPointerException("No course with id: " + courseId)));
    }
}
