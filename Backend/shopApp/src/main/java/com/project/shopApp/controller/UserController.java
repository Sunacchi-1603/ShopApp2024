package com.project.shopApp.controller;

import com.project.shopApp.dtos.UserDTO;
import com.project.shopApp.dtos.UserLoginDTO;
import com.project.shopApp.models.User;
import com.project.shopApp.responses.LoginResponse;
import com.project.shopApp.responses.RegisterResponse;
import com.project.shopApp.services.UserService;
import com.project.shopApp.utils.MessageKeys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

//    private final MessageSource messageSource;
//    private final LocaleResolver localeResolver;
    private final com.project.shopApp.components.LocalizationUtils localizationUtils;
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> createUser(@Valid @RequestBody UserDTO userDTO,
                                        BindingResult result) {
        RegisterResponse registerResponse = new RegisterResponse();
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream().map(FieldError::getDefaultMessage).toList();
                registerResponse.setMesage(errorMessages.toString());
                return ResponseEntity.badRequest().body(registerResponse);
            }
            if(!userDTO.getPassword().equals(userDTO.getRetypePassword())){
                registerResponse.setMesage(localizationUtils.getLocalizedMessage(MessageKeys.PASSWORD_NOT_MATCH));
                return ResponseEntity.badRequest().body(registerResponse);
            }
            User newUser = userService.createUser(userDTO);
            return ResponseEntity.ok(
                    RegisterResponse.builder()
                            .mesage(localizationUtils.getLocalizedMessage(MessageKeys.REGISTER_SUCCESSFULLY))
                            .user(newUser)
                            .build()
            );
        } catch (Exception e) {
            registerResponse.setMesage(e.getMessage());
            return ResponseEntity.badRequest().body(registerResponse);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO,
                                                   BindingResult result,
                                                   HttpServletRequest request) {
        try{
            String token = userService.login(userLoginDTO.getPhoneNumber(),userLoginDTO.getPassword());
//            Locale locale = localeResolver.resolveLocale(request);
            return ResponseEntity.ok(LoginResponse.builder()
                            .mesage(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_SUCCESSFULLY))
                            .token(token)
                    .build());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(LoginResponse.builder()
                            .mesage(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_FAILED,e.getMessage()))
                            .build());
        }
    }
}
