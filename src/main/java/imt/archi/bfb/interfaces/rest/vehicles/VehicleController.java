package imt.archi.bfb.interfaces.rest.vehicles;

import imt.archi.bfb.core.vehicles.VehiclesService;
import imt.archi.bfb.core.vehicles.model.Vehicle;
import imt.archi.bfb.interfaces.rest.vehicles.model.input.VehicleInput;
import imt.archi.bfb.interfaces.rest.vehicles.model.output.VehicleOutput;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("api/vehicles")
public class VehicleController {
    private final VehiclesService vehiclesService;

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<VehicleOutput> getVehicles() {
        return Objects.requireNonNullElse(vehiclesService.findAll(), Collections.<Vehicle>emptySet()).stream()
                .map(VehicleOutput::from)
                .collect(Collectors.toSet());
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public VehicleOutput create(@RequestBody final VehicleInput input) {
        return VehicleOutput.from(
                vehiclesService.create(
                        VehicleInput.convert(input)
                )
        );
    }
}
