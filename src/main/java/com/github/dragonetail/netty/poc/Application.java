package com.github.dragonetail.netty.poc;

import com.github.dragonetail.payroll.emailnotification.main.controller.MainMenuController;
import com.github.dragonetail.payroll.emailnotification.util.LookAndFeelUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
//@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        LookAndFeelUtils.setWindowsLookAndFeel();
        ConfigurableApplicationContext context = new SpringApplicationBuilder(Application.class).headless(false).run(args);
        MainMenuController mainMenuController = context.getBean(MainMenuController.class);
        mainMenuController.prepareAndOpenFrame();
    }

}
