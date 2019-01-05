package ca.usherbrooke.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.RollbackException;

import org.springframework.stereotype.Service;

@Service
public class AlienService {
	
	private final AlienRepository alienRepository;

	public AlienService(final AlienRepository alienRepository) {
		this.alienRepository = alienRepository;
	}

	public Long getId(Alien entity) {
		return entity.getId();
	}

	public AlienRepository getRepository() {
		return alienRepository;
	}
	
	public List<Alien> findAll() {
		Iterable<Alien> list = getRepository().findAll();
		List<Alien> arrayList = new ArrayList<>();
		
		for (Alien entity : list) {
			arrayList.add(entity);
		}
		
		return arrayList;
	}

	public Alien get(Long id) {
		Optional<Alien> entity = getRepository().findById(id);
		if (entity.isPresent()) {
			return entity.get();
		}
		return null;
	}
	
	public Alien get(String name) {
		Optional<Alien> entity = getRepository().findByName(name);
		if (entity.isPresent()) {
			return entity.get();
		}
		return null;
	}

	public Alien save(Alien entity) {
		Alien result;

		try {
			result = getRepository().save(entity);
			return result;
		} catch (RollbackException e) {
			throw new RuntimeException("Entity not found");
		}
	}

	public Alien update(Alien entity) {
		Alien result;

		if (getRepository().existsById(getId(entity))) {
			result = save(entity);
			return result;
		} else {
			throw new RuntimeException("Entity not found");
		}
	}

	public void delete(Long id) {
		if (getRepository().existsById(id)) {
			getRepository().deleteById(id);
		} else {
			throw new RuntimeException("Entity not found");
		}
	}
}
