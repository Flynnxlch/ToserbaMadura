package com.example.toserbamadura.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toserbamadura.R;
import com.example.toserbamadura.adapter.HomeAdapter;
import com.example.toserbamadura.adapter.PopAdapter;
import com.example.toserbamadura.adapter.RecAdapter;
import com.example.toserbamadura.databinding.FragmentHomeBinding;
import com.example.toserbamadura.models.PopulerMod;
import com.example.toserbamadura.models.RecModel;
import com.example.toserbamadura.ui.category.CategoryFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class HomeFragment extends Fragment {

    ScrollView scrollView;
    ProgressBar progressBar;

    RecyclerView popularRec,homeCatRec,recommendRec;
    FirebaseFirestore db;
    //populer menu
    List<PopulerMod> populerModList;
    PopAdapter popAdapter;

    //Home Category
    List<HomeCategory> categoryList;
    HomeAdapter homeAdapter;

    //Recommended
    List<RecModel> recModelList;
    RecAdapter recAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        db = FirebaseFirestore.getInstance();
        popularRec = root.findViewById(R.id.popmenu);
        homeCatRec = root.findViewById(R.id.popexp);
        recommendRec = root.findViewById(R.id.popreco);
        scrollView = root.findViewById(R.id.scroll_view);
        progressBar = root.findViewById(R.id.progressbarHome);

        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        //Popular menu
        popularRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        populerModList = new ArrayList<>();
        popAdapter = new PopAdapter(getActivity(),populerModList);
        popularRec.setAdapter(popAdapter);

        db.collection("PopularMenu")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                PopulerMod populerMod = document.toObject(PopulerMod.class);
                                populerModList.add(populerMod);
                                popAdapter.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(getActivity(),"Error"+task.getException(),Toast.LENGTH_SHORT);
                        }
                    }
                });

        //Home Category
        homeCatRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryList = new ArrayList<>();
        homeAdapter = new HomeAdapter(getActivity(),categoryList);
        homeCatRec.setAdapter(homeAdapter);

        db.collection("HomeCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                HomeCategory homeCategory = document.toObject(HomeCategory.class);
                                categoryList.add(homeCategory);
                                homeAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(),"Error"+task.getException(),Toast.LENGTH_SHORT);
                        }
                    }
                });
        //Recommend Category
        recommendRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        recModelList = new ArrayList<>();
        recAdapter = new RecAdapter(getActivity(),recModelList);
        recommendRec.setAdapter(recAdapter);

        db.collection("Recommend")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                RecModel recModel = document.toObject(RecModel.class);
                                recModelList.add(recModel);
                                recAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(),"Error"+task.getException(),Toast.LENGTH_SHORT);
                        }
                    }
                });
        return root;
    }
}