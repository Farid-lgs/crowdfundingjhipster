package fr.crowdfunding.jhipster.service.mapper;

import fr.crowdfunding.jhipster.domain.*;
import fr.crowdfunding.jhipster.service.dto.CreditCardDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CreditCard} and its DTO {@link CreditCardDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserInfosMapper.class })
public interface CreditCardMapper extends EntityMapper<CreditCardDTO, CreditCard> {
    @Mapping(target = "userInfos", source = "userInfos", qualifiedByName = "id")
    CreditCardDTO toDto(CreditCard s);
}
