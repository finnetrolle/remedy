package ru.trollsmedjan.remedy.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by finnetrolle on 29.07.2015.
 */
@Service
public class EveSovApiService {

    public double getSystemSecurityModifier(String systemName) {
        return 2.5;
    }

}
