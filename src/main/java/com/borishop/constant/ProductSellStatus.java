package com.borishop.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductSellStatus {
    WAIT("WAIT", "판매대기"),
    SELL("SELL", "판매개시"),
    SOLD_OUT("SOLD_OUT", "일시품절"),
    CLOSE("CLOSE", "판매종료");

    private final String key;
    private final String value;
}
