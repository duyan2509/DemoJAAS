/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.demojaas;

import java.util.HashMap;
import java.util.Scanner;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

/**
 *
 * @author Admin
 */
public class DemoJAAS {

    public static void main(String[] args) {

        AppConfigurationEntry[] configEntries = {
            new AppConfigurationEntry(
                "com.mycompany.demojaas.MyLoginModule",
                AppConfigurationEntry.LoginModuleControlFlag.REQUIRED,
                new HashMap<String, Object>()
            )
        };
        Configuration jaasConfiguration = new Configuration() {
            @Override
            public AppConfigurationEntry[] getAppConfigurationEntry(String name) {
                return configEntries;
            }
        };

        // Thiết lập cấu hình JAAS cho ứng dụng
        Configuration.setConfiguration(jaasConfiguration);
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Register: ");
            System.out.print("username: ");
            String username = scanner.nextLine();
            System.out.print("password: ");
            String password = scanner.nextLine();
            MyLoginModule.setUser(username, password);
            
            System.out.println("Login: ");
            System.out.print("username: ");
            username = scanner.nextLine();
            System.out.print("password: ");
            password = scanner.nextLine();
            
            LoginContext loginContext = new LoginContext("LoginModule", new MyCallbackHandler(username, password));
            loginContext.login();
        } catch (LoginException e) {
            System.err.println("Authentication failed: " + e.getMessage());
        }
    }
}
