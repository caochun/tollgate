package com.example.tollgate.tolling;

import com.example.tollgate.channel.TollingContext;
import com.example.tollgate.model.TollgateService;
import com.example.tollgate.model.Tolling;
import com.example.tollgate.statemachine.TollingStateMachine;
import org.apache.commons.scxml2.SCXMLListener;
import org.apache.commons.scxml2.model.EnterableState;
import org.apache.commons.scxml2.model.ModelException;
import org.apache.commons.scxml2.model.Transition;
import org.apache.commons.scxml2.model.TransitionTarget;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TollingService extends TollgateService {

    private TollingRepository tollingRepository;

    @Autowired
    public void setTollingRepository(TollingRepository tollingRepository) {
        this.tollingRepository  = tollingRepository;
    }


    public void newTolling() {

        TollingStateMachine tollingStateMachine;

        try {
            tollingStateMachine = new TollingStateMachine(new Tolling());
        } catch (ModelException e) {
            e.printStackTrace();
            return;
        }

        tollingStateMachine.getEngine().addListener(tollingStateMachine.getEngine().getStateMachine(), new SCXMLListener() {
            @Override
            public void onEntry(EnterableState enterableState) {
                LoggerFactory.getLogger(TollingService.class).info(tollingStateMachine.getTolling() + " in state: " + enterableState.getId());
                TollingService.this.sendTollingState(tollingStateMachine.getTolling(), enterableState.getId());
            }

            @Override
            public void onExit(EnterableState enterableState) {

            }

            @Override
            public void onTransition(TransitionTarget transitionTarget, TransitionTarget transitionTarget1, Transition transition, String s) {
                LoggerFactory.getLogger(TollingService.class).info(tollingStateMachine.getTolling() + " transition fired: " + s);
            }
        });

        this.tollingRepository.saveTollingStateMachine(tollingStateMachine);

    }

    public boolean startTolling(Tolling tolling) {
        return this.startTolling(tolling.getId());
    }

    public boolean startTolling(String tollingId) {
        TollingStateMachine tollingStateMachine = tollingRepository.findTollingStateMachineById(tollingId);
        if (tollingStateMachine != null) {
            return tollingStateMachine.fireEvent("start");
        }
        return false;
    }

    public List<TollingStateMachine> tollings() {
        return this.tollingRepository.findAll();
    }

    private void handleTollingTransition(TollingContext tollingContext) {

        TollingStateMachine tollingStateMachine = tollingRepository.findTollingStateMachineById(tollingContext.getId());
        if (tollingStateMachine != null) {
            tollingStateMachine.fireEvent(tollingContext.getContext());

            if (tollingContext.getContext().equals("recognition_successes")) {
                tollingRepository.saveTolling(tollingContext.getTolling());
            } else {
                //TODO
            }
        }
    }


    @Override
    public void accept(TollingContext context) {
        if (!context.isState()) {
            handleTollingTransition(context);
        } else {
            //otherwise, just ignore
        }
    }

}
