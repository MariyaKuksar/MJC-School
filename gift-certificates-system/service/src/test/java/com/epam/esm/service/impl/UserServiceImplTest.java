package com.epam.esm.service.impl;

import com.epam.esm.configuration.ServiceConfiguration;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = ServiceConfiguration.class)
public class UserServiceImplTest {
    @MockBean
    private UserDao userDao;
    @MockBean
    private UserValidator userValidator;
    @Autowired
    private UserService userService;
    private User user1;
    private UserDto userDto1;

    @BeforeEach
    public void setUp() {
        user1 = new User(1, "Maryia");
        userDto1 = new UserDto(1, "Maryia");
    }

    @Test
    public void findUserByIdPositiveTest() {
        doNothing().when(userValidator).validateId(anyLong());
        when(userDao.findById(anyLong())).thenReturn(Optional.of(user1));
        UserDto actual = userService.findUserById(1);
        assertEquals(userDto1, actual);
    }

    @Test
    public void findUserByIdNegativeTest() {
        doNothing().when(userValidator).validateId(anyLong());
        doThrow(ResourceNotFoundException.class).when(userDao).findById(anyLong());
        assertThrows(ResourceNotFoundException.class, () -> userService.findUserById(0));
    }
}
