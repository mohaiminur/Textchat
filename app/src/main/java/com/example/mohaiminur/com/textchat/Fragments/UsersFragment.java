package com.example.mohaiminur.com.textchat.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.mohaiminur.com.textchat.Model.UserObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.example.mohaiminur.com.textchat.Adapter.UserAdapter;
import com.example.mohaiminur.com.textchat.Model.User;
import com.example.mohaiminur.com.textchat.R;
import java.util.ArrayList;
import java.util.List;


public class UsersFragment extends Fragment {

    private RecyclerView recyclerView;

    private UserAdapter userAdapter;
    private List<User> mUsers;
    Cursor cursor;
    String phone;

    ArrayList<UserObject> userList, contactList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_users, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mUsers = new ArrayList<>();

        contactList= new ArrayList<>();
        userList= new ArrayList<>();


        readUsers();

        return view;
    }


    public void readUsers() {

        cursor = getActivity().getApplicationContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

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

}
