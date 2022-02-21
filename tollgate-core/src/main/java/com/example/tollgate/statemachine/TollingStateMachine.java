package com.example.tollgate.statemachine;

import com.example.tollgate.model.Tolling;
import com.example.tollgate.serialization.TollingStateMachineSerializer;
import com.example.tollgate.statemachine.SilentStateMachine;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.scxml2.model.ModelException;

@JsonSerialize(using = TollingStateMachineSerializer.class)
public class TollingStateMachine extends SilentStateMachine {

    public static final String SCXML_MODEL = "tolling.xml";

    public TollingStateMachine(Tolling tolling) throws ModelException {
        super(Tolling.class.getClassLoader().getResource(SCXML_MODEL));
        this.tolling = tolling;
    }

    @Getter
    @Setter
    private Tolling tolling;

}
