package org.example;

import org.example.ControlGastosIngresos;
import org.example.DataBase;
import org.example.Usuario;

public class Main {
    public static void main(String[] args) {
        System.out.println("Bienvenido al programa de control de gastos e ingresos.");
        DataBase.iniciarBaseDeDatos();

        Usuario usuario = null;
        while (usuario == null) {
            usuario = ControlGastosIngresos.Creariniciarusuario();
        }

        ControlGastosIngresos.inicioPrograma(usuario);
    }
}
