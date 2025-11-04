package imt.archi.bfb.core.clients;

import imt.archi.bfb.core.clients.model.Client;
import imt.archi.bfb.core.clients.validators.ClientAlreadyExistValidatorStep;
import imt.archi.bfb.core.clients.validators.NumberDriverLicenceAlreadyExistValidatorStep;
import imt.archi.bfb.core.common.validators.ConstraintValidatorStep;
import imt.archi.bfb.infra.db.clients.ClientBddService;
import org.springframework.stereotype.Service;


@Service
public class ClientServiceValidator extends ClientService {
    public ClientServiceValidator(final ClientBddService clientBddService) {
        super(clientBddService);
    }

    public Client create(Client newClient) {
        new ConstraintValidatorStep<Client>()
                .linkWith(new ClientAlreadyExistValidatorStep(this.clientBddService))
                .linkWith(new NumberDriverLicenceAlreadyExistValidatorStep(this.clientBddService))
                .validate(newClient)
                .throwIfValid();
        return super.create(newClient);
    }

    public void update(final Client client) {
        new ConstraintValidatorStep<Client>()
                .linkWith(new ClientAlreadyExistValidatorStep(this.clientBddService))
                .linkWith(new NumberDriverLicenceAlreadyExistValidatorStep(this.clientBddService))
                .validate(client)
                .throwIfValid();
        super.update(client);
    }
}
