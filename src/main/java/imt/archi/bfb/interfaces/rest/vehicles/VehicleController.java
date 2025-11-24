package imt.archi.bfb.interfaces.rest.vehicles;

import imt.archi.bfb.core.vehicles.VehiclesServiceValidator;
import imt.archi.bfb.core.vehicles.model.Vehicle;
import imt.archi.bfb.interfaces.rest.common.exception.NotFoundException;
import imt.archi.bfb.interfaces.rest.vehicles.model.input.VehicleInput;
import imt.archi.bfb.interfaces.rest.vehicles.model.input.VehicleUpdateInput;
import imt.archi.bfb.interfaces.rest.vehicles.model.output.VehicleOutput;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("api/vehicles")
@CrossOrigin(origins = "http://localhost:5173")
public class VehicleController {
    private final VehiclesServiceValidator vehiclesService;

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<VehicleOutput> getVehicles() {
        return Objects.requireNonNullElse(vehiclesService.findAll(), Collections.<Vehicle>emptySet()).stream()
                .map(VehicleOutput::from)
                .collect(Collectors.toSet());
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public VehicleOutput create(@RequestBody @Valid final VehicleInput input) {
        return VehicleOutput.from(
                vehiclesService.create(
                        VehicleInput.convert(input)
                )
        );
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/{registration}", produces = MediaType.APPLICATION_JSON_VALUE)
    public VehicleOutput getOne(@PathVariable final String registration) {
        return vehiclesService.getOne(registration).map(VehicleOutput::from).orElseThrow(() ->
                new NotFoundException(String.format("Véhicule %s non trouvé", registration)));
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{registration}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable("registration") final String registration, @RequestBody @Valid final VehicleUpdateInput vehicle) {
        vehiclesService.update(
            vehiclesService.getOne(registration)
                .map(alreadySaved -> VehicleUpdateInput.from(vehicle, alreadySaved))
                .orElseThrow(() -> new NotFoundException(String.format("Véhicule %s non trouvé", registration)))
        );
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{registration}")
    public void delete(@PathVariable final String registration) {
        vehiclesService.delete(registration);
    }
}
