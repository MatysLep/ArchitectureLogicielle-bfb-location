package imt.archi.bfb.interfaces.event.vehicles;

import imt.archi.bfb.core.contracts.ContractsService;
import imt.archi.bfb.infra.event.vehicles.model.MouvementVehicleEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MouvementVehicleEventListener implements ApplicationListener<MouvementVehicleEvent> {

    private final ContractsService contractsService;

    @Async
    @Override
    public void onApplicationEvent(final MouvementVehicleEvent event) {
        contractsService.cancelContracts(event.getRegistration());
    }
}
