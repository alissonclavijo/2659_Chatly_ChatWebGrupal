package ec.edu.espe.chatws.chatwebsocketserver.repository;

import ec.edu.espe.chatws.chatwebsocketserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndPassword(String username, String password);
    List<User> findByUsernameIn(List<String> username);
}
