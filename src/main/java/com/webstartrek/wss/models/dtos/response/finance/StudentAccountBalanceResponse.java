package com.webstartrek.wss.models.dtos.response.finance;

import com.webstartrek.wss.models.dtos.response.student.StudentResponse;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentAccountBalanceResponse {
    private BigDecimal accountBalance;
    private StudentResponse studentResponse;
}
