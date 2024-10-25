package org.iesvdm.tienda.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Producto {

    @Id
    private int codigo;
    
    private String nombre;

    private double precio;

    @ManyToOne
    @JoinColumn(name = "codigo_fabricante", referencedColumnName = "codigo")
    private Fabricante fabricante;

    public static final double FACT = 1.08;

}    
