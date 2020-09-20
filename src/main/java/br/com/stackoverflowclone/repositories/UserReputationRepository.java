package br.com.stackoverflowclone.repositories;

import br.com.stackoverflowclone.model.UserReputation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReputationRepository extends JpaRepository<UserReputation, Long> {
    UserReputation findByUserId(Long userId);
}
