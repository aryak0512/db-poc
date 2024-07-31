package com.aryak.db.domain;

import lombok.Builder;

@Builder
public record Product(
        String name,
        int id,
        double price
) {

    public Product() {
        this("", 0, 0.0);
    }

}
