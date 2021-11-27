package com.example.ole.common;

import android.app.Application;

import com.example.ole.repository.SavedDataRepository;

public class ServiceLocator {
    private static ServiceLocator instance = null;
    SavedDataRepository savedDataRepository;

    private ServiceLocator(Application application) {
        savedDataRepository = new SavedDataRepository(application);
    }

    public static ServiceLocator getInstance(Application application) {
        if (instance == null) {
            synchronized(ServiceLocator.class) {
                instance = new ServiceLocator(application);
            }
        }
        return instance;
    }

    public SavedDataRepository getSavedDataRepository() {
        return savedDataRepository;
    }
}
