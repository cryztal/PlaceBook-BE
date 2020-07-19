package com.exadel.placebook.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "floors")
@Data
public class Floors {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "office_id")
    private Long officeId;

    @Column(name = "floor_configuration")
    private String floorConfiguration;

}
