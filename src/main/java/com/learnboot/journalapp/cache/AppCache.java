package com.learnboot.journalapp.cache;

import com.learnboot.journalapp.entity.ConfigJournalApp;
import com.learnboot.journalapp.repository.ConfigJournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum keys {
        WEATHER_API
    }

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    public Map<String, String> appCache;

    @PostConstruct
    public void init() {
        appCache = new HashMap<>();
        List<ConfigJournalApp> configJournalApps = configJournalAppRepository.findAll();
        for (ConfigJournalApp configJournalApp : configJournalApps) {
            appCache.put(configJournalApp.getKey(), configJournalApp.getValue());
        }
    }

}
