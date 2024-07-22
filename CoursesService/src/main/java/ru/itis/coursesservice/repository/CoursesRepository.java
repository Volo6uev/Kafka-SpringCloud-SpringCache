package ru.itis.coursesservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.coursesservice.models.Course;

public interface CoursesRepository extends JpaRepository<Course , Long> {
}
