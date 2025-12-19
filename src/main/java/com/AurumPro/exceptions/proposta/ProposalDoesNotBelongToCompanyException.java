package com.AurumPro.exceptions.proposta;

import com.AurumPro.exceptions.UnprocessableEntityException;

public class ProposalDoesNotBelongToCompanyException extends UnprocessableEntityException {

    public ProposalDoesNotBelongToCompanyException(){
        super("Esta proposta não pertence à sua empresa");
    }
}
