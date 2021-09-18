package com.mvvm_tutorials.a_simple_binding.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;

import android.database.DatabaseUtils;
import android.os.Bundle;
import android.widget.Toast;

import com.mvvm_tutorials.R;
import com.mvvm_tutorials.a_simple_binding.commands_actions.UserLogin;
import com.mvvm_tutorials.a_simple_binding.model.User;
import com.mvvm_tutorials.a_simple_binding.view_model.UserModel;
import com.mvvm_tutorials.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        UserModel userModel = new UserModel(new User("email hint","password hint"));
       activityMainBinding.setLogin(userModel);

       activityMainBinding.setUserLoginEvent(new UserLogin() {
           @Override
           public void onClickLogin() {
               Toast.makeText(getApplicationContext(), userModel.getEmail()+" "+userModel.getPassword(), Toast.LENGTH_SHORT).show();
           //or Toast.makeText(getApplicationContext(), activityMainBinding.getLogin().getEmail()+" "+activityMainBinding.getLogin().getPassword(), Toast.LENGTH_SHORT).show();
           }
       });
    }
}