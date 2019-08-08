    package com.cipher.sharesmilesandroid.fragments;

    import android.os.Bundle;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.cipher.sharesmilesandroid.R;
    import com.cipher.sharesmilesandroid.adapters.HomeAdapter;
    import com.cipher.sharesmilesandroid.modals.ProductTags;
    import com.cipher.sharesmilesandroid.modals.ProductUser;
    import com.cipher.sharesmilesandroid.modals.Products;
    import com.cipher.sharesmilesandroid.modals.Users;
    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.firestore.DocumentChange;
    import com.google.firebase.firestore.DocumentReference;
    import com.google.firebase.firestore.DocumentSnapshot;
    import com.google.firebase.firestore.EventListener;
    import com.google.firebase.firestore.FirebaseFirestore;
    import com.google.firebase.firestore.FirebaseFirestoreException;
    import com.google.firebase.firestore.QuerySnapshot;
    import com.wang.avi.AVLoadingIndicatorView;

    import java.util.ArrayList;

    public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseAuth auth;
    private FirebaseFirestore dRef = FirebaseFirestore.getInstance();

    ArrayList<Products> productsArrayList = new ArrayList<>();
    ArrayList<ProductUser> productUserArrayList = new ArrayList<>();

    private AVLoadingIndicatorView avlLoader;
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
        avlLoader = view.findViewById(R.id.avlLoader);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        avlLoader.setVisibility(View.VISIBLE);
        mAdapter = new HomeAdapter(getActivity(),productsArrayList);
        recyclerView.setAdapter(mAdapter);

        getListItems();
        return view;

    }

    private void getListItems() {
        dRef.collection("products").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (e !=null)
                {
                    e.printStackTrace();
                }

                for (DocumentChange documentChange : documentSnapshots.getDocumentChanges())
                {
                    if (documentChange.getDocument().getData().isEmpty()){
                        avlLoader.setVisibility(View.GONE);
                    }else {
                        try {
                            ArrayList<ProductTags> productTagsArrayList = new ArrayList<>();

                            ArrayList<ProductTags>   stringArrayList = (ArrayList<ProductTags>) documentChange.getDocument().getData().get("Tags");

//                            for(ProductTags item : stringArrayList) {
//                                productTagsArrayList.add(item);
//                            }
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
                                avlLoader.setVisibility(View.GONE);
                            }else {
                                avlLoader.setVisibility(View.VISIBLE);
                            }

                        }catch (Exception exception){
                            Log.e(TAG, "onEvent: "+exception.getMessage() );
                            exception.printStackTrace();
                        }


                    }

                }
            }
        });

//        for (int i =0 ; i<productUserArrayList.size() ; i++){
//            getUserInfo(productUserArrayList.get(i).getProducts().getSellerID(), productUserArrayList.get(i) ,i);
//        }

    }

        private void getUserInfo(String sellerId, ProductUser productUser, int i) {

            Users users = new Users();
            DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document(sellerId);


            docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {

                    String  firstName =  documentSnapshot.getData().get("firstName").toString();
                    String  lastName =  documentSnapshot.getData().get("lastName").toString();
                    String  profileImage =  documentSnapshot.getData().get("profilePic").toString();

                    users.setFirstName(firstName);
                    users.setLastName(lastName);
                    users.setUserImage(profileImage);

                    if (documentSnapshot.getData().get("description")!=null){
                        users.setDescription( documentSnapshot.getData().get("description").toString());
                    }

                    if (documentSnapshot.getData().get("dob")!=null){
                        users.setDob( documentSnapshot.getData().get("dob").toString());
                    }

                    if (documentSnapshot.getData().get("address")!=null){
                        users.setAddress( documentSnapshot.getData().get("address").toString());
                    }

                    if (documentSnapshot.getData().get("city")!=null){
                        users.setCity( documentSnapshot.getData().get("city").toString());
                    }

                    if (documentSnapshot.getData().get("zipcode")!=null){
                        users.setZipcode(documentSnapshot.getData().get("zipcode").toString());
                    }

                    productUser.setUsers(users);
                    productUserArrayList.set(i , productUser);
                    mAdapter.notifyDataSetChanged();

                    Log.e(TAG, "onEvent: "+users.getLastName() );
                }
            });


        }

    }
