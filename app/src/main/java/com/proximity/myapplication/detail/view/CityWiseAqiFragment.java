package com.proximity.myapplication.detail.view;

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
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.proximity.myapplication.viewmodel.AqiViewModel;
import com.proximity.myapplication.R;
import com.proximity.myapplication.databinding.FragmentCityWiseAqiBinding;
import com.proximity.myapplication.detail.Holder;
import com.proximity.myapplication.home.model.AqiData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CityWiseAqiFragment  extends Fragment {

    private AqiViewModel mViewModel;
    private FragmentCityWiseAqiBinding cityWiseAqiBinding;
    private NavHostFragment navHostFragment;
    private Activity mHostActivity;
    Context mContext;
    ArrayList<AqiData> aqiDataArrayList = new ArrayList<>();
    LiveData<List<AqiData>> listLiveData;
    RecyclerView.LayoutManager layoutManager;
    String selectedCity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cityWiseAqiBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_city_wise_aqi, container, false);
        View view = cityWiseAqiBinding.getRoot();

        init();

        // connectWebSocket();
        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        mHostActivity = (Activity) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments()!=null) {
            CityWiseAqiFragmentArgs cityWiseAqiFragmentArgs = CityWiseAqiFragmentArgs.fromBundle(getArguments());
            selectedCity = cityWiseAqiFragmentArgs.getSelectedCity();
        }
    }

    private void init() {
        mViewModel = new ViewModelProvider(this).get(AqiViewModel.class);
        navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        cityWiseAqiBinding.vFullLoader.setVisibility(View.VISIBLE);
        cityWiseAqiBinding.vFullLoader.setmLoadingText("Creating the chart...");
        listLiveData = mViewModel.fetchAqiData(mHostActivity);

        listLiveData.observe(getActivity(), new Observer<List<AqiData>>() {
            @Override
            public void onChanged(List<AqiData> aqiData) {
                if (aqiData != null && !aqiData.isEmpty()) {
                    aqiDataArrayList = new ArrayList<>(aqiData);
                    setBarChartForCityAqi(aqiDataArrayList);
                }
            }
        });

    }

    private void setBarChartForCityAqi(ArrayList<AqiData> aqiDataArrayList) {

        for (int i = 0; i < aqiDataArrayList.size(); i++) {
            AqiData x = aqiDataArrayList.get(i);
            if (aqiDataArrayList.get(i).getCity().equals(selectedCity)) {

                DateFormat df = new SimpleDateFormat("h:mm:ss");
                String date = df.format(Calendar.getInstance().getTime());
                AqiData aqiData_fromServer = new AqiData(selectedCity,aqiDataArrayList.get(i).getAqi(),date);

                if (Holder.getInstance().getItemArray()!=null && Holder.getInstance().getItemArray().size()>8)
                {
                    Holder.getInstance().clearItemArray();
                }else {
                    Holder.getInstance().addItemToArray(aqiData_fromServer);

                }
            }
        }

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<String> theTime = new ArrayList<>();
        if (Holder.getInstance().getItemArray().size()>2){
            ArrayList<AqiData> saved_list = Holder.getInstance().getItemArray();

            float k = 1f;
            for (int j=0;j<saved_list.size();j++){

                barEntries.add(new BarEntry(k,Float.parseFloat(saved_list.get(j).getAqi())));
                theTime.add(saved_list.get(j).getTime());
              k++;
            }
        }
        if (Holder.getInstance().getItemArray().size()>2) {
            BarDataSet barDataSet = new BarDataSet(barEntries, "AQI");
            //setBarColor(barDataSet,Holder.getInstance().getItemArray());
            cityWiseAqiBinding.barchart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(theTime));
            BarData theData = new BarData(barDataSet);//----Line of error
            cityWiseAqiBinding.barchart.setData(theData);
            cityWiseAqiBinding.barchart.setTouchEnabled(true);
            cityWiseAqiBinding.barchart.setDragEnabled(true);
            cityWiseAqiBinding.barchart.setScaleEnabled(true);
            cityWiseAqiBinding.barchart.invalidate();
            cityWiseAqiBinding.barchart.refreshDrawableState();

            if (cityWiseAqiBinding.barchart.isShown() == true) {
                cityWiseAqiBinding.vFullLoader.setVisibility(View.GONE);
            } }
    }

    /*private void setBarColor(BarDataSet barDataSet, ArrayList<AqiData> barEntries) {
        for (int l=0;l<barEntries.size();l++){
            if (Double.parseDouble(barEntries.get(l).getAqi())<=50){
                barDataSet.setColor(mContext.getResources().getColor(R.color.Good));
            }else if (Double.parseDouble(barEntries.get(l).getAqi())>51 && Double.parseDouble(barEntries.get(l).getAqi())<=100){
                barDataSet.setColor(mContext.getResources().getColor(R.color.Satisfactory));
            }else if (Double.parseDouble(barEntries.get(l).getAqi())>101 && Double.parseDouble(barEntries.get(l).getAqi())<=200){
                barDataSet.setColor(mContext.getResources().getColor(R.color.Moderate));
            }else if (Double.parseDouble(barEntries.get(l).getAqi())>201 && Double.parseDouble(barEntries.get(l).getAqi())<=300){
                barDataSet.setColor(mContext.getResources().getColor(R.color.Poor));
            }else if (Double.parseDouble(barEntries.get(l).getAqi())>301 && Double.parseDouble(barEntries.get(l).getAqi())<=400){
                barDataSet.setColor(mContext.getResources().getColor(R.color.VPoor));
            }else if (Double.parseDouble(barEntries.get(l).getAqi())>401 && Double.parseDouble(barEntries.get(l).getAqi())<=500){
                barDataSet.setColor(mContext.getResources().getColor(R.color.Severe));
            }
        }
    }*/
}