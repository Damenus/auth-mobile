package pl.darczuk.warehouse.controller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.darczuk.warehouse.entity.Token;

@RepositoryRestResource
public interface TokenRepository extends JpaRepository<Token, String> {
}
