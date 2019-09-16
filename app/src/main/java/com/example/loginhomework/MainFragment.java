package com.example.loginhomework;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import static android.content.Context.CONNECTIVITY_SERVICE;


public class MainFragment extends Fragment {
    private final static String TAG = "TAG_MainFragment";
    private Activity activity;
    private EditText etName,etPassword;
    private Button btLogin,btNew;
    private TextView tvMessage;
    private Gson gson;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        gson = new Gson();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity.setTitle("Login");
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etName = view.findViewById(R.id.etName);
        etPassword = view.findViewById(R.id.etPassword);
        btLogin = view.findViewById(R.id.btLogin);
        btNew = view.findViewById(R.id.btNew);
        tvMessage = view.findViewById(R.id.tvMessage);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                User user = new User(name, password);

                if (networkConnected()) {
                    String url = Common.URL_SERVER + "LoginServlet";
                    String outStr = gson.toJson(user);
                    CommonTask loginTask = new CommonTask(url, outStr);
                    boolean isUserValid = false;
                    try {
                        String jsonIn = loginTask.execute().get();
                        JsonObject jsonObject = gson.fromJson(jsonIn, JsonObject.class);
                        isUserValid = jsonObject.get("result").getAsBoolean();
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    }

                    if (isUserValid) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("user", user);
                        Navigation.findNavController(tvMessage)
                                .navigate(R.id.action_mainFragment_to_testFragment, bundle);
                    } else {
                        tvMessage.setText(R.string.textLoginFail);
                    }
                }
            }
        });


        btNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view)
                        .navigate(R.id.action_mainFragment_to_resultFragment);

            }
        });

    }
    // 檢查是否有網路連線
    private boolean networkConnected() {
        ConnectivityManager conManager =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conManager != null ? conManager.getActiveNetworkInfo() : null;
        return networkInfo != null && networkInfo.isConnected();
    }
    }


