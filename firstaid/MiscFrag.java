package com.app.firstaid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class MiscFrag extends Fragment {

    private Button btnAmbulanceCall;
    private Button btnFireStationCall;
    private Button btnPoliceStationCall;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_misc, container, false);

        btnAmbulanceCall = view.findViewById(R.id.idBtnCall1);
        btnFireStationCall = view.findViewById(R.id.idBtnCall2);
        btnPoliceStationCall = view.findViewById(R.id.idBtnCall3);

        btnAmbulanceCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCall("tel:108");
            }
        });

        btnFireStationCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCall("tel:101");
            }
        });

        btnPoliceStationCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCall("tel:100");
            }
        });

        return view;
    }

    private void makeCall(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(phoneNumber));
        startActivity(intent);
    }
}
