package com.cipher.sharesmilesandroid.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cipher.sharesmilesandroid.R;
import com.cipher.sharesmilesandroid.ShareSmilesSingleton;
import com.cipher.sharesmilesandroid.adapters.HomeAdapter;
import com.cipher.sharesmilesandroid.adapters.UserAddedProdAdapter;
import com.cipher.sharesmilesandroid.modals.ProductTags;
import com.cipher.sharesmilesandroid.modals.ProductUser;
import com.cipher.sharesmilesandroid.modals.Products;
import com.cipher.sharesmilesandroid.modals.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.rvUsers)
    RecyclerView rvUsers;

    private FirebaseFirestore dRef = FirebaseFirestore.getInstance();
    private static final String TAG = "CategoriesActivity";

    ArrayList<ProductUser> productUserArrayList = new ArrayList<>();
    AppCompatActivity mActivity = CategoriesActivity.this;
    UserAddedProdAdapter mAdapter;

    String data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        ButterKnife.bind(mActivity);

        if (getIntent().getExtras()!=null){
            data = getIntent().getStringExtra("data");
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(layoutManager);
        rvUsers.setLayoutManager(layoutManager);


        mAdapter = new UserAddedProdAdapter(mActivity, productUserArrayList);
        recyclerView.setAdapter(mAdapter);

        if (!data.equalsIgnoreCase("users")){
            loadProducts();
        }else {
            loadUsers();
        }


    }

    private void loadUsers() {
    }

    private void loadProducts() {

        dRef.collection("products").whereEqualTo("category", "Sports").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // ...

                        for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {

                            if (documentChange.getDocument().getData().isEmpty()) {
                                Log.e(TAG, "empty document: ");
                            } else {

                                try {
                                    ArrayList<ProductTags> productTagsArrayList = new ArrayList<>();
                                    String data = String.valueOf(documentChange.getDocument().getData().get("Tags"));
                                    if (!data.equalsIgnoreCase("[]")) {
                                        JSONArray dataArray = new JSONArray(data);
                                        for (int i = 0; i < dataArray.length(); i++) {
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

                                    String isSoldString = String.valueOf(documentChange.getDocument().getData().get("isSold"));

                                    boolean isSold = false;
                                    if (isSoldString.equalsIgnoreCase("true")) {
                                        isSold = true;
                                    }
                                    String itemImage = "";
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


                            }

                        }
                        if (productUserArrayList.size() > 0) {
                            mAdapter.notifyDataSetChanged();

                        } else {

                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private Users getProductOwner(String userID) {
        for (int i = ShareSmilesSingleton.usersArrayList.size()-1; i >=0 ; i--){
            if (ShareSmilesSingleton.usersArrayList.get(i).getUserID().equalsIgnoreCase(userID)){
                return ShareSmilesSingleton.usersArrayList.get(i);
            }
        }
        return null;
    }
}
