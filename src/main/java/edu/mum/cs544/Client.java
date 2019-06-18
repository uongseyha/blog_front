package edu.mum.cs544;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("edu.mum.cs544")
public class Client implements CommandLineRunner {
    @Autowired
    private UserService userService;

    @Override
    public void run(String... args){
//        User user=userService.get(2);

        //=== getAll ===
        System.out.println(userService.getAll());

//        //=== add ===
        userService.add(new User("seyha3","uong3","seyha3@mail.com","123"));
        System.out.println(userService.getAll());
////
//        //=== update ===
//        user.setEmail("seyhaUpdate@mail.com");
//        userService.update(user);
//        System.out.println(userService.getAll());
////
//        //=== delete ===
//        userService.delete(2);
//        System.out.println(userService.getAll());
//
//        //=== get ===
//        user=userService.getAll().get(0);
//        System.out.println(user.getFirstName());
    }
}
