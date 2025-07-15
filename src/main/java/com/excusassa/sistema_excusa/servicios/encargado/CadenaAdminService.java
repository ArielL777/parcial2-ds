package com.excusassa.sistema_excusa.servicios.encargado;

import com.excusassa.sistema_excusa.dominio.modelo.empleado.GerenteRRHH;
import com.excusassa.sistema_excusa.dominio.modelo.empleado.Recepcionista;
import com.excusassa.sistema_excusa.dominio.modelo.empleado.SupervisorArea;
import com.excusassa.sistema_excusa.infraestructura.email.EmailSender;
import com.excusassa.sistema_excusa.interfaz.dto.EncargadoDTO;
import com.excusassa.sistema_excusa.interfaz.dto.EncargadoRequestDTO;
import com.excusassa.sistema_excusa.servicios.modotrabajo.ModoTrabajo;
import com.excusassa.sistema_excusa.servicios.prontuario.ProntuarioService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CadenaAdminService {

    private final CadenaManager cadenaManager;
    private final EmailSender emailSender;
    private final ProntuarioService prontuarioService;

    public CadenaAdminService(CadenaManager cadenaManager, EmailSender emailSender, ProntuarioService prontuarioService) {
        this.cadenaManager = cadenaManager;
        this.emailSender = emailSender;
        this.prontuarioService = prontuarioService;
    }

    public List<EncargadoDTO> obtenerConfiguracionCadena() {
        return cadenaManager.getEncargadosActuales().stream()
                .filter(encargado -> encargado instanceof EncargadoAbstracto)
                .map(encargado -> {
                    EncargadoAbstracto encargadoAbs = (EncargadoAbstracto) encargado;
                    return EncargadoDTO.fromEntity(encargadoAbs, encargadoAbs.getModoTrabajo());
                })
                .collect(Collectors.toList());
    }

    public boolean cambiarModoDeEncargado(Integer legajo, ModoTrabajo nuevoModo) {
        for (IEncargado encargado : cadenaManager.getEncargadosActuales()) {
            if (encargado instanceof EncargadoAbstracto) {
                EncargadoAbstracto encargadoAbs = (EncargadoAbstracto) encargado;
                if (encargadoAbs.getNroLegajo() == legajo) {
                    encargadoAbs.setModoTrabajo(nuevoModo);
                    return true;
                }
            }
        }
        return false;
    }

    public void agregarNuevoEncargado(EncargadoRequestDTO dto) {
        IEncargado nuevoEncargado = crearEncargadoDesdeDTO(dto);
        cadenaManager.agregarEncargado(nuevoEncargado);
    }

    private IEncargado crearEncargadoDesdeDTO(EncargadoRequestDTO dto) {
        ModoTrabajo modo = ModoTrabajo.NORMAL;

        switch (dto.tipoExcusasQueManeja().toUpperCase()) {
            case "TRIVIAL":
                return new Recepcionista(dto.nombre(), dto.email(), dto.nroLegajo(), modo, emailSender);
            case "MODERADA":
                return new SupervisorArea(dto.nombre(), dto.email(), dto.nroLegajo(), modo, emailSender);
            case "COMPLEJA":
                return new GerenteRRHH(dto.nombre(), dto.email(), dto.nroLegajo(), modo, emailSender);
            default:
                throw new IllegalArgumentException("El tipo de excusa '" + dto.tipoExcusasQueManeja() + "' no es válido para crear un encargado.");
        }
    }
}