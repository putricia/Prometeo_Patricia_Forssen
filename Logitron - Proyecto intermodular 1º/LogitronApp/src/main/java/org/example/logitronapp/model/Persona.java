package org.example.logitronapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public abstract class Persona {
    private String nombre, correo, telefono, direccion, cp, ciudad;
}
