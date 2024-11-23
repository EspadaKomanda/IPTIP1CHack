package ru.espada.ep.iptip.university.institute;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InstituteServiceImpl implements InstituteService {
    private InstituteRepository repository;

    @Autowired
    public void setRepository(InstituteRepository repository) {
        this.repository = repository;
    }
}
