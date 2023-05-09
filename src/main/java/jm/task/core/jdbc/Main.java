package jm.task.core.jdbc;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        for (int i = 0; i < 4; i++) {
            String name = "name" + i;
            String lastName = "lastName" + i;
            byte age = (byte)(i*5);
            userService.saveUser(name, lastName, age);
            System.out.printf("User с именем %s добавлен в базу данных%n", name);
        }

        userService.getAllUsers().forEach(System.out::println);

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
