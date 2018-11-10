package pl.darczuk.warehouse.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String surename;
    private Role role;



}
