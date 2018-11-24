package pl.darczuk.warehouse.controller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.darczuk.warehouse.entity.User;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {
}
