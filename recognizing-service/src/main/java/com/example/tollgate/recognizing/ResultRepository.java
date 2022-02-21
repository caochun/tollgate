package com.example.tollgate.recognizing;

import com.example.tollgate.model.Tolling;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ResultRepository {

    private Map<String, RecognizingResult> results = new HashMap<>();

    public Collection<RecognizingResult> getAllResults() {
        return results.values();
    }

    public void saveResult(Tolling tolling) {
        this.results.put(tolling.getId(), new RecognizingResult(tolling, false));
    }

    public void saveResult(RecognizingResult recognizingResult) {
        this.results.put(recognizingResult.getTolling().getId(), recognizingResult);
    }

    public RecognizingResult getResult(Tolling tolling) {
        return this.results.get(tolling.getId());
    }

    public RecognizingResult getResultById(String tollingId) {
        return this.results.get(tollingId);
    }

    public RecognizingResult setConfirmResult(Tolling tolling, boolean confirm) {
        RecognizingResult r = this.getResult(tolling);
        r.setConfirmed(confirm);
        this.saveResult(r);
        return r;
    }

    public RecognizingResult setConfirmResultById(String tollingId, boolean confirm) {
        RecognizingResult r = this.getResultById(tollingId);
        r.setConfirmed(confirm);
        this.saveResult(r);
        return r;
    }


}
