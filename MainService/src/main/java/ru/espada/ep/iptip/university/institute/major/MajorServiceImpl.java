package ru.espada.ep.iptip.university.institute.major;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.espada.ep.iptip.university.UniversityEntity;
import ru.espada.ep.iptip.university.institute.InstituteEntity;
import ru.espada.ep.iptip.university.institute.InstituteRepository;

@RequiredArgsConstructor
@Service
public class MajorServiceImpl implements MajorService {

    private MajorRepository repository;
    private InstituteRepository instituteRepository;

    @Override
    public Long createMajor(CreateMajorModel createMajorModel) {
        InstituteEntity institute = instituteRepository.findById(createMajorModel.getInstituteId()).orElseThrow(() -> new UsernameNotFoundException("Institute not found"));
        MajorEntity majorEntity = repository.save(MajorEntity.builder()
                .name(createMajorModel.getName())
                .institute(institute)
                .build());
        return majorEntity.getId();
    }

    @Autowired
    public void setRepository(MajorRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setInstituteRepository(InstituteRepository instituteRepository) {
        this.instituteRepository = instituteRepository;
    }
}
