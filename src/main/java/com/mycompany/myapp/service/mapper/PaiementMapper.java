package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Paiement;
import com.mycompany.myapp.service.dto.PaiementDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Paiement} and its DTO {@link PaiementDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaiementMapper extends EntityMapper<PaiementDTO, Paiement> {}
