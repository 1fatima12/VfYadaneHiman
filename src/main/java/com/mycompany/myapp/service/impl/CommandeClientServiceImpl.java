package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.CommandeClient;
import com.mycompany.myapp.repository.CommandeClientRepository;
import com.mycompany.myapp.service.CommandeClientService;
import com.mycompany.myapp.service.dto.CommandeClientDTO;
import com.mycompany.myapp.service.mapper.CommandeClientMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CommandeClient}.
 */
@Service
@Transactional
public class CommandeClientServiceImpl implements CommandeClientService {

    private final Logger log = LoggerFactory.getLogger(CommandeClientServiceImpl.class);

    private final CommandeClientRepository commandeClientRepository;

    private final CommandeClientMapper commandeClientMapper;

    public CommandeClientServiceImpl(CommandeClientRepository commandeClientRepository, CommandeClientMapper commandeClientMapper) {
        this.commandeClientRepository = commandeClientRepository;
        this.commandeClientMapper = commandeClientMapper;
    }

    @Override
    public CommandeClientDTO save(CommandeClientDTO commandeClientDTO) {
        log.debug("Request to save CommandeClient : {}", commandeClientDTO);
        CommandeClient commandeClient = commandeClientMapper.toEntity(commandeClientDTO);
        commandeClient = commandeClientRepository.save(commandeClient);
        return commandeClientMapper.toDto(commandeClient);
    }

    @Override
    public CommandeClientDTO update(CommandeClientDTO commandeClientDTO) {
        log.debug("Request to save CommandeClient : {}", commandeClientDTO);
        CommandeClient commandeClient = commandeClientMapper.toEntity(commandeClientDTO);
        commandeClient = commandeClientRepository.save(commandeClient);
        return commandeClientMapper.toDto(commandeClient);
    }

    @Override
    public Optional<CommandeClientDTO> partialUpdate(CommandeClientDTO commandeClientDTO) {
        log.debug("Request to partially update CommandeClient : {}", commandeClientDTO);

        return commandeClientRepository
            .findById(commandeClientDTO.getId())
            .map(existingCommandeClient -> {
                commandeClientMapper.partialUpdate(existingCommandeClient, commandeClientDTO);

                return existingCommandeClient;
            })
            .map(commandeClientRepository::save)
            .map(commandeClientMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommandeClientDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommandeClients");
        return commandeClientRepository.findAll(pageable).map(commandeClientMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CommandeClientDTO> findOne(Long id) {
        log.debug("Request to get CommandeClient : {}", id);
        return commandeClientRepository.findById(id).map(commandeClientMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CommandeClient : {}", id);
        commandeClientRepository.deleteById(id);
    }
}
