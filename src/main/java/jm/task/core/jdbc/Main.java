package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Mark", "Mironov", (byte) 27);
        userService.saveUser("Alisa", "Veber", (byte) 39);
        userService.saveUser("Victor", "Miheev", (byte) 45);
        userService.saveUser("Den", "Denver", (byte) 34);

        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
    }
}