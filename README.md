# Textchat
---
### Android lightweight chating application with Maching learning ML-KIT
---
## Using Tech:

* Java
* Xml
* Firebase,
* Firebase Push Notification
* Retrofit,
* Text Recognition with ML-KIT
---
#### Watch Demo : https://youtu.be/S2gbnVanKDA
---
## Features

* User can register with phone number
* User can add personal info such name, profile image
* User can see userlist who are using TextChat from his/her mobile contact
* User can see who is online
* User can send text message each other
* User can chat by group
* User can see notification if someone message
* User can send message to retrieve text from image using Gallery or Camera
---
## Screensorts with description

After open the application you will find the welcome screen that stay in 2.5 seconds. after you can see login screen that you can login with your name and phone number. After login with this you will get a verification code in the given phone number after input the code you can login

This is the welcome Screen             |  This is login screen | This is verification code
:-------------------------:|:-------------------------:|:-------------------------:
![1 - Copy](https://user-images.githubusercontent.com/42282069/61285904-90302580-a7e3-11e9-87d9-35392291beb3.png)  |  ![2](https://user-images.githubusercontent.com/42282069/61287478-c4591580-a7e6-11e9-8116-93c6d71241ae.png) | ![3](https://user-images.githubusercontent.com/42282069/61288197-6fb69a00-a7e8-11e9-8e44-650135b4ca84.png)

---

After login you can see the main features of the application. At first you can see Chatlist where your chatlist will be available. you can see who is online or offline. you can also see the userlist that who used this application from your contact. You can also add your profile image from profile section.

Here is the chatlist  |  Here is userlist | Here is the profile
:-------------------------:|:-------------------------:|:-------------------------:
![Screenshot (132) - Copy](https://user-images.githubusercontent.com/42282069/61289991-8eb72b00-a7ec-11e9-8ba9-66b02413c1a3.png) | ![2019_05_14_17 01 48](https://user-images.githubusercontent.com/42282069/61287464-c28f5200-a7e6-11e9-97a5-51bc2d578372.jpg) | ![2019_05_14_17 02 13](https://user-images.githubusercontent.com/42282069/61287468-c28f5200-a7e6-11e9-809d-86c68bb70658.jpg)

---

Then you can chat anyone from the list and enter the chatbox. You can send text from capture any image using camera and featching text from that image

Chatbox             |  Find text from camera | Chatbox text from image
:-------------------------:|:-------------------------:|:-------------------------:
![2019_05_14_17 01 30](https://user-images.githubusercontent.com/42282069/61287462-c1f6bb80-a7e6-11e9-8ab0-6ce5161aed12.jpg)  |  ![2019_05_14_17 10 13](https://user-images.githubusercontent.com/42282069/61287471-c327e880-a7e6-11e9-802d-9bafb1503f94.jpg) | ![2019_05_14_17 11 04](https://user-images.githubusercontent.com/42282069/61287472-c3c07f00-a7e6-11e9-9751-587838c4e924.jpg)

---

In your phone gallery if any image have text then you can send it as text message by featching the text from the gallery image. there is also have logout option

Gallery image    |  Find text from gallery | This is logout option
:-------------------------:|:-------------------------:|:-------------------------:
![2019_05_14_17 11 38](https://user-images.githubusercontent.com/42282069/61287474-c3c07f00-a7e6-11e9-9ef3-332eb4914a8a.jpg)  |  ![2019_05_14_17 11 43 - Copy](https://user-images.githubusercontent.com/42282069/61287476-c4591580-a7e6-11e9-8279-a258015ba2e7.jpg) | ![2019_05_14_17 02 28](https://user-images.githubusercontent.com/42282069/61287469-c327e880-a7e6-11e9-91bf-dc2af414f78a.jpg)

---

If you send message to anyone who is not online p but they have net connection with the phone then they will get your message notification

Sending message            |  Message notification
:-------------------------:|:-------------------------:
![Screenshot (134) - Copy](https://user-images.githubusercontent.com/42282069/61291082-41888880-a7ef-11e9-866d-75b62f8697d8.png)  |  ![Capture - Copy (2)](https://user-images.githubusercontent.com/42282069/61291083-42211f00-a7ef-11e9-981c-cd2f666e8081.PNG)

---

## Feature Work

I will be  added voice recognization function and also  smart reply option by analyzing previous messages

---

## Important code

* Userlist(Firebase) with comparing phone contact:

```

Cursor=getActivity().getApplicationContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            if (phone.length() <= 10) {
                continue;
            }
            if (!String.valueOf(phone.charAt(0)).equals("+")) {
                phone = "+88" + phone;
            }
            phone = phone.replace(" ", "");
            phone = phone.replace("-", "");
            phone = phone.replace("(", "");
            phone = phone.replace(")", "");
            final UserObject mContact = new UserObject("", name, phone);
            contactList.add(mContact);

            final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            final DatabaseReference mUserDB = FirebaseDatabase.getInstance().getReference().child("Users");

            Query query = mUserDB.orderByChild("phone").equalTo(mContact.getPhone());
            query.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   // mUsers.clear();
                   if (dataSnapshot.exists()) {

                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                    User user = childSnapshot.getValue( User.class );
                                    if (!user.getId().equals( firebaseUser.getUid() )) {
                                        mUsers.add(user);
                                    }
                                }

                                userAdapter = new UserAdapter( getContext(), mUsers, false );
                                recyclerView.setAdapter( userAdapter );
                            }
                        }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
        cursor.close();

}


```

* Sending Message:

```

private void sendMessage(String sender, final String receiver, String message){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("isseen", false);
        reference.child("Chats").push().setValue(hashMap);
        final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(fuser.getUid())
                .child(userid);
        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    chatRef.child("id").setValue(userid);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });   
        final DatabaseReference chatRefReceiver = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(userid)
                .child(fuser.getUid());
        chatRefReceiver.child("id").setValue(fuser.getUid());
        final String msg = message;
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (notify) {
                    sendNotifiaction(receiver, user.getUsername(), msg);
                }
                notify = false;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


```

* Receiving/Reading Message:

```

private void readMesagges(final String myid, final String userid, final String imageurl){
        mchat = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||
                            chat.getReceiver().equals(userid) && chat.getSender().equals(myid)){
                        mchat.add(chat);
                    }
                    messageAdapter = new MessageAdapter(MessageActivity.this, mchat, imageurl);
                    recyclerView.setAdapter(messageAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }



```

* Message Notification:

```

private void sendNotifiaction(String receiver, final String username, final String message){
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Token token = snapshot.getValue(Token.class);
            Data data = new Data(fuser.getUid(), R.mipmap.ic_launcher, username+": "+message, "New Message",
                            userid);
                    Sender sender = new Sender(data, token.getToken());
                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if (response.code() == 200){
                                        if (response.body().success != 1){
                                            Toast.makeText(MessageActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {
                                }
                            });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

```


## License

MIT License

Copyright (c) 2019 Md. Moahiminur Rahaman

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

[Back To The Top](#Textchat)

---

## Author Info
- Linkedin- [@Mohaiminur](https://www.linkedin.com/in/mohaiminur/)
- Youtube- [@Mohaiminur](https://www.youtube.com/channel/UC5MlwVt5vXtpHvgDHxbgqmw)
- Facebook- [@Mohaiminur](https://facebook.com/sifat404)
- Twitter - [@Mohaiminur](https://twitter.com/sifatkhan442)
- Website - [Mohaiminur](https://mohaiminur.ml)

[Back To The Top](#Textchat)
---
