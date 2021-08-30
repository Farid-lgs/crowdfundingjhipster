package fr.crowdfunding.jhipster.service;

import fr.crowdfunding.jhipster.domain.BalanceTransfer;
import fr.crowdfunding.jhipster.repository.BalanceTransferRepository;
import fr.crowdfunding.jhipster.service.dto.BalanceTransferDTO;
import fr.crowdfunding.jhipster.service.mapper.BalanceTransferMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BalanceTransfer}.
 */
@Service
@Transactional
public class BalanceTransferService {

    private final Logger log = LoggerFactory.getLogger(BalanceTransferService.class);

    private final BalanceTransferRepository balanceTransferRepository;

    private final BalanceTransferMapper balanceTransferMapper;

    public BalanceTransferService(BalanceTransferRepository balanceTransferRepository, BalanceTransferMapper balanceTransferMapper) {
        this.balanceTransferRepository = balanceTransferRepository;
        this.balanceTransferMapper = balanceTransferMapper;
    }

    /**
     * Save a balanceTransfer.
     *
     * @param balanceTransferDTO the entity to save.
     * @return the persisted entity.
     */
    public BalanceTransferDTO save(BalanceTransferDTO balanceTransferDTO) {
        log.debug("Request to save BalanceTransfer : {}", balanceTransferDTO);
        BalanceTransfer balanceTransfer = balanceTransferMapper.toEntity(balanceTransferDTO);
        balanceTransfer = balanceTransferRepository.save(balanceTransfer);
        return balanceTransferMapper.toDto(balanceTransfer);
    }

    /**
     * Partially update a balanceTransfer.
     *
     * @param balanceTransferDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BalanceTransferDTO> partialUpdate(BalanceTransferDTO balanceTransferDTO) {
        log.debug("Request to partially update BalanceTransfer : {}", balanceTransferDTO);

        return balanceTransferRepository
            .findById(balanceTransferDTO.getId())
            .map(
                existingBalanceTransfer -> {
                    balanceTransferMapper.partialUpdate(existingBalanceTransfer, balanceTransferDTO);

                    return existingBalanceTransfer;
                }
            )
            .map(balanceTransferRepository::save)
            .map(balanceTransferMapper::toDto);
    }

    /**
     * Get all the balanceTransfers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BalanceTransferDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BalanceTransfers");
        return balanceTransferRepository.findAll(pageable).map(balanceTransferMapper::toDto);
    }

    /**
     * Get one balanceTransfer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BalanceTransferDTO> findOne(Long id) {
        log.debug("Request to get BalanceTransfer : {}", id);
        return balanceTransferRepository.findById(id).map(balanceTransferMapper::toDto);
    }

    /**
     * Delete the balanceTransfer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BalanceTransfer : {}", id);
        balanceTransferRepository.deleteById(id);
    }
}
