package com.youth.manito.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResultSetType {

    MANITO_GIVER_RESULT("마니또 선물자 결과"),
    MANITO_VOTER_RESULT("마니또 투표자 결과")
    ;

    private final String description;
}
