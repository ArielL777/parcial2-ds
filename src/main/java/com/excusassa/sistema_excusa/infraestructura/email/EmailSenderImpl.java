package com.excusassa.sistema_excusa.infraestructura.email;

import org.springframework.stereotype.Component;

@Component
public class EmailSenderImpl implements EmailSender {

    @Override
    public void enviarEmail(String destino, String origen, String asunto, String cuerpo) {
        System.out.println("Enviando email:");
        System.out.println("  De:      " + origen);
        System.out.println("  Para:    " + destino);
        System.out.println("  Asunto:  " + asunto);
        System.out.println("  Cuerpo:  " + cuerpo);
        System.out.println("-----------------------------");
    }
}
