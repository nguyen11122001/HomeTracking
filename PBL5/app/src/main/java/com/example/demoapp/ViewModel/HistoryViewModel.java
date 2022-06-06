package com.example.demoapp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.demoapp.Model.History;

import java.util.List;

public class HistoryViewModel extends ViewModel {
    private MutableLiveData<List<History>> users;
    public LiveData<List<History>> getUsers() {
        if (users == null) {
            users = new MutableLiveData<List<History>>();
            loadHistories();
        }
        return users;
    }

    private void loadHistories() {
        // Do an asynchronous operation to fetch users.
    }
}
