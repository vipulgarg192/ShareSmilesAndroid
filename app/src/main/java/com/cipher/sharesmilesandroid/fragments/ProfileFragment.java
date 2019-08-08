package com.cipher.sharesmilesandroid.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cipher.sharesmilesandroid.R;
import com.cipher.sharesmilesandroid.ShareSmilesPrefs;
import com.cipher.sharesmilesandroid.ShareSmilesSingleton;
import com.cipher.sharesmilesandroid.adapters.UserAddedProdAdapter;
import com.cipher.sharesmilesandroid.modals.ProductTags;
import com.cipher.sharesmilesandroid.modals.Products;
import com.cipher.sharesmilesandroid.modals.Users;
import com.cipher.sharesmilesandroid.ui.BeautifullTextView;
import com.cipher.sharesmilesandroid.ui.HelveticaNeueTextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends Fragment {


    @BindView(R.id.imgTimeline)
    AppCompatImageView imgTimeline;
    @BindView(R.id.imgProfile)
    AppCompatImageView imgProfile;
    @BindView(R.id.cdProfile)
    MaterialCardView cdProfile;
    @BindView(R.id.ibSetting)
    ImageButton ibSetting;
    @BindView(R.id.tvName)
    BeautifullTextView tvName;
    @BindView(R.id.tvDesc)
    HelveticaNeueTextView tvDesc;

    @BindView(R.id.rvUserItems)
    RecyclerView rvUserItems;

    @BindView(R.id.nestedScroll)
    NestedScrollView nestedScroll;
    @BindView(R.id.tvNotAvailable)
    HelveticaNeueTextView tvNotAvailable;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    private FirebaseFirestore firebaseFirestore ;
    private FirebaseFirestore dRef = FirebaseFirestore.getInstance();
    private String userID;

    private ArrayList<Users> userData ;

    private String  profileImage;
    private String  description="";
    private String  dob="";
    private String  address="";
    private String  city="";
    private String  zipcode="";

    private static final String TAG = "ProfileFragment";

    private ArrayList<Products> productsArrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment,
                container, false);
        ButterKnife.bind(this, view);
        firebaseFirestore = FirebaseFirestore.getInstance();
        layoutManager = new LinearLayoutManager(getContext());
        rvUserItems.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String userPic = ShareSmilesPrefs.readString(getActivity(),ShareSmilesPrefs.userPic,null);
        if (userPic!=null){
            Glide.with(this).load(userPic).placeholder(R.drawable.ic_profile).into(imgProfile);
        }
        userData = new ArrayList<>();
        getProfileData();

        mAdapter = new UserAddedProdAdapter(getActivity(),productsArrayList);
        rvUserItems.setAdapter(mAdapter);

        getListItems();

    }

    private void getProfileData() {
        userID = ShareSmilesPrefs.readString(getActivity(),ShareSmilesPrefs.userId,null);

        DocumentReference docRef = firebaseFirestore.collection("users").document(userID);


        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if (!document.getData().isEmpty()){

                            String  firstName =  document.getData().get("firstName").toString();
                            String  lastName =  document.getData().get("lastName").toString();

                            if (document.getData().get("profilePic")!=null){
                                profileImage =  document.getData().get("profilePic").toString();
                            }


                            if (document.getData().get("description")!=null){
                                description =  document.getData().get("description").toString();
                            }

                            if (document.getData().get("dob")!=null){
                                dob =  document.getData().get("dob").toString();
                            }

                            if (document.getData().get("address")!=null){
                                address =  document.getData().get("address").toString();
                            }

                            if (document.getData().get("city")!=null){
                                city =  document.getData().get("city").toString();
                            }

                            if (document.getData().get("zipcode")!=null){
                                zipcode =  document.getData().get("zipcode").toString();
                            }

                            String fullName = firstName+" "+lastName;
                            tvName.setText(fullName.trim());
                            tvName.setGravity(Gravity.CENTER);
                            tvDesc.setText(description.trim());

                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    ShareSmilesSingleton.getInstance().getDialogBoxs().showDismissBox(getContext(),task.getException().getMessage());
                }
            }
        });


    }


    private void getListItems() {
        String userId = ShareSmilesPrefs.readString(getActivity(),ShareSmilesPrefs.userId,null);
        dRef.collection("products").whereEqualTo("userId", userId).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (e !=null)
                {
                    e.printStackTrace();
                }

                for (DocumentChange documentChange : documentSnapshots.getDocumentChanges())
                {
                    if (documentChange.getDocument().getData().isEmpty()){
                        tvNotAvailable.setVisibility(View.GONE);
                    }else {
                        try {
                            ArrayList<ProductTags> productTagsArrayList = new ArrayList<>();

                            ArrayList<ProductTags>   stringArrayList = (ArrayList<ProductTags>) documentChange.getDocument().getData().get("Tags");


                            String   userID =  documentChange.getDocument().getData().get("userId").toString();

                            String   productName =  documentChange.getDocument().getData().get("productName").toString();
                            String   productDesc =  documentChange.getDocument().getData().get("productDesc").toString();
                            String   productPrice =  documentChange.getDocument().getData().get("price").toString();
                            String   productCategory =  documentChange.getDocument().getData().get("category").toString();

                            String   productOrganisation =  documentChange.getDocument().getData().get("organisationName").toString();
                            int   organisationId = Integer.parseInt(documentChange.getDocument().getData().get("organisationId").toString());

                            String   sellerId =  documentChange.getDocument().getData().get("sellerID").toString();
                            String   buyerId =  documentChange.getDocument().getData().get("buyerID").toString();
                            String   sellerName = documentChange.getDocument().getData().get("sellerName").toString();
                            String   buyerName = documentChange.getDocument().getData().get("buyerName").toString();


                            String   productAddedTime = documentChange.getDocument().getData().get("productAddedAt").toString();
                            String   productSoldTime =documentChange.getDocument().getData().get("productSoldTime").toString();

                            Products products = new Products( documentChange.getDocument().getId(),productName, productDesc,productPrice,
                                    "",sellerName,buyerName,productAddedTime,productSoldTime,false,productOrganisation
                                    ,organisationId,productCategory, productTagsArrayList);

                            for (int i=0 ;i<productTagsArrayList.size();i++) {
                                Log.e(TAG, "onEvent: "+productTagsArrayList.get(i).getTagName() );
                            }



                            productsArrayList.add(products);

                            if (productsArrayList.size()>0) {
                                mAdapter.notifyDataSetChanged();
                                tvNotAvailable.setVisibility(View.GONE);
                            }else {
                                tvNotAvailable.setVisibility(View.VISIBLE);
                            }

                        }catch (Exception exception){
                            Log.e(TAG, "onEvent: "+exception.getMessage() );
                            exception.printStackTrace();
                        }


                    }

                }
            }
        });

    }

}
