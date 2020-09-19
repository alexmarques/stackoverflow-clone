package br.com.stackoverflowclone.repositories;

import br.com.stackoverflowclone.model.Flag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlagRepository extends JpaRepository<Flag, Long> {
}
