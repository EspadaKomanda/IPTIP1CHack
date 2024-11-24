package ru.espada.ep.iptip.university.institute.major.faculty;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FacultyServiceImpl implements FacultyService {
    private FacultyRepository repository;

    @Autowired
    public void setFacultyRepository(FacultyRepository repository) {
        this.repository = repository;
    }
}
