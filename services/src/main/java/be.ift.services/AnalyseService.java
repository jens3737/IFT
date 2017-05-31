package be.ift.services;

import be.ift.domain.Stagiair;

import java.util.List;

/**
 * Created by JHNBD42 on 8/05/2017.
 */
public interface AnalyseService {
    List<Stagiair> getAllStagiairesPerSchool(Integer schoolID, Integer categorieID);
}
