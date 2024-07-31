package com.aryak.db.domain;

public record Student(

        long id,
        String name,
        String address
) {

    public Student() {
        this(0l, "","");
    }

    public Student(long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }
}
