package ru.espada.ep.iptip.university.institute;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.espada.ep.iptip.university.UniversityEntity;
import ru.espada.ep.iptip.university.UniversityRepository;

@RequiredArgsConstructor
@Service
public class InstituteServiceImpl implements InstituteService {

    private InstituteRepository repository;
    private UniversityRepository universityRepository;

    @Override
    public Long createInstitute(CreateInstituteModel createInstituteModel) {
        UniversityEntity university = universityRepository.findById(createInstituteModel.getUniversityId()).orElseThrow(() -> new UsernameNotFoundException("University not found"));
        InstituteEntity instituteEntity = repository.save(InstituteEntity.builder()
                .name(createInstituteModel.getName())
                .university(university)
                .build());
        return instituteEntity.getId();
    }

    @Autowired
    public void setRepository(InstituteRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setUniversityRepository(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }
}
