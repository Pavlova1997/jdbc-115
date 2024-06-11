package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Alex", "Alexon ", (byte) 22);
        userService.saveUser("Maria", "First", (byte) 25);
        userService.saveUser("Joseph", "Jordan", (byte) 30);
        userService.saveUser("Jose", "Secundo", (byte) 26);


        System.out.println(userService.getAllUsers());

        userService.cleanUsersTable();

        userService.dropUsersTable();

    }
}
