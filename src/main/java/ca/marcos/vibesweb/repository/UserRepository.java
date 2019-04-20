package ca.marcos.vibesweb.repository;

import ca.marcos.vibesweb.repository.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long > {
    User findByEmail(String email);
}