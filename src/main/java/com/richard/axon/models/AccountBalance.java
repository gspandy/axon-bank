package com.richard.axon.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created on 2/10/2017.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AccountBalance {
    @Id
    private String accountId;
    private int balance;
}
