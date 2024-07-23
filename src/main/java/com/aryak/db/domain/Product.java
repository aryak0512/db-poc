package com.aryak.db.domain;

import lombok.Builder;

@Builder
public record Product(
        String name,
        int id
) {

    public Product() {
        this("", 0);
    }

}
