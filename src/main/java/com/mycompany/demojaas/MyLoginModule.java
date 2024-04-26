/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demojaas;

import java.io.IOException;
import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

/**
 *
 * @author Admin
 */
public class MyLoginModule implements LoginModule {

    private Subject subject;
    private CallbackHandler callbackHandler;
    private boolean succeeded = false;
    
    
    private static String userName, passWord;
    public static void setUser(String username, String password){
        userName=username;
        passWord=password;
    }
    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler,
                           Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
    }

    @Override
    public boolean login() throws LoginException {
        Callback[] callbacks = new Callback[2];
        callbacks[0] = new NameCallback("Username: ");
        callbacks[1] = new PasswordCallback("Password: ", false);

        try {
            callbackHandler.handle(callbacks);
            String username = ((NameCallback) callbacks[0]).getName();
            String password = new String(((PasswordCallback) callbacks[1]).getPassword());

            if (username.equals(userName) && password.equals(passWord)) {
                succeeded = true;
                System.out.println("Authentication successful!");
                return true;
            } else {
                throw new FailedLoginException("Authentication failed!");
            }
        } catch (IOException | UnsupportedCallbackException e) {
            throw new LoginException(e.getMessage());
        }
    }

    @Override
    public boolean commit() throws LoginException {
        if (succeeded) {
            subject.getPrincipals().add(new MyUserPrincipal("admin"));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean abort() throws LoginException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public boolean logout() throws LoginException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
