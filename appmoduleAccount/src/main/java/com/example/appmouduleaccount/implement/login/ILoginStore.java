package com.example.appmouduleaccount.implement.login;


import com.example.appmouduleaccount.api.login.domain.LoginResult;

/**
 * Saved a list of all config, uses SharedPreferences, SQLite or Realm to realize.
 */
public interface ILoginStore {

    /*
     * Returns a credentials list
     */
    LoginResult getCredentialsList();

    /*
     * Adds a credential to the credentials list from a HomeServerConnectionConfig
     */
    void addLoginResult(LoginResult loginResult);

    /**
     * Removes the credentials from the list by the same userId
     */
    void removeLoginResult(LoginResult loginResult);

    /**
     * Clears the credentials list
     */
    void clearLoginResult();
}
