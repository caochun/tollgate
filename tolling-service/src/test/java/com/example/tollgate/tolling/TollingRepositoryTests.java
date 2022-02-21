package com.example.tollgate.tolling;

import com.example.tollgate.model.Tolling;
import com.example.tollgate.statemachine.TollingStateMachine;
import org.apache.commons.scxml2.model.ModelException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TollingRepositoryTests {


    @Test
    public void testSaveAndFind() throws ModelException {
        TollingStateMachine t = new TollingStateMachine(new Tolling());

        TollingRepository repository = new TollingRepository();
        repository.saveTollingStateMachine(t);
        TollingStateMachine tt= repository.findTollingStateMachineById(t.getTolling().getId());

        assertEquals(tt, t);



    }
}
