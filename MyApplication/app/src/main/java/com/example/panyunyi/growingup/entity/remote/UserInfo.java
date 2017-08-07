package com.example.panyunyi.growingup.entity.remote;



/**
 * Created by panyu on 2017/6/7.
 */

public class UserInfo{
    public UserInfo(){}
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserWechat() {
        return userWechat;
    }

    public void setUserWechat(String userWechat) {
        this.userWechat = userWechat;
    }

    public String getUserAlipay() {
        return userAlipay;
    }

    public void setUserAlipay(String userAlipay) {
        this.userAlipay = userAlipay;
    }

    public String getUserTelephoneNumber() {
        return userTelephoneNumber;
    }

    public void setUserTelephoneNumber(String userTelephoneNumber) {
        this.userTelephoneNumber = userTelephoneNumber;
    }

    public String getUserGrade() {
        return userGrade;
    }

    public void setUserGrade(String userGrade) {
        this.userGrade = userGrade;
    }

    public String getUserClass() {
        return userClass;
    }

    public void setUserClass(String userClass) {
        this.userClass = userClass;
    }

    public String getUserInstitution() {
        return userInstitution;
    }

    public void setUserInstitution(String userInstitution) {
        this.userInstitution = userInstitution;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    private String userName;
    private String userPassword;
    private String userId;
    private String userWechat;
    private String userAlipay;
    private String userTelephoneNumber;
    private String userGrade;
    private String userClass;
    private String userInstitution;
    private String userRole;



    public static class UserBuilder{
        private String userName;
        private String userPassword;
        private String userId;
        private String userWechat;
        private String userAlipay;
        private String userTelephoneNumber;
        private String userGrade;
        private String userClass;
        private String userInstitution;
        private String userRole;

        public UserBuilder userName(String name){
            userName=name;
            return this;
        }
        public UserBuilder userId(String sId){
            userId=sId;
            return this;
        }
        public UserBuilder userWechat(String weChat){
            userWechat=weChat;
            return this;
        }
        public UserBuilder userClass(String sClass){
            userClass=sClass;
            return this;
        }
        public UserBuilder userPhone(String phone){
            userTelephoneNumber=phone;
            return this;
        }
        public UserBuilder userInstitute(String institute){
            userInstitution=institute;
            return this;
        }
        public UserBuilder userGrade(String grade){
            userGrade=grade;
            return this;
        }
        public UserBuilder userPassword(String Password){
            userPassword=Password;
            return this;
        }
        public UserBuilder userAliPay(String aliPay){
            userAlipay=aliPay;
            return this;
        }
        public UserBuilder userRole(String role){
            userRole=role;
            return this;
        }
        public UserInfo build(){
            return new UserInfo(this);
        }
    }

    private UserInfo(UserBuilder builder){
        userId=builder.userId;
        userName=builder.userName;
        userAlipay=builder.userAlipay;
        userClass=builder.userClass;
        userGrade=builder.userGrade;
        userInstitution=builder.userInstitution;
        userPassword=builder.userPassword;
        userTelephoneNumber=builder.userTelephoneNumber;
        userWechat=builder.userWechat;
        userRole=builder.userRole;
    }
}
