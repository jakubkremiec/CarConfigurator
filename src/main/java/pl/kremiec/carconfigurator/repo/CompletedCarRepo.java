package pl.kremiec.carconfigurator.repo;

import org.springframework.data.repository.CrudRepository;
import pl.kremiec.carconfigurator.objects.CompletedCar;

public interface CompletedCarRepo extends CrudRepository<CompletedCar, Integer> {
}
