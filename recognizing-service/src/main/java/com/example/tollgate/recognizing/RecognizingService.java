package com.example.tollgate.recognizing;

import com.example.tollgate.channel.TollingContext;
import com.example.tollgate.model.TollgateService;
import com.example.tollgate.model.Tolling;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecognizingService extends TollgateService {

    private ResultRepository resultRepository;

    @Autowired
    public void setResultRepository(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    public Tolling recognize(Tolling tolling) {
        tolling.getVehicle().setRandomPlate();
        resultRepository.saveResult(tolling);
        return tolling;
    }

    public List<Tolling> getUnconfirmedTollings() {
        return this.resultRepository.getAllResults().stream()
                .filter(r -> !r.isConfirmed())
                .map(r -> r.getTolling())
                .collect(Collectors.toList());
    }

    public void confirm(Tolling tolling) {
        this.resultRepository.setConfirmResult(tolling, true);
        this.sendTollingTransition(tolling, "recognition_successes");
    }

    public void confirm(String vehicleId) {
        RecognizingResult r = this.resultRepository.getResultById(vehicleId);
        this.confirm(r.getTolling());
    }

    public void unconfirm(Tolling tolling) {
        this.resultRepository.setConfirmResult(tolling, false);
        this.sendTollingTransition(tolling, "recognition_fails");
    }

    public void unconfirm(String tollingId) {
        RecognizingResult r = this.resultRepository.getResultById(tollingId);
        this.unconfirm(r.getTolling());
    }

    @Override
    public void accept(TollingContext context) {

        if (context.isState()) { //actually it must be
            LogFactory.getLog(RecognizingService.class).info("handling vehicle " + context.getTolling() + " at state " + context.getContext());
            // currently the recognizing is executed automatically
            // you can just save the received tolling object and execute recognition manually
            this.recognize(context.getTolling());
        } else {
            //otherwise, just ignore
        }

    }
}
