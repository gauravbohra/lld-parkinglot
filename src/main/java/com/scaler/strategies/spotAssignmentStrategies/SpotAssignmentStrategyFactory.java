package com.scaler.strategies.spotAssignmentStrategies;

import com.scaler.models.SpotAssignmentStrategyType;
import com.scaler.repositories.ParkingLotRepository;

public class SpotAssignmentStrategyFactory {

    public static SpotAssignmentStrategy getSpotAssignmentStrategyForType
            (SpotAssignmentStrategyType spotAssignmentStrategyType, ParkingLotRepository parkingLotRepository){

        if (spotAssignmentStrategyType.equals(SpotAssignmentStrategyType.RANDOM)){
            return new RandomSpotAssignmentStrategy(parkingLotRepository);
        }
        return null;
    }
}
