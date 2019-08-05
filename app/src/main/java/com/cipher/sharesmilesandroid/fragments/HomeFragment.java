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
    import com.cipher.sharesmilesandroid.modals.ProductTags;
    import com.cipher.sharesmilesandroid.modals.Products;
    import com.google.android.gms.tasks.OnFailureListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.firestore.DocumentChange;
    import com.google.firebase.firestore.EventListener;
    import com.google.firebase.firestore.FirebaseFirestore;
    import com.google.firebase.firestore.FirebaseFirestoreException;
    import com.google.firebase.firestore.QuerySnapshot;
    import com.wang.avi.AVLoadingIndicatorView;

    import java.lang.reflect.Type;
    import java.util.ArrayList;
    import java.util.List;

    import static com.facebook.FacebookSdk.getApplicationContext;

    public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    private FirebaseAuth auth;

    private FirebaseFirestore dRef = FirebaseFirestore.getInstance();

    ArrayList<Products> productsArrayList = new ArrayList<>();


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

                }

                for (DocumentChange documentChange : documentSnapshots.getDocumentChanges())
                {
                    if (documentChange.getDocument().getData().isEmpty()){

                    }else {

                        try {


                            ArrayList<ProductTags> productTagsArrayList = new ArrayList<>();

                            ArrayList<ProductTags>   stringArrayList = (ArrayList<ProductTags>) documentChange.getDocument().getData().get("Tags");

//                            for(ProductTags item : stringArrayList) {
//                                productTagsArrayList.add(item);
//                            }


                            String   productName =  documentChange.getDocument().getData().get("productName").toString();
                            String   productDesc =  documentChange.getDocument().getData().get("productDesc").toString();
                            String   productPrice =  documentChange.getDocument().getData().get("price").toString();
                            String   productCategory =  documentChange.getDocument().getData().get("category").toString();

                            String   productOrganisation =  documentChange.getDocument().getData().get("organisationName").toString();
                            int   organisationId = Integer.parseInt(documentChange.getDocument().getData().get("organisationId").toString());

                            String   sellerId =  documentChange.getDocument().getData().get("sellerID").toString();
                            String   buyerId =  documentChange.getDocument().getData().get("buyerID").toString();

    //                            long   productAddedTime = Long.parseLong(documentChange.getDocument().getData().get("productName").toString());
    //                            long   productSoldTime = Long.parseLong(documentChange.getDocument().getData().get("productName").toString());

                            Products products = new Products( documentChange.getDocument().getId(),productName, productDesc,productPrice,
                                    "",sellerId,buyerId,1565025442,1565025442,false,productOrganisation
                            ,organisationId,productCategory, productTagsArrayList);

                            for (int i=0 ;i<productTagsArrayList.size();i++) {
                                Log.e(TAG, "onEvent: "+productTagsArrayList.get(i).getTagName() );
                            }
                            productsArrayList.add(products);

                        }catch (Exception exception){
                            exception.printStackTrace();
                        }
                        if (productsArrayList.size()>0) {
                            mAdapter.notifyDataSetChanged();
                            avlLoader.setVisibility(View.GONE);
                        }else {
                            avlLoader.setVisibility(View.VISIBLE);
                        }

                    }

                }
            }
        });
    }

    }
