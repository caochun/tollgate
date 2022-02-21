package com.example.tollgate.tolling;

import com.example.tollgate.model.Tolling;
import com.example.tollgate.statemachine.TollingStateMachine;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class TollingRepository {

    private Map<String, TollingStateMachine> tollingMap;

    public TollingRepository() {
        this.tollingMap = new HashMap<>();
    }

    public TollingStateMachine findTollingStateMachineById(String tollingId) {
        return tollingMap.get(tollingId);
    }

    public void saveTollingStateMachine(TollingStateMachine tollingStateMachine) {
        tollingMap.put(tollingStateMachine.getTolling().getId(), tollingStateMachine);
    }

    public void saveTolling(Tolling tolling){
        TollingStateMachine tollingStateMachine = tollingMap.get(tolling.getId());
        tollingStateMachine.setTolling(tolling);
        tollingMap.put(tolling.getId(), tollingStateMachine);
    }

    public List<TollingStateMachine> findAll() {
        return tollingMap.values().stream().collect(Collectors.toList());
    }

    public int count() {
        return this.tollingMap.size();
    }

    public void deleteTollingStateMachineById(String tollingId) {
        this.tollingMap.remove(tollingId);
    }

}
