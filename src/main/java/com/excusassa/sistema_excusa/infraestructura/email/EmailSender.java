package com.excusassa.sistema_excusa.infraestructura.email;

public interface EmailSender {
    void enviarEmail(String destino, String origen, String asunto, String cuerpo);
}
