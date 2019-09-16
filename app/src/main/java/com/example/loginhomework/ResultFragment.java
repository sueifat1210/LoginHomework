package com.example.loginhomework;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


public class ResultFragment extends Fragment {
    private static final String TAG = "TAG_ResultFragment";
    private Activity activity;
    private EditText etName, etPassword, etUserName;
    private Button btInsert, btEdit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final NavController navController = Navigation.findNavController(view);


        etName = view.findViewById(R.id.etName);
        etPassword = view.findViewById(R.id.etPassword);
        etUserName = view.findViewById(R.id.etUserName);
        btInsert = view.findViewById(R.id.btInsert);
        btEdit = view.findViewById(R.id.btEdit);

        btInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString().trim();
                if (name.length() <= 0) {
                    Common.showToast(getActivity(), R.string.textNameIsInvalid);
                    return;
                }
                String password = etPassword.getText().toString().trim();
                String userName = etUserName.getText().toString().trim();
                if (Common.networkConnected(activity)) {
                    String url = Common.URL_SERVER + "SpotServlet";
                    Spot spot = new Spot(0, name, password, userName);
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("action", "spotInsert");
                    jsonObject.addProperty("spot", new Gson().toJson(spot));

                    int count = 0;
                    try {
                        String result = new CommonTask(url, jsonObject.toString()).execute().get();
                        count = Integer.valueOf(result);
                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }
                    if (count == 0) {
                        Common.showToast(getActivity(), R.string.textInsertFail);
                    } else {
                        Common.showToast(getActivity(), R.string.textInsertSuccess);
                    }
                } else {
                    Common.showToast(getActivity(), R.string.textNoNetwork);
                }
                /* 回前一個Fragment */
                navController.popBackStack();
            }
        });

        Button btCancel = view.findViewById(R.id.btEdit);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* 回前一個Fragment */
                navController.popBackStack();
            }
        });
    }
}







