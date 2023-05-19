package com.scaler.repositories;

import com.scaler.models.Gate;
import com.scaler.models.ParkingLot;

import java.util.Map;
import java.util.TreeMap;

public class ParkingLotRepository {
    GateRepository gateRepository = new GateRepository();
    Map<Long, ParkingLot> parkingLots = new TreeMap<Long, ParkingLot>();

    public ParkingLot findParkingLotWithGate(Gate gate){
        for(ParkingLot parkingLot: parkingLots.values()){
            if(parkingLot.getGates().contains(gate)){
                return parkingLot;
            }
        }
        return null;
    }

}
