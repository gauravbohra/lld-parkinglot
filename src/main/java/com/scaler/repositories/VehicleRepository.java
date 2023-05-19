package com.scaler.repositories;

import com.scaler.models.Vehicle;
import com.scaler.models.VehicleType;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class VehicleRepository {
    private Map<Long, Vehicle> vehicles = new TreeMap<>();
    int prevVehicleID =0;

    public Optional<Vehicle> findVehicleByVehicleNumber(String vehicleNumber){
        for (Vehicle vehicle: vehicles.values()){
            if (vehicle.getVehicleNumber().equalsIgnoreCase(vehicleNumber)){
                return Optional.of(vehicle);
            }
        }
        return Optional.empty();
    }

    public Vehicle saveVehicle(Vehicle vehicle){
        prevVehicleID+=1;
        vehicle.setId((long) prevVehicleID);
        vehicles.put((long) prevVehicleID, vehicle);
        return vehicle;
    }
}
