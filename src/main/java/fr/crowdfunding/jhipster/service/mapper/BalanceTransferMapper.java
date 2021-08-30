package fr.crowdfunding.jhipster.service.mapper;

import fr.crowdfunding.jhipster.domain.*;
import fr.crowdfunding.jhipster.service.dto.BalanceTransferDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BalanceTransfer} and its DTO {@link BalanceTransferDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserInfosMapper.class, ProjectMapper.class })
public interface BalanceTransferMapper extends EntityMapper<BalanceTransferDTO, BalanceTransfer> {
    @Mapping(target = "userInfos", source = "userInfos", qualifiedByName = "id")
    @Mapping(target = "project", source = "project", qualifiedByName = "id")
    BalanceTransferDTO toDto(BalanceTransfer s);
}
