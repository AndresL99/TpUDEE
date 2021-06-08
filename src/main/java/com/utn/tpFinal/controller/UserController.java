package com.utn.tpFinal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn.tpFinal.domain.User;
import com.utn.tpFinal.domain.dto.LoginRequestDTO;
import com.utn.tpFinal.domain.dto.LoginResponseDTO;
import com.utn.tpFinal.domain.dto.UserDTO;
import com.utn.tpFinal.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.utn.tpFinal.utils.Constants.JWT_SECRET;

@Slf4j
@RestController
@RequestMapping("/User")
public class UserController
{
    @Autowired
    private UserService userService;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    public UserController(UserService userService,ModelMapper modelMapper, ObjectMapper objectMapper) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(value = "login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO)
    {
        log.info(loginRequestDTO.toString());
        User user = userService.login(loginRequestDTO.getUserName(), loginRequestDTO.getPassword());
        if (user!=null)
        {
            UserDTO dto = modelMapper.map(user, UserDTO.class);
            return ResponseEntity.ok(LoginResponseDTO.builder().token(this.generateToken(dto)).build());
        }
        else
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<User>> getAllUser(Pageable pageable) {
        Page page = userService.getAll(pageable);
        return response(page);
    }

    private ResponseEntity response(List list, Page page) {
        HttpStatus status = !list.isEmpty() ? HttpStatus.OK : HttpStatus.NO_CONTENT;
        return ResponseEntity.status(status).
                header("X-Total-Count", Long.toString(page.getTotalElements())).
                header("X-Total-Pages", Long.toString(page.getTotalPages())).
                body(page.getContent());
    }


    private ResponseEntity response(List list) {
        return ResponseEntity.status(list.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK).body(list);
    }

    private ResponseEntity response(Page page) {
        HttpStatus httpStatus = page.getContent().isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return ResponseEntity.
                status(httpStatus).
                header("X-Total-Count", Long.toString(page.getTotalElements())).
                header("X-Total-Pages", Long.toString(page.getTotalPages())).
                body(page.getContent());

    }

    private String generateToken(UserDTO userDto) {
        try {
            List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("EMPLOYEE");
            String token = Jwts
                    .builder()
                    .setId("JWT")
                    .setSubject(userDto.getUserName())
                    .claim("user", objectMapper.writeValueAsString(userDto))
                    .claim("authorities",grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 1000000))
                    .signWith(SignatureAlgorithm.HS512, JWT_SECRET.getBytes()).compact();
            return  token;
        } catch(Exception e) {
            return "dummy";
        }



    }

}
