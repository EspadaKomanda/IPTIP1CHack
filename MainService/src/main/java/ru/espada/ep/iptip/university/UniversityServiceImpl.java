package ru.espada.ep.iptip.university;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UniversityServiceImpl implements UniversityService {

    private UniversityRepository universityRepository;

    @Override
    public Long createUniversity(CreateUniversityModel createUniversityModel) {
        return universityRepository.save(UniversityEntity.builder().name(createUniversityModel.getName()).build()).getId();
    }

    @Autowired
    public void setUniversityRepository(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }
}
