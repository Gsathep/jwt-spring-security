package completeJwt.Mapper;

import ch.qos.logback.core.model.ComponentModel;
import completeJwt.Domain.RefreshToken;
import completeJwt.Service.DTO.RefreshTokenDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RefreshTokenMapper extends EntityMapper<RefreshTokenDTO, RefreshToken> {
}
