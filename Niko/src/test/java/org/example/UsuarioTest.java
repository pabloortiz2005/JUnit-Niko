package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

        @Test
        void testGetDni() {
            Usuario usuario = new Usuario("12345678", 100.0);
            assertEquals("12345678", usuario.getDni());
        }

        @Test
        void testSetDni() {
            Usuario usuario = new Usuario("12345678", 100.0);
            usuario.setDni("87654321");
            assertEquals("87654321", usuario.getDni());
        }

        @Test
        void testGetSaldo() {
            Usuario usuario = new Usuario("12345678", 100.0);
            assertEquals(100.0, usuario.getSaldo());
        }

        @Test
        void testSetSaldo() {
            Usuario usuario = new Usuario("12345678", 100.0);
            usuario.setSaldo(200.0);
            assertEquals(200.0, usuario.getSaldo());
        }



    @Test
    void testValidarDni() {

        String validDni = "12345678";
        String invalidDni = "abcd1234";


        assertEquals(true, Usuario.validarDni(validDni));

    }


    @Test
        void testSumarSaldo() {
            Usuario usuario = new Usuario("12345678", 100.0);
            usuario.setSaldo(usuario.getSaldo() + 50.0);
            assertEquals(150.0, usuario.getSaldo());
        }

        @Test
        void testRestarSaldo() {
            Usuario usuario = new Usuario("12345678", 100.0);
            usuario.setSaldo(usuario.getSaldo() - 50.0);
            assertEquals(50.0, usuario.getSaldo());
        }

        @Test
        void testRestarSaldo_MayorQueSaldo() {
            Usuario usuario = new Usuario("12345678", 100.0);
            double montoARestar = 150.0;

            if (montoARestar > usuario.getSaldo()) {
                System.out.println("No se puede restar un monto mayor al saldo actual.");
                assertEquals(100.0, usuario.getSaldo());
            }
        }
    }

