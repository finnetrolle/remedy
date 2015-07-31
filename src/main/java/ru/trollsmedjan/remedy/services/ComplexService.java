package ru.trollsmedjan.remedy.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.trollsmedjan.remedy.dto.BeaconDTO;
import ru.trollsmedjan.remedy.dto.CampaignDTO;
import ru.trollsmedjan.remedy.dto.EntoserDTO;
import ru.trollsmedjan.remedy.dto.PrimaryDTO;
import ru.trollsmedjan.remedy.exception.RemedyDataLayerException;
import ru.trollsmedjan.remedy.model.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by finnetrolle on 31.07.2015.
 */
@Service
public class ComplexService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OptionalDataProvider db;

    public CampaignDTO getFullCampaignDTO(Long campaignid) throws RemedyDataLayerException {
        Campaign campaign = db.getCampaign(campaignid)
                .orElseThrow(RemedyDataLayerException::new);
        CampaignDTO campaignDTO = new CampaignDTO(campaign.getName(), campaign.getConstellation().getName(), campaignid);

        campaignDTO.setPrimaries(new ArrayList<>());
        for (PrimaryGoal primary : db.findPrimaryByCampaign(campaign)) {
            PrimaryDTO primaryDTO = new PrimaryDTO(primary.getName(), primary.getId());
            primaryDTO.setBeacons(new ArrayList<>());
            for (Beacon beacon : db.findBeaconsByPrimary(primary)) {
                BeaconDTO beaconDTO = new BeaconDTO(
                        beacon.getId(),
                        beacon.getName(),
                        beacon.getLocation().getName(),
                        beacon.getStatus(),
                        (beacon.getEntoser() == null) ? null : beacon.getEntoser().getName(),
                        beacon.getStartTime(),
                        beacon.getTimeToCapture(),
                        beacon.getPrimaryGoal().getName());
                beaconDTO.setPrimaryId(beacon.getPrimaryGoal().getId());
                primaryDTO.getBeacons().add(beaconDTO);
            }
            campaignDTO.getPrimaries().add(primaryDTO);
        }

        campaignDTO.setEntosers(new ArrayList<>());
        for (Entoser entoser : db.findEntoserByCampaign(campaign)) {
            EntoserDTO entoserDTO = new EntoserDTO(
                    entoser.getId(),
                    entoser.getName(),
                    entoser.getShip(),
                    entoser.isCapitalShip(),
                    entoser.isT2EntosisModule(),
                    (entoser.getEngaging() == null) ? null : entoser.getEngaging().getName());
            campaignDTO.getEntosers().add(entoserDTO);
        }

        campaignDTO.setSolarSystems(new ArrayList<>());
        for (SolarSystem s : campaign.getConstellation().getSolarSystems()) {
            campaignDTO.getSolarSystems().add(s.getName());
        }
        return campaignDTO;
    }

    public List<BeaconDTO> getBeaconsForCampaign(Long campaignid) throws RemedyDataLayerException {
        List<PrimaryGoal> primaries = db.findPrimaryByCampaign(
                db.getCampaign(campaignid).orElseThrow(RemedyDataLayerException::new));
        List<BeaconDTO> beaconDTOs = new ArrayList<>();
        for (PrimaryGoal p : primaries) {
            beaconDTOs.addAll(db.findBeaconsByPrimary(p).stream().map(beacon -> {
                BeaconDTO beaconDTO = new BeaconDTO(
                        beacon.getId(),
                        beacon.getName(),
                        beacon.getLocation().getName(),
                        beacon.getStatus(),
                        (beacon.getEntoser() == null) ? null : beacon.getEntoser().getName(),
                        beacon.getStartTime(),
                        beacon.getTimeToCapture(),
                        beacon.getPrimaryGoal().getName());
                beaconDTO.setPrimaryId(beacon.getPrimaryGoal().getId());
                return beaconDTO;
            }).collect(Collectors.toList()));
        }
        return beaconDTOs;
    }
}
