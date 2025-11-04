package imt.archi.bfb.infra.db.clients.repositories.mappers;


import imt.archi.bfb.core.clients.model.Client;
import imt.archi.bfb.infra.db.clients.repositories.entity.ClientEntity;
import imt.archi.bfb.infra.db.common.model.mappers.AbstractDbMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ClientBddMapper extends AbstractDbMapper<Client, ClientEntity> {

    @Override
    public Client from(final ClientEntity input) {
        return Client.builder()
                .id(UUID.fromString(input.getId()))
                .name(input.getName())
                .surname(input.getSurname())
                .birthDate(input.getBirthDate())
                .address(input.getAddress())
                .driverLicenseNumber(input.getDriverLicenseNumber())
                .build();
    }

    @Override
    public ClientEntity to(final Client object) {
        return ClientEntity.builder()
                .id(object.getId().toString())
                .name(object.getName())
                .surname(object.getSurname())
                .birthDate(object.getBirthDate())
                .address(object.getAddress())
                .driverLicenseNumber(object.getDriverLicenseNumber())
                .build();
    }
}

