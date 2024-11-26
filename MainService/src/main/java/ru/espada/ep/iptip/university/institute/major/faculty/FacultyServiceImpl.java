package ru.espada.ep.iptip.university.institute.major.faculty;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.espada.ep.iptip.university.institute.major.MajorEntity;
import ru.espada.ep.iptip.university.institute.major.MajorRepository;

@RequiredArgsConstructor
@Service
public class FacultyServiceImpl implements FacultyService {

    private FacultyRepository facultyRepository;
    private MajorRepository majorRepository;

    @Override
    public Long createFaculty(CreateFacultyModel createFacultyModel) {
        MajorEntity major = majorRepository.findById(createFacultyModel.getMajorId()).orElseThrow(() -> new UsernameNotFoundException("Major not found"));
        FacultyEntity facultyEntity = facultyRepository.save(FacultyEntity.builder()
                .name(createFacultyModel.getName())
                .major(major)
                .build());
        return facultyEntity.getId();
    }

    @Autowired
    public void setFacultyRepository(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Autowired
    public void setMajorRepository(MajorRepository majorRepository) {
        this.majorRepository = majorRepository;
    }
}
