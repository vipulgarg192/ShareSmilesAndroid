    package com.cipher.sharesmilesandroid.fragments;

    import android.os.Bundle;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;
    import androidx.lifecycle.LiveData;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.cipher.sharesmilesandroid.R;
    import com.cipher.sharesmilesandroid.activities.MainActivity;
    import com.cipher.sharesmilesandroid.adapters.HomeAdapter;
    import com.cipher.sharesmilesandroid.databases.AppExecutors;
    import com.cipher.sharesmilesandroid.databases.Respo;
    import com.cipher.sharesmilesandroid.databases.RoomDBCallBacks;
    import com.cipher.sharesmilesandroid.databases.UserRoomDatabase;
    import com.cipher.sharesmilesandroid.interfaces.UserDao;
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
    import com.google.firebase.firestore.QueryDocumentSnapshot;
    import com.google.firebase.firestore.QuerySnapshot;
    import com.wang.avi.AVLoadingIndicatorView;

    import java.util.ArrayList;
    import java.util.List;

    public class HomeFragment extends Fragment implements RoomDBCallBacks {

        RecyclerView recyclerView;
        private RecyclerView.Adapter mAdapter;
        private RecyclerView.LayoutManager layoutManager;

        private FirebaseAuth auth;
        private FirebaseFirestore dRef = FirebaseFirestore.getInstance();

        ArrayList<Products> productsArrayList = new ArrayList<>();
        List<Users> usersArrayList = new ArrayList<>();

        private AVLoadingIndicatorView avlLoader;
        private static final String TAG = "HomeFragment";

        RoomDBCallBacks roomDBCallBacks;

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
            mAdapter = new HomeAdapter(getActivity(), productsArrayList);
            recyclerView.setAdapter(mAdapter);

            roomDBCallBacks = (RoomDBCallBacks) getContext();

//            getUserInfo();
//            Respo.retrieveTask(UserRoomDatabase.getDatabase(getContext()), roomDBCallBacks);
              getListItems();


//            Log.e(TAG, "onCreateView: "+ UserRoomDatabase.getDatabase(getContext()).userDao().getAllUsers().size());
            return view;

        }

        private void getListItems() {

            dRef.collection("products").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    if (e != null) {
                        e.printStackTrace();
                    }
                    for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {

                        if (documentChange.getDocument().getData().isEmpty()) {
                            avlLoader.setVisibility(View.GONE);
                        } else {

                            try {
                                ArrayList<ProductTags> productTagsArrayList = new ArrayList<>();

                                ArrayList<ProductTags> stringArrayList = (ArrayList<ProductTags>) documentChange.getDocument().getData().get("Tags");

//                            for(ProductTags item : stringArrayList) {
//                                productTagsArrayList.add(item);
//                               }
                                String userID = documentChange.getDocument().getData().get("userId").toString();

                                String productName = documentChange.getDocument().getData().get("productName").toString();
                                String productDesc = documentChange.getDocument().getData().get("productDesc").toString();
                                String productPrice = documentChange.getDocument().getData().get("price").toString();
                                String productCategory = documentChange.getDocument().getData().get("category").toString();

                                String productOrganisation = documentChange.getDocument().getData().get("organisationName").toString();
                                int organisationId = Integer.parseInt(documentChange.getDocument().getData().get("organisationId").toString());

                                String sellerId = String.valueOf(documentChange.getDocument().getData().get("sellerID"));
                                String buyerId = String.valueOf(documentChange.getDocument().getData().get("buyerID"));
                                String sellerName = String.valueOf(documentChange.getDocument().getData().get("sellerName"));
                                String buyerName = String.valueOf(documentChange.getDocument().getData().get("buyerName"));


                                String productAddedTime = String.valueOf(documentChange.getDocument().getData().get("productAddedAt"));
                                String productSoldTime = String.valueOf(documentChange.getDocument().getData().get("productSoldTime"));

                                Products products = new Products(documentChange.getDocument().getId(), productName, productDesc, productPrice,
                                        "", sellerName, buyerName, productAddedTime, productSoldTime, false, productOrganisation
                                        , organisationId, productCategory, productTagsArrayList);

                                for (int i = 0; i < productTagsArrayList.size(); i++) {
                                    Log.e(TAG, "onEvent: " + productTagsArrayList.get(i).getTagName());
                                }
                                 productsArrayList.add(products);

                            } catch (Exception exception) {
                                Log.e(TAG, "onEvent: " + exception.getMessage());
                                exception.printStackTrace();
                            }

                            if (productsArrayList.size() > 0) {
                                mAdapter.notifyDataSetChanged();
                                avlLoader.setVisibility(View.GONE);
                            } else {
                                avlLoader.setVisibility(View.VISIBLE);
                            }
                        }

                    }
                }
            });

//        for (int i =0 ; i<productUserArrayList.size() ; i++){
//            getUserInfo(productUserArrayList.get(i).getProducts().getSellerID(), productUserArrayList.get(i) ,i);
//        }
        }

        private Users getProductOwner(String userID) {
            for (Users users1 : usersArrayList) {
                Log.e(TAG, "getProductOwner: " + users1.getUserID());
                if (users1.getUserID().equalsIgnoreCase(userID)) {
                    return users1;
                }
            }
            return null;
        }

        private void getUserInfo() {


            Users users = new Users();
            dRef.collection("users")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if (document.getData().isEmpty()) {
                                    } else {
                                        String userID = document.getData().get("userId").toString();
                                        String firstName = document.getData().get("firstName").toString();
                                        String lastName = document.getData().get("lastName").toString();
                                        String email = document.getData().get("email").toString();

                                        users.setUserID(userID);
                                        users.setFirstName(firstName);
                                        users.setLastName(lastName);
                                        users.setEmail(email);
                                        if (document.getData().containsKey("profilePic")) {
                                            users.setUserImage(document.getData().get("profilePic").toString());
                                        }
                                        String description = "";
                                        if (document.getData().get("description") != null) {
                                            users.setDescription(document.getData().get("description").toString());
                                        }

                                        String dob = "";
                                        if (document.getData().get("dob") != null) {
                                            users.setDescription(document.getData().get("dob").toString());
                                        }

                                        if (document.getData().get("address") != null) {
                                            users.setAddress(document.getData().get("address").toString());
                                        }

                                        if (document.getData().get("city") != null) {
                                            users.setCity(document.getData().get("city").toString());
                                        }

                                        if (document.getData().get("zipcode") != null) {
                                            users.setZipcode(document.getData().get("zipcode").toString());

                                        }
                                        updateTask(UserRoomDatabase.getDatabase(getContext()), users);
                                    }
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }

        @Override
        public void getUsersListSize(int size) {
            Log.e(TAG, "getUsersListSize: " + size);
        }

        @Override
        public List<Users> getUsersList(List<Users> usersList) {
            Log.e(TAG, "getUsersList: " + usersList.size());
            if (usersList.size() > 0) {
                usersArrayList = usersList;
            }
            return null;
        }

        public void updateTask(UserRoomDatabase userDb, Users users) {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "run: " + users.getUserID());
                    userDb.userDao().insertUsers(users);
                }
            });
        }


        public void getUser(UserRoomDatabase userDb, Users users) {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                Users users1 =    userDb.userDao().loadPersonById(users.getUserID());
                }
            });
        }
    }
