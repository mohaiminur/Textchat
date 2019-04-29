package com.example.mohaiminur.com.textchat.Fragments;

import com.example.mohaiminur.com.textchat.Notifications.MyResponse;
import com.example.mohaiminur.com.textchat.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAvbCEAo4:APA91bHjxeZAeBT4-hUzVqcYz0cAzYjYb49GGgHlOeOlAUzbxYlTXZDZePKbpKQfUjM1QSDZdiLIs_nNUJcXwZREAELb3RcS97YGXLNDVREFa8IItZp7A9hcdfdDloVqM3VrLfY1FHdG"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
