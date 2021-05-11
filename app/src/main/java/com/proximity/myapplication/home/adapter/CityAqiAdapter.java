package com.proximity.myapplication.home.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proximity.myapplication.R;
import com.proximity.myapplication.databinding.AdapterCityAqiBinding;
import com.proximity.myapplication.home.InterfaceCityClick;
import com.proximity.myapplication.home.model.AqiData;

import java.util.ArrayList;
import java.util.List;

public class CityAqiAdapter extends RecyclerView.Adapter<CityAqiAdapter.ViewHolder>
{
    private Context context;
    private ArrayList<AqiData> aqiDataList = new ArrayList<>();
    AdapterCityAqiBinding cityAqiBinding;
    InterfaceCityClick mInterfaceCityClick;

    public CityAqiAdapter(Context context,ArrayList<AqiData> aqiDataList,InterfaceCityClick interfaceCityClick) {
        this.context = context;
        this.aqiDataList = aqiDataList;
        this.mInterfaceCityClick=interfaceCityClick;
    }

    public CityAqiAdapter() {
    }

    @NonNull
    @Override
    public CityAqiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       /* View view = LayoutInflater.from(context).inflate(R.layout.adapter_prediction_category,parent,false);
        return new CategoryAdapter.ViewHolder(view);*/
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        cityAqiBinding = AdapterCityAqiBinding.inflate(layoutInflater, parent, false);
        return new CityAqiAdapter.ViewHolder(cityAqiBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CityAqiAdapter.ViewHolder holder, int position) {

        holder.bind(aqiDataList.get(position));

    }


    @Override
    public int getItemCount() {
        return aqiDataList.size();
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(String.valueOf(aqiDataList.get(position)));
    }

    public void setAqiData(ArrayList<AqiData>  mBooksArrayList){
        aqiDataList = mBooksArrayList;
        notifyDataSetChanged();

    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AqiData aqiData;

        private AdapterCityAqiBinding mBinding;
        ViewHolder(AdapterCityAqiBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        void bind(AqiData data)
        {
            this.aqiData = data;
            mBinding.txtCityName.setText("City- "+aqiData.getCity());
            mBinding.txtAqi.setText("AQI- "+aqiData.getAqi());
            mBinding.cLInside.setOnClickListener(this);
            if (Double.parseDouble(aqiData.getAqi())<=50){
                mBinding.txtAqi.setTextColor(context.getResources().getColor(R.color.Good));
            }else if (Double.parseDouble(aqiData.getAqi())>51 && Double.parseDouble(aqiData.getAqi())<=100){
                mBinding.txtAqi.setTextColor(context.getResources().getColor(R.color.Satisfactory));
            }else if (Double.parseDouble(aqiData.getAqi())>101 && Double.parseDouble(aqiData.getAqi())<=200){
                mBinding.txtAqi.setTextColor(context.getResources().getColor(R.color.Moderate));
            }else if (Double.parseDouble(aqiData.getAqi())>201 && Double.parseDouble(aqiData.getAqi())<=300){
                mBinding.txtAqi.setTextColor(context.getResources().getColor(R.color.Poor));
            }else if (Double.parseDouble(aqiData.getAqi())>301 && Double.parseDouble(aqiData.getAqi())<=400){
                mBinding.txtAqi.setTextColor(context.getResources().getColor(R.color.VPoor));
            }else if (Double.parseDouble(aqiData.getAqi())>401 && Double.parseDouble(aqiData.getAqi())<=500){
                mBinding.txtAqi.setTextColor(context.getResources().getColor(R.color.Severe));
            }
        }


        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.cLInside:
                    mInterfaceCityClick.onClickCityAction(aqiData.getCity());
                    break;
            }
        }
    }

}



