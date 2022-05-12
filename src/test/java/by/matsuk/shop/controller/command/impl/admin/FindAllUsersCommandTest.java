package by.matsuk.shop.controller.command.impl.admin;

import by.matsuk.shop.controller.Router;
import by.matsuk.shop.entity.User;
import by.matsuk.shop.exception.CommandException;
import by.matsuk.shop.exception.ServiceException;
import by.matsuk.shop.model.service.UserService;
import by.matsuk.shop.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.mockito.Mockito;
import org.powermock.reflect.internal.WhiteboxImpl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class FindAllUsersCommandTest {
    UserService mockService;
    HttpServletRequest mock;
    FindAllUsersCommand command;

    @BeforeMethod
    public void setUp(){
        command = new FindAllUsersCommand();
        mock = Mockito.mock(HttpServletRequest.class);
        mockService = Mockito.mock(UserServiceImpl.class);
        WhiteboxImpl.setInternalState(command, "userService", mockService);
    }

    @DataProvider(name = "users")
    public Object[][] signInData() {
        return new Object[][]{
                {List.of(new User(20, "Iryna", "Matsuk", "admin1",
                        "e00cf25ad42683b3df678c61f42c6bda", "g3726866@gmail.com", 333726866,
                        1, User.UserRole.ADMIN, User.UserState.NEW)), "/jsp/pages/admin/users.jsp"},
        };
    }

    @Test(dataProvider = "users")
    public void findAllClientsTest(List<User> userList, String expected) throws CommandException, ServiceException {
        Mockito.when(mockService.findAllClients())
                .thenReturn(userList);
        Router router = command.execute(mock);
        Mockito.verify(mockService, Mockito.times(1)).findAllClients();
        assertEquals(router.getCurrentPage(), expected);
    }
}