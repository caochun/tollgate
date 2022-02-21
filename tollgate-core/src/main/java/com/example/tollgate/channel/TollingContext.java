package com.example.tollgate.channel;

import com.example.tollgate.model.TollgateEntity;
import com.example.tollgate.model.Tolling;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TollingContext extends TollgateEntity {

    private String context;

    /**
     * true if context is a tolling process's current state,
     * otherwise it's a happening transition
     */
    private boolean isState = false;

    private Tolling tolling;


    private TollingContext(Tolling tolling, String context, boolean isState) {
        super();
        this.context = context;
        this.tolling = tolling;
        this.isState = isState;
    }

    public static TollingContext generateTollingState(Tolling tolling, String state) {
        return new TollingContext(tolling, state, true);
    }

    public static TollingContext generateTollingTransition(Tolling tolling, String transition) {
        return new TollingContext(tolling, transition, false);
    }

}
