package org.example.logitronapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario extends Persona {

    private String clave, rol;

    public Usuario(String nombre, String correo,String direccion, String clave, String rol, String genero) {
    }
}
