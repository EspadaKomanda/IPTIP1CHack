package ru.espada.ep.iptip.university.institute.major;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MajorServiceImpl implements MajorService {
    private MajorRepository repository;

    @Autowired
    public void setRepository(MajorRepository repository) {
        this.repository = repository;
    }
}
