/*Created by Sinatra Gunda
  At 2:51 AM on 2/25/2020 */

package com.wese.weseaddons.errors.springexceptions;

import com.wese.weseaddons.errors.WeseErrorService;

public class LoanNotFoundException extends RuntimeException implements WeseErrorService {


    private long loanId ;

    public LoanNotFoundException(long loanId) {
        this.loanId = loanId;
    }

    @Override
    public String getMessage() {
        return String.format("Loan with id %d not found in database",loanId);
    }
}
