package br.com.stackoverflowclone.repositories;

import br.com.stackoverflowclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
