package com.example.toserbamadura.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toserbamadura.R;
import com.example.toserbamadura.adapter.NavCategAdapater;
import com.example.toserbamadura.adapter.PopAdapter;
import com.example.toserbamadura.models.NavCatModel;
import com.example.toserbamadura.models.PopulerMod;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    FirebaseFirestore db;
    RecyclerView recyclerView;
    List<NavCatModel> catModelList;
    NavCategAdapater navCategAdapater;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_category, container, false);
        db = FirebaseFirestore.getInstance();
        recyclerView = root.findViewById(R.id.cat_recy);
        //Popular menu
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        catModelList = new ArrayList<>();
        navCategAdapater = new NavCategAdapater(getActivity(),catModelList);
        recyclerView.setAdapter(navCategAdapater);

        db.collection("NavCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                NavCatModel navCatModel = document.toObject(NavCatModel.class);
                                catModelList.add(navCatModel);
                                navCategAdapater.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(),"Error"+task.getException(),Toast.LENGTH_SHORT);
                        }
                    }
                });
        return root;
    }
}