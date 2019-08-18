    package com.cipher.sharesmilesandroid.fragments;

    import android.content.Intent;
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

    import com.cipher.sharesmilesandroid.ShareSmilesSingleton;
    import com.cipher.sharesmilesandroid.activities.DetailActivity;
    import com.cipher.sharesmilesandroid.adapters.HomeAdapter;
    import com.cipher.sharesmilesandroid.databases.AppExecutors;

    import com.cipher.sharesmilesandroid.databases.RoomDBCallBacks;
    import com.cipher.sharesmilesandroid.databases.UserRoomDatabase;
    import com.cipher.sharesmilesandroid.interfaces.Clicklisteners;
    import com.cipher.sharesmilesandroid.modals.ProductTags;

    import com.cipher.sharesmilesandroid.modals.ProductUser;
    import com.cipher.sharesmilesandroid.modals.Products;
    import com.cipher.sharesmilesandroid.modals.Users;
    import com.google.firebase.firestore.DocumentChange;


    import com.google.firebase.firestore.EventListener;
    import com.google.firebase.firestore.FirebaseFirestore;
    import com.google.firebase.firestore.FirebaseFirestoreException;

    import com.google.firebase.firestore.QuerySnapshot;
    import com.wang.avi.AVLoadingIndicatorView;


    import org.json.JSONArray;
    import org.json.JSONObject;

    import java.util.ArrayList;
    import java.util.List;

    public class HomeFragment extends Fragment implements RoomDBCallBacks , Clicklisteners {

        private RecyclerView recyclerView;
        private RecyclerView.Adapter mAdapter;
        private RecyclerView.LayoutManager layoutManager;

        private FirebaseFirestore dRef = FirebaseFirestore.getInstance();

        private ArrayList<ProductUser> productUserArrayList = new ArrayList<>();
        private List<Users> usersArrayList = new ArrayList<>();

        private AVLoadingIndicatorView avlLoader;
        private static final String TAG = "HomeFragment";

        private RoomDBCallBacks roomDBCallBacks;

        Clicklisteners clicklisteners;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.home_fragment,
                    container, false);


            recyclerView = view.findViewById(R.id.recyclerView);
            avlLoader = view.findViewById(R.id.avlLoader);

            clicklisteners = this;

            // use a linear layout manager
            layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);

            // specify an adapter (see also next example)
            avlLoader.setVisibility(View.VISIBLE);
            mAdapter = new HomeAdapter(getActivity(), productUserArrayList , clicklisteners);
            recyclerView.setAdapter(mAdapter);

            roomDBCallBacks = (RoomDBCallBacks) getContext();


            getListItems();

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
                                String data = String.valueOf(documentChange.getDocument().getData().get("Tags"));
                                if (!data.equalsIgnoreCase("[]")){
                                    JSONArray dataArray = new JSONArray(data);
                                    for (int i = 0 ; i<dataArray.length() ; i++){
                                        JSONObject dataObject = dataArray.getJSONObject(i);

                                        ProductTags productTags = new ProductTags();
                                        productTags.setTagName(dataObject.getString("text"));
                                        productTagsArrayList.add(productTags);
                                    }
                                }


                                String userID = documentChange.getDocument().getData().get("userId").toString();

                                String productName = documentChange.getDocument().getData().get("productName").toString();
                                String productDesc = documentChange.getDocument().getData().get("productDesc").toString();
                                String productPrice = documentChange.getDocument().getData().get("price").toString();
                                String productCategory = documentChange.getDocument().getData().get("category").toString();

                                String productOrganisation = documentChange.getDocument().getData().get("organisationName").toString();
                                int organisationId = Integer.parseInt(documentChange.getDocument().getData().get("organisationId").toString());

                                String sellerId = String.valueOf(documentChange.getDocument().getData().get("sellerID"));
                                String buyerId = String.valueOf(documentChange.getDocument().getData().get("buyerID"));

                                String isSoldString =String.valueOf(documentChange.getDocument().getData().get("isSold"));

                                boolean isSold = false;
                                if (isSoldString.equalsIgnoreCase("true")){
                                    isSold = true;
                                }
                                String itemImage= "";
                                if (documentChange.getDocument().getData().containsKey("itemImage")) {
                                    itemImage = String.valueOf(documentChange.getDocument().getData().get("itemImage"));
                                }

                                String productAddedTime = String.valueOf(documentChange.getDocument().getData().get("productAddedAt"));
                                String productSoldTime = String.valueOf(documentChange.getDocument().getData().get("productSoldTime"));

                                Products products = new Products(documentChange.getDocument().getId(), productName, productDesc, productPrice,
                                        itemImage, sellerId, buyerId, productAddedTime, productSoldTime, isSold, productOrganisation
                                        , organisationId, productCategory, productTagsArrayList);

                                Users users = getProductOwner(userID);
                                ProductUser productUser = new ProductUser();
                                productUser.setUsers(users);
                                productUser.setProducts(products);
                                productUserArrayList.add(productUser);

                            } catch (Exception exception) {
                                Log.e(TAG, "onEvent: " + exception.getMessage());
                                exception.printStackTrace();
                            }

                            if (productUserArrayList.size() > 0) {
                                mAdapter.notifyDataSetChanged();
                                avlLoader.setVisibility(View.GONE);
                            } else {
                                avlLoader.setVisibility(View.VISIBLE);
                            }
                        }

                    }
                }
            });
        }

        private Users getProductOwner(String userID) {
           for (int i =ShareSmilesSingleton.usersArrayList.size()-1 ; i >=0 ; i--){
               if (ShareSmilesSingleton.usersArrayList.get(i).getUserID().equalsIgnoreCase(userID)){
                   return ShareSmilesSingleton.usersArrayList.get(i);
               }
           }
            return null;
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

        @Override
        public void onClickListeners(int position) {
            Intent intent = new Intent(getActivity() , DetailActivity.class);
            intent.putExtra("data",productUserArrayList.get(position));
//                intent.putExtra("data", productUserArrayList.get(position));
            startActivity(intent);
        }
    }
