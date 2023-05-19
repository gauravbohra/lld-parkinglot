package com.scaler.strategies.spotAssignmentStrategies;

import com.scaler.models.*;

import java.util.Optional;

public interface SpotAssignmentStrategy {

    Optional<ParkingSpot> getSpot(ParkingLot parkingLot, VehicleType vehicleType);
}
