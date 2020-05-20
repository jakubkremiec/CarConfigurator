package pl.kremiec.carconfigurator.repo;

import org.springframework.data.repository.CrudRepository;
import pl.kremiec.carconfigurator.objects.Models;

public interface CarModelsRepo extends CrudRepository<Models, Integer> {
}
