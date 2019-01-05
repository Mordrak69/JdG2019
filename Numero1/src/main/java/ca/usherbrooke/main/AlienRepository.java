package ca.usherbrooke.main;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlienRepository extends JpaRepository<Alien, Long> {

	@Query("SELECT a FROM Alien a WHERE a.name = :name")
    public Optional<Alien> findByName(@Param("name") String lastName);
}
