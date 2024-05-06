package com.app.firstaid;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BotFrag extends Fragment {

    private RecyclerView chatsRV;
    private EditText userMsg;
    private FloatingActionButton sendBtn;
    private final String BOT_KEY = "bot";
    private final String USER_KEY = "user";
    private ArrayList<ChatsModal>chatsModalArrayList;
    private ChatAdpater chatAdpater;
    private Chip chip1,chip2,chip3,chip4,chip5,chip6,chip7,chip8;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bot,container,false);

        chip1 = (Chip) view.findViewById(R.id.idChip1);
        chip2 = (Chip) view.findViewById(R.id.idChip2);
        chip3 = (Chip) view.findViewById(R.id.idChip3);
        chip4 = (Chip) view.findViewById(R.id.idChip4);
        chip5 = (Chip) view.findViewById(R.id.idChip5);
        chip6 = (Chip) view.findViewById(R.id.idChip6);
        chip7 = (Chip) view.findViewById(R.id.idChip7);
        chip8 = (Chip) view.findViewById(R.id.idChip8);
        chatsRV = view.findViewById(R.id.idChats);
        userMsg = view.findViewById(R.id.idUserMsg);
        sendBtn = view.findViewById(R.id.idSend);
        chatsModalArrayList = new ArrayList<>();
        chatAdpater = new ChatAdpater(chatsModalArrayList,getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        chatsRV.setLayoutManager(manager);
        chatsRV.setAdapter(chatAdpater);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userMsg.getText().toString().isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(),"Please enter a message",Toast.LENGTH_SHORT).show();
                    return;
                }

                getResponse(userMsg.getText().toString());
                userMsg.setText("");

            }
        });
        chip1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getResponse(chip1.getText().toString());
            }
        });
        chip2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getResponse(chip2.getText().toString());
            }
        });
        chip3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getResponse(chip3.getText().toString());
            }
        });
        chip4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getResponse(chip4.getText().toString());
            }
        });
        chip5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getResponse(chip5.getText().toString());
            }
        });
        chip6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getResponse(chip6.getText().toString());
            }
        });
        chip7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getResponse(chip7.getText().toString());
            }
        });
        chip8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getResponse(chip8.getText().toString());
            }
        });



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        chatsModalArrayList.add(new ChatsModal("Hello, what can I do for you today? ",BOT_KEY));
        chatAdpater.notifyDataSetChanged();

    }

    private void getResponse(String message){
        chatsModalArrayList.add(new ChatsModal(message,USER_KEY));
        chatAdpater.notifyDataSetChanged();
        String url = "http://api.brainshop.ai/get?bid=169799&key=lGB3xYk4VMUdhUeh&uid=[uid]&msg="+message;
        String BASE_URL = "http://api.brainshop.ai/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<MsgModal> call = retrofitAPI.getMessage(url);
        call.enqueue(new Callback<MsgModal>() {
            @Override
            public void onResponse(Call<MsgModal> call, Response<MsgModal> response) {
                if(response.isSuccessful()){
                    MsgModal modal = response.body();
                    String modifiedMessage = modal.getCnt().replaceAll(";", ".\n\n");
                    modal.setCnt(modifiedMessage);
                    chatsModalArrayList.add(new ChatsModal(modal.getCnt(),BOT_KEY));
                    chatAdpater.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<MsgModal> call, Throwable t) {
                chatsModalArrayList.add(new ChatsModal("Please revert your message",BOT_KEY));
                chatAdpater.notifyDataSetChanged();
            }
        });
    }
}
