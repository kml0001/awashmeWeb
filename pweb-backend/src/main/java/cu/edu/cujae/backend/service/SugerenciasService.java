package cu.edu.cujae.backend.service;

import java.util.List;

import cu.edu.cujae.backend.core.dto.SugerenciaDto;

public interface SugerenciasService {

    List<SugerenciaDto> getSugerencias();

    SugerenciaDto getSugerenciaById(int id);

    int createSugerencia(SugerenciaDto sugerencia);

    int updateSugerencia(int id, SugerenciaDto updatedSugerencia);

    int deleteSugerencia(int id);
}