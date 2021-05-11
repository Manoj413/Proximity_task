package com.proximity.myapplication.home.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.proximity.myapplication.viewmodel.AqiViewModel;
import com.proximity.myapplication.R;
import com.proximity.myapplication.databinding.FragmentCityAqiListBinding;
import com.proximity.myapplication.home.InterfaceCityClick;
import com.proximity.myapplication.home.adapter.CityAqiAdapter;
import com.proximity.myapplication.home.model.AqiData;

import org.java_websocket.client.WebSocketClient;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AqiListFragment  extends Fragment implements InterfaceCityClick {

    private AqiViewModel mViewModel;
    private FragmentCityAqiListBinding cityAqiListBinding;
    private NavHostFragment navHostFragment;
    private Activity mHostActivity;
    Context mContext;
    private WebSocketClient mWebSocketClient;
    LiveData<List<AqiData>> listLiveData;
    CityAqiAdapter cityAqiAdapter;
    ArrayList<AqiData> aqiDataArrayList = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    AqiListFragmentDirections .ActionAqiListFragmentToCityWiseAqiFragment action;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cityAqiListBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_city_aqi_list, container, false);
        View view = cityAqiListBinding.getRoot();

        init();

       // connectWebSocket();
        return view;
    }

    private void attachRecyclerView() {
        cityAqiAdapter = new CityAqiAdapter(getContext(),aqiDataArrayList,this);

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        cityAqiListBinding.rVAqi.setLayoutManager(layoutManager);
        cityAqiListBinding.rVAqi.setAdapter(cityAqiAdapter);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext=context;
        mHostActivity = (Activity) context;
    }

    private void init() {
        mViewModel = new ViewModelProvider(this).get(AqiViewModel.class);
        navHostFragment= (NavHostFragment)getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

         action =  AqiListFragmentDirections.actionAqiListFragmentToCityWiseAqiFragment();
        listLiveData = mViewModel.fetchAqiData(mHostActivity);
        attachRecyclerView();
        listLiveData.observe(getActivity(), new Observer<List<AqiData>>() {
            @Override
            public void onChanged(List<AqiData> aqiData) {
               if (aqiData!=null && !aqiData.isEmpty()) {
                   aqiDataArrayList = new ArrayList<>(aqiData);
                   setAqiAdapter(aqiDataArrayList);
               }
            }
        });

    }
    private void setAqiAdapter(ArrayList<AqiData> results) {
        cityAqiAdapter.setAqiData(results);

    }


    @Override
    public void onClickCityAction(String city) {
        NavController navController = NavHostFragment.findNavController(AqiListFragment.this);
       action.setSelectedCity(city);
       navController.navigate(action);
    }
}