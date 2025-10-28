package imt.archi.bfb.vehicles.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collation = "vehicles")
public class Vehicle {
    @Id
    public String registration;
    public String brand;
    public String model;
    public String motorization;
    public String color;
    public Date acquisitionDate;
    public StateVehicle state;
}
