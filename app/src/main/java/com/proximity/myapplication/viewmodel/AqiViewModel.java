package com.proximity.myapplication.viewmodel;

import android.app.Activity;
import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.proximity.myapplication.home.model.AqiData;
import com.proximity.myapplication.repository.AqiRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.disposables.CompositeDisposable;

@HiltViewModel
public class AqiViewModel extends ViewModel {
    AqiRepository aqiRepository;
    Application application;
    private CompositeDisposable disposable;
    private MutableLiveData<Boolean> repoLoadError = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private MutableLiveData<List<AqiData>> aqiLiveData = new MutableLiveData<>();

    @Inject
    public AqiViewModel(Application application,AqiRepository aqiRepository) {
        this.application = application;
        this.aqiRepository = aqiRepository;
    }
    private void fetchLoaderState() {
        loading = aqiRepository.getLoadingStateFromProductRepo();
    }


    private void fetchLoadError() {
        repoLoadError = aqiRepository.getErrorStateFromProductRepo();
    }

    LiveData<Boolean> getError() {
        return repoLoadError;
    }
    public LiveData<Boolean> getLoading() {
        fetchLoaderState();
        return loading;
    }

    public LiveData<List<AqiData>> fetchAqiData(Activity activity){
        aqiLiveData = aqiRepository.fetchAqiData(activity);
        return aqiLiveData;
    }
}


