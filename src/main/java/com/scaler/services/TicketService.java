package com.scaler.services;

import com.scaler.exceptions.GateNotFoundException;
import com.scaler.exceptions.InvalidEntryGateException;
import com.scaler.exceptions.ParkingSpotNotAvailableException;
import com.scaler.models.*;
import com.scaler.repositories.GateRepository;
import com.scaler.repositories.ParkingLotRepository;
import com.scaler.repositories.VehicleRepository;
import com.scaler.strategies.spotAssignmentStrategies.RandomSpotAssignmentStrategy;
import com.scaler.strategies.spotAssignmentStrategies.SpotAssignmentStrategy;
import com.scaler.strategies.spotAssignmentStrategies.SpotAssignmentStrategyFactory;

import java.util.Optional;

public class TicketService {
    private GateRepository gateRepository;
    private ParkingLotRepository parkingLotRepository;
    private VehicleRepository vehicleRepository;

    public TicketService(GateRepository gateRepository, ParkingLotRepository parkingLotRepository,
                VehicleRepository vehicleRepository){
        this.gateRepository = gateRepository;
        this.parkingLotRepository = parkingLotRepository;
        this.vehicleRepository = vehicleRepository;
    }
    public Ticket issueTicket(Long gateID, String vehicleNumber,
                              String vehicleOwnerName, VehicleType vehicleType){

        Optional<Gate> optionalGate= gateRepository.findGateByID(gateID);
        if(optionalGate.isEmpty()){
            throw new GateNotFoundException();
        }
        //Check whether gate is valid for entry
        Gate gate = optionalGate.get();
        if(!gate.getGateType().equals(GateType.ENTRY)){
            throw new InvalidEntryGateException();
        }
        //Save the gate and operator details in ticket
        Ticket ticket = new Ticket();
        ticket.setEntryGate(gate);
        ticket.setGeneratedBy(gate.getOperator());


        Vehicle savedVehicle;
        //Check whether Vehicle with Vehicle number is present in DB
        Optional<Vehicle> vehicleOptional = vehicleRepository.findVehicleByVehicleNumber(vehicleNumber);
        if(vehicleOptional.isEmpty()){
            //Save the vehicle with number in DB if not present
            Vehicle vehicle = new Vehicle();
            vehicle.setVehicleNumber(vehicleNumber);
            vehicle.setOwnerName(vehicleOwnerName);
            vehicle.setVehicleType(vehicleType);
            savedVehicle = vehicleRepository.saveVehicle(vehicle);
        }
        else {
            savedVehicle = vehicleOptional.get();
        }
        //Save the vehicle details in ticket
        ticket.setVehicle(savedVehicle);

        //Find the parking lot with the gate and get the parking lot spotAssignmentStrategy
        ParkingLot parkingLot = parkingLotRepository.findParkingLotWithGate(gate);

        //Get the spotAssignment strategy for the parking lot
        SpotAssignmentStrategy spotAssignmentStrategy = SpotAssignmentStrategyFactory
                .getSpotAssignmentStrategyForType(parkingLot.getSpotAssignmentStrategyType(), parkingLotRepository);

        Optional<ParkingSpot> parkingSpotOptional = spotAssignmentStrategy.getSpot(parkingLot, vehicleType);
        if(parkingSpotOptional.isEmpty()){
            throw new ParkingSpotNotAvailableException();
        }

        parkingSpotOptional.get().setParkingSpotStatus(ParkingSpotStatus.FILLED);

        ticket.setAssignedSpot(parkingSpotOptional.get());
        ticket.setNumber("Ticket"+ticket.getId());

        return ticket;
    }
}
