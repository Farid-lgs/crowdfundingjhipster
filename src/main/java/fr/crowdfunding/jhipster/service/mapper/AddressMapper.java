package fr.crowdfunding.jhipster.service.mapper;

import fr.crowdfunding.jhipster.domain.*;
import fr.crowdfunding.jhipster.service.dto.AddressDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserInfosMapper.class, CountryMapper.class })
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {
    @Mapping(target = "userInfos", source = "userInfos", qualifiedByName = "id")
    @Mapping(target = "country", source = "country", qualifiedByName = "id")
    AddressDTO toDto(Address s);
}
