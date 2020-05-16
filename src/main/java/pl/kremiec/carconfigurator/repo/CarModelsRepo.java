package pl.kremiec.carconfigurator.repo;

import org.springframework.data.repository.CrudRepository;
import pl.kremiec.carconfigurator.objects.CarModels;

public interface CarModelsRepo extends CrudRepository<CarModels, Integer> {
}
