package completeJwt.Mapper;

import completeJwt.Domain.User;
import completeJwt.Service.DTO.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMappper extends EntityMapper<UserDTO, User>{
}
