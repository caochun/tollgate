package com.example.tollgate.recognizing;

import com.example.tollgate.model.TollgateEntity;
import com.example.tollgate.model.Tolling;
import com.example.tollgate.model.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecognizingResult extends TollgateEntity {
    private Tolling tolling;
    private boolean confirmed;
}
