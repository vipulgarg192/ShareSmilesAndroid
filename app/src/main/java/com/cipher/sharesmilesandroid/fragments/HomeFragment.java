package com.cipher.sharesmilesandroid.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cipher.sharesmilesandroid.R;
import com.cipher.sharesmilesandroid.adapters.HomeAdapter;
import com.cipher.sharesmilesandroid.adapters.MyAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Type;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    private FirebaseAuth auth;

    private FirebaseFirestore dRef = FirebaseFirestore.getInstance();

    private static final String TAG = "HomeFragment";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,
                container, false);

        auth = FirebaseAuth.getInstance();
        recyclerView = view.findViewById(R.id.recyclerView);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new HomeAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);

getListItems();
        return view;

    }

    private void getListItems() {
//        dRef.collection("products").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                if (queryDocumentSnapshots.isEmpty()) {
//
//                    return;
//                } else {
//                    // Convert the whole Query Snapshot to a list
//                    // of objects directly! No need to fetch each
//                    // document.
//                    List<Type> types = queryDocumentSnapshots.toObjects(Type.class);
//
//                    // Add all to your list
//                    Log.e(TAG, "onSuccess: "+types );
//                }
//            }
//        });

        dRef.collection("products").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (e !=null)
                {

                }

                for (DocumentChange documentChange : documentSnapshots.getDocumentChanges())
                {
                    if (documentChange.getDocument().getData().isEmpty()){

                    }else {

                        try {
                            String   productName =  documentChange.getDocument().getData().get("productName").toString();
                            String  price   =  documentChange.getDocument().getData().get("price").toString();

                            Log.e(TAG, "onEvent: "+productName );
                            Log.e(TAG, "onEvent: "+price );
                        }catch (Exception ea){
                            ea.getMessage();
                        }


                    }

                }
            }
        });
    }

}
