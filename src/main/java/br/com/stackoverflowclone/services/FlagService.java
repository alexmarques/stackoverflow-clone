package br.com.stackoverflowclone.services;

import br.com.stackoverflowclone.model.Flag;
import br.com.stackoverflowclone.repositories.FlagRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FlagService {

    private final FlagRepository flagRepository;

    public FlagService(FlagRepository flagRepository) {
        this.flagRepository = flagRepository;
    }

    public Flag insert(String description) {
        Flag flag = new Flag();
        flag.setUpdatedAt(LocalDateTime.now());
        flag.setCreatedAt(LocalDateTime.now());
        flag.setDescription(description);
        flag.setEnabled(true);
        return this.flagRepository.save(flag);
    }

}
