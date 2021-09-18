package com.mvvm_tutorials.a_simple_binding.view_model;

import androidx.databinding.BaseObservable;

import com.mvvm_tutorials.R;
import com.mvvm_tutorials.a_simple_binding.model.User;

//BaseObservable help to notify everything with your activity
public class UserModel extends BaseObservable {

    private String email;
    private String password;
    private String emailHint;
    private String passwordHint;

    public UserModel(User user){
        this.emailHint=user.emailHint;
        this.passwordHint=user.passwordHint;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(R.id.userEmail);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(R.id.userPassword);
    }

    public String getEmailHint() {
        return emailHint;
    }

    public void setEmailHint(String emailHint) {
        this.emailHint = emailHint;
    }

    public String getPasswordHint() {
        return passwordHint;
    }

    public void setPasswordHint(String passwordHint) {
        this.passwordHint = passwordHint;
    }
}
