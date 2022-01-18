package com.recruitmentbackend.recruitmentbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.recruitmentbackend.recruitmentbackend.controller.AppConstants.API_MAPPING.*;
/**
 * Created by Patrik Melander
 * Date: 2022-01-18
 * Time: 14:27
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@RestController
@RequestMapping(BASE_API + ADMIN)
@RequiredArgsConstructor
public class AdminController {
    //ToDo: Hämta recruitment, inparam jobOffer_id

    //ToDo: Skapa JobOffer, inparam Body av joboffer

    //ToDo: Skapa Admin, inparam Body av Candidate

    //ToDo: Uppdatera RecruitmentListors ordning, inparam Body av JobOffer, (uppdatera JobOffer i frontend och skicka in den färdiga listan)

    //ToDo: Uppdatera RecruitmentListors kandidater, inparam Body av JobOffer, (uppdatera JobOffer i frontend och skicka in de färdiga listorna där kandidaterna fått ny plats)

    //ToDo: SÄtt Rate på Candidate, inparam JobOffer_id, Candidate_id, Rate

    //ToDo: Ändra visningsnamn på Candidates, inparam candidate_id, showedNickname



}
