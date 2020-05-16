package pl.kremiec.carconfigurator.repo;

import org.springframework.data.repository.CrudRepository;
import pl.kremiec.carconfigurator.objects.Engines;

public interface EnginesRepo extends CrudRepository<Engines, Integer> {
}
