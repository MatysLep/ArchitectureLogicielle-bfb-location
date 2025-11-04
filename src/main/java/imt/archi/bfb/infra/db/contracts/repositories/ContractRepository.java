package imt.archi.bfb.infra.db.contracts.repositories;

import imt.archi.bfb.infra.db.contracts.repositories.entities.ContractEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContractRepository extends MongoRepository<ContractEntity, String> {
}
