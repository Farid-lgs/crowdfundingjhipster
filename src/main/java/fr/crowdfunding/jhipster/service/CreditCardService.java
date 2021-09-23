package fr.crowdfunding.jhipster.service;

import fr.crowdfunding.jhipster.domain.CreditCard;
import fr.crowdfunding.jhipster.domain.UserInfos;
import fr.crowdfunding.jhipster.repository.CreditCardRepository;
import fr.crowdfunding.jhipster.repository.UserInfosRepository;
import fr.crowdfunding.jhipster.service.dto.CreditCardDTO;
import fr.crowdfunding.jhipster.service.mapper.CreditCardMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CreditCard}.
 */
@Service
@Transactional
public class CreditCardService {

    private final Logger log = LoggerFactory.getLogger(CreditCardService.class);

    private final CreditCardRepository creditCardRepository;

    private final CreditCardMapper creditCardMapper;

    private final UserInfosRepository userInfosRepository;

    public CreditCardService(CreditCardRepository creditCardRepository, CreditCardMapper creditCardMapper, UserInfosRepository userInfosRepository) {
        this.creditCardRepository = creditCardRepository;
        this.creditCardMapper = creditCardMapper;
        this.userInfosRepository = userInfosRepository;
    }

    /**
     * Save a creditCard.
     *
     * @param creditCardDTO the entity to save.
     * @return the persisted entity.
     */
    public CreditCardDTO save(CreditCardDTO creditCardDTO) {
        log.debug("Request to save CreditCard : {}", creditCardDTO);
        CreditCard creditCard = creditCardMapper.toEntity(creditCardDTO);
        Optional<UserInfos> userInfos = userInfosRepository.findById(creditCardDTO.getUserInfos().getId());
        creditCard.setUserInfos(userInfos.get());
        creditCard = creditCardRepository.save(creditCard);
        return creditCardMapper.toDto(creditCard);
    }

    /**
     * Partially update a creditCard.
     *
     * @param creditCardDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CreditCardDTO> partialUpdate(CreditCardDTO creditCardDTO) {
        log.debug("Request to partially update CreditCard : {}", creditCardDTO);

        return creditCardRepository
            .findById(creditCardDTO.getId())
            .map(
                existingCreditCard -> {
                    creditCardMapper.partialUpdate(existingCreditCard, creditCardDTO);

                    return existingCreditCard;
                }
            )
            .map(creditCardRepository::save)
            .map(creditCardMapper::toDto);
    }

    /**
     * Get all the creditCards.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CreditCardDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CreditCards");
        return creditCardRepository.findAll(pageable).map(creditCardMapper::toDto);
    }

    /**
     * Get one creditCard by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CreditCardDTO> findOne(Long id) {
        log.debug("Request to get CreditCard : {}", id);
        Optional<UserInfos> userInfos = userInfosRepository.findById(id);

        return creditCardRepository.findOneCreditCardByUserInfos(userInfos.get()).map(creditCardMapper::toDto);
    }

    /**
     * Delete the creditCard by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CreditCard : {}", id);
        creditCardRepository.deleteById(id);
    }
}
