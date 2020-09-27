package br.com.stackoverflowclone.services.impl;

import br.com.stackoverflowclone.model.Flag;
import br.com.stackoverflowclone.repositories.FlagRepository;
import br.com.stackoverflowclone.services.FlagService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FlagServiceImpl implements FlagService {

    private final FlagRepository flagRepository;

    public FlagServiceImpl(FlagRepository flagRepository) {
        this.flagRepository = flagRepository;
    }

    @Override
    public Flag insert(String description) {
        Flag flag = new Flag();
        flag.setUpdatedAt(LocalDateTime.now());
        flag.setCreatedAt(LocalDateTime.now());
        flag.setDescription(description);
        flag.setEnabled(true);
        return this.flagRepository.save(flag);
    }
}
