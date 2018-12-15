package com.example.appmouduleaccount.api.login.domain;


import com.google.gson.annotations.SerializedName;

/**
 * FinoChatSDK User login model.
 */
public class LoginRequest {

    /**
     * For user's userId, must use with :password.
     */
    @SerializedName("userId")
    public String userId;

    /**
     * For user password, must use with :userId.
     */
    @SerializedName("password")
    public String password;

    /**
     * For userToken.
     */
    @SerializedName("token")
    public String token;

    /*
     * Two types: 'pwd' and 'token'.
     *     'pwd'    -> requests :userId and :password.
     *     'token'  -> requests :token only.
     */

    @SerializedName("mobile")
    public String mobile;

    @SerializedName("verification")
    public String verification;

    @SerializedName("login_type")
    public String login_type;

    /**
     * Platform types: iOS, Android, web
     * Be careful of uppercase and lowercase.
     */
    @SerializedName("device_type")
    public String device_type;

    /**
     * Global version dictionary.
     */
    @SerializedName("app_type")
    public String app_type;

    /**
     * retail type
     */
    @SerializedName("account_type")
    public String account_type;

    /**
     * device id
     */
    @SerializedName("device_id")
    public String deviceId;

    public LoginRequest(){

    }

    /**
     * Login constructor.
     *
     * @param userId      userId same as matrixId.
     * @param password    User's password.
     * @param token       The token which get after login successfully
     * @param login_type  Login type, for server to recognize which login method is using
     * @param device_type device_type
     * @param app_type    app_type
     */
    public LoginRequest(String userId, String password, String token,
                        String login_type, String device_type, String app_type) {

        this.userId = userId;
        this.password = password;
        this.token = token;
        this.login_type = login_type;
        this.device_type = device_type;
        this.app_type = app_type;
        this.account_type = null;
    }

    /**
     * Login constructor.
     *
     * @param userId       userId same as matrixId.
     * @param password     User's password.
     * @param token        The token which get after login successfully
     * @param login_type   Login type, for server to recognize which login method is using
     * @param device_type  device_type
     * @param app_type     app_type
     * @param account_type retail type
     */
    public LoginRequest(String userId, String password, String token,
                        String login_type, String device_type, String app_type, String account_type) {

        this.userId = userId;
        this.password = password;
        this.token = token;
        this.login_type = login_type;
        this.device_type = device_type;
        this.app_type = app_type;
        this.account_type = account_type;
    }

    public LoginRequest(String userId, String password, String mobile, String verification, String token,
                        String login_type, String device_type, String app_type, String account_type) {

        this.userId = userId;
        this.password = password;
        this.mobile = mobile;
        this.verification = verification;
        this.token = token;
        this.login_type = login_type;
        this.device_type = device_type;
        this.app_type = app_type;
        this.account_type = account_type;
    }
}
