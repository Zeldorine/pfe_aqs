/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ets.pfe.aqs.modele;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Zeldorine
 */
@Entity
@Table(name = "\"Test\"")
public class TestDao {

    @Id
    @Column(name = "test")
    private int test;

    public TestDao() {
    }

    public TestDao(int test) {
        this.test = test;
    }

    public int getTest() {
        return test;
    }

    public int setTest(int test) {
        return this.test = test;
    }

    @Override
    public String toString() {
        return "Id: " + test;
    }
}
