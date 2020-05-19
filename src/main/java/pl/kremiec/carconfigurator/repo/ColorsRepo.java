package pl.kremiec.carconfigurator.repo;

import org.springframework.data.repository.CrudRepository;
import pl.kremiec.carconfigurator.objects.Colors;

public interface ColorsRepo extends CrudRepository<Colors, Integer> {
}
