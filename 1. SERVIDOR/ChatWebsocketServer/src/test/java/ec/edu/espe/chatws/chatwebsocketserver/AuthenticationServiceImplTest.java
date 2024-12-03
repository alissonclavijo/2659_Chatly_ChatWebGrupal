package ec.edu.espe.chatws.chatwebsocketserver;

import ec.edu.espe.chatws.chatwebsocketserver.entity.User;
import ec.edu.espe.chatws.chatwebsocketserver.entity.UserPreference;
import ec.edu.espe.chatws.chatwebsocketserver.entity.UserRole;
import ec.edu.espe.chatws.chatwebsocketserver.presenter.AuthUserPresenter;
import ec.edu.espe.chatws.chatwebsocketserver.repository.UserPreferenceRepository;
import ec.edu.espe.chatws.chatwebsocketserver.repository.UserRepository;
import ec.edu.espe.chatws.chatwebsocketserver.service.impl.AuthenticationServiceImpl;
import ec.edu.espe.chatws.chatwebsocketserver.utils.ColorUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceImplTest {

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserPreferenceRepository userPreferenceRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ColorUtils colorUtils;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
    }

    @Test
    void shouldSignupNewUser() {
        // Configurar datos de entrada
        AuthUserPresenter input = new AuthUserPresenter("testuser", "testpassword");

        // Configurar comportamiento de los mocks
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("testpassword")).thenReturn("encodedPassword");
        when(userPreferenceRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(colorUtils.getRandomFlatColor()).thenReturn(Pair.of("#000000", "#FFFFFF"));

        // Ejecutar método
        User result = authenticationService.signup(input);

        // Verificar resultado
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("encodedPassword", result.getPassword());
        assertEquals(UserRole.ADMIN, result.getRole());

        // Verificar interacciones con los repositorios
        verify(userRepository, times(1)).findByUsername("testuser");
        verify(userRepository, times(1)).save(any());
        verify(userPreferenceRepository, times(1)).save(any());
    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyExists() {
        // Configurar datos de entrada
        AuthUserPresenter input = new AuthUserPresenter("existinguser", "testpassword");

        // Configurar comportamiento del mock
        when(userRepository.findByUsername("existinguser")).thenReturn(Optional.of(new User()));

        // Ejecutar método y verificar excepción
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> authenticationService.signup(input));

        assertEquals(HttpStatus.PRECONDITION_FAILED, exception.getStatusCode());
        assertEquals("User already exists", exception.getReason());

        // Verificar que no se guarda nada en los repositorios
        verify(userPreferenceRepository, never()).save(any());
        verify(userRepository, never()).save(any());
    }
}
