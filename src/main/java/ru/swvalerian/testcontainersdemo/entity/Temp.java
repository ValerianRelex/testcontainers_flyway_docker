package ru.swvalerian.testcontainersdemo.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Temp implements Serializable {
    @Id
    private String id;
    @Column
    private String city;
    @Column
    private Double tempInC;
    @Column
    private Double tempInF; // TODO: значение лучше выcxитывать в сервисе, чем хранить в БД, чтоб не забивать данными

}
