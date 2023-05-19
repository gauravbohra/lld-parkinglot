package com.scaler.repositories;

import com.scaler.models.Gate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GateRepository {
    Map<Long,Gate> gateMap = new HashMap<Long, Gate>();

    public Optional<Gate> findGateByID(Long gateID){
        // database.execute(select * from gate);
        if(gateMap.containsKey(gateID)){
            return Optional.of(gateMap.get(gateID));
        }

        return Optional.empty();
    }

}
