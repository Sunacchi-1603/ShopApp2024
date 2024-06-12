package com.project.shopApp.services;

import com.project.shopApp.component.JwtTokenUtil;
import com.project.shopApp.dtos.UserDTO;
import com.project.shopApp.exeptions.DataNotFoundException;
import com.project.shopApp.exeptions.InvalidParamException;
import com.project.shopApp.exeptions.PermissionDenyException;
import com.project.shopApp.models.Role;
import com.project.shopApp.models.User;
import com.project.shopApp.repositories.RoleRepository;
import com.project.shopApp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    @Override
    public User createUser(UserDTO userDTO) throws Exception {
        String phoneNumber = userDTO.getPhoneNumber();
        if(userRepository.existsByPhoneNumber(phoneNumber)){
            throw new DataIntegrityViolationException(("phone da duoc su dung"));
        }
        Role role = roleRepository.findById(Long.valueOf(userDTO.getRoleId())).orElseThrow(()->new DataNotFoundException("Role not found"));
        if(role.getName().toUpperCase().equals(Role.ADMIN)){
            throw new PermissionDenyException("cannot register an admin account");
        }
        //convert
        User newUser = User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(phoneNumber)
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .dateofBirth(userDTO.getDateOfBirth())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .build();
        // câu này tương đương với câu lệnh SELECT * FROM roles WHERE id = ?;
        newUser.setRole(role);
        // kiểm tra nếu có accountId không yêu cầu password nếu không có 2 cái này mới yêu cầu mật khẩu
        if(userDTO.getGoogleAccountId() == 0 && userDTO.getFacebookAccountId() == 0){
            String password = userDTO.getPassword();
            String encodePassword = passwordEncoder.encode(password); // mã hoá mật khẩu
            // mã hoá
            newUser.setPassword(encodePassword);
        }
        return userRepository.save(newUser);
    }

    @Override
    public String login(String phoneNumber, String password) throws DataNotFoundException, InvalidParamException {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if(optionalUser.isEmpty()){
            throw new DataNotFoundException("Invalid username,password");
        }
        //return optionalUser.get(); // muốn trả về jwt token
        User existingUser = optionalUser.get();
        // check password
        if(existingUser.getGoogleAccountId() == 0 && existingUser.getFacebookAccountId() == 0){
            if(!passwordEncoder.matches(password,existingUser.getPassword())){
                throw new BadCredentialsException("wrong phone or pass");
            }
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                phoneNumber,password,
                existingUser.getAuthorities()
        );
        // authenticate with java spring secirity
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generrateToken(existingUser);
    }
}
