package com.recruitmentbackend.recruitmentbackend.services;

import com.recruitmentbackend.recruitmentbackend.controller.requests.ApplyForJobRequest;
import com.recruitmentbackend.recruitmentbackend.controller.requests.CreateNewJobOfferRequest;
import com.recruitmentbackend.recruitmentbackend.models.Candidate;
import com.recruitmentbackend.recruitmentbackend.models.Competence;
import com.recruitmentbackend.recruitmentbackend.models.JobOffer;
import com.recruitmentbackend.recruitmentbackend.models.Recruitment;
import com.recruitmentbackend.recruitmentbackend.repositories.CandidateRepository;
import com.recruitmentbackend.recruitmentbackend.repositories.CompetenceRepository;
import com.recruitmentbackend.recruitmentbackend.repositories.JobOfferRepository;
import com.recruitmentbackend.recruitmentbackend.utils.ServiceHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by Patrik Melander
 * Date: 2022-01-18
 * Time: 15:04
 * Project: Recruitment-Backend
 * Copyright: MIT
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class JobOfferService {

    private final JobOfferRepository jobRepo;
    private final ServiceHelper serviceHelper;
    private final CandidateRepository candidateRepo;
    private final JobOfferRepository jobOfferRepo;
    LocalDate date;

    public List<JobOffer> getAllJobOffers() {
        log.info("Fetching all jobOffers");
        return jobRepo.findAll();
    }

    @Transactional
    public String createJobOffer(CreateNewJobOfferRequest request) {
        JobOffer newJobOffer = new JobOffer();

        newJobOffer.setTitle(request.getTitle());
        newJobOffer.setPublishDate(date.now());
        newJobOffer.setApplyDate(request.getApplyDate());
        newJobOffer.setPreview(request.getPreview());
        newJobOffer.setCompanyDescription(request.getCompanyDescription());
        newJobOffer.setAboutRole(request.getAboutRole());

        newJobOffer.setLocation(request.getLocation());



        jobRepo.saveAndFlush(newJobOffer);
        System.out.println(newJobOffer.toString());
        List<Competence> competenceList = serviceHelper.fillCompetenceList(request, newJobOffer);
        List<Recruitment> recruitmentList = serviceHelper.defaultRecruitmentStepsList(newJobOffer);
        newJobOffer.setRecruitmentList(recruitmentList);
        newJobOffer.setCompetenceList(competenceList);

        jobRepo.saveAndFlush(newJobOffer);

        final String msg = String.format("JobOffer created: %s", newJobOffer.getId());
        log.info(msg);
        return msg;
    }

    @Transactional
    public String applyForJob(ApplyForJobRequest request) {
        Candidate candidate = serviceHelper.getCandidateById(request.getCandidateId());
        JobOffer jobOffer = serviceHelper.getJobOfferById(request.getJobOfferId());

        if(serviceHelper.alreadyApplied(candidate, jobOffer)){
            final String msg = String.format("%s already applied for: %s", candidate.getId(), jobOffer.getId());
            log.info(msg);
            return msg;
        }

        candidate.setRecruitment(jobOffer.getRecruitmentList().get(0));
        candidateRepo.saveAndFlush(candidate);
        jobOffer.getRecruitmentList().get(0).getCandidateList().add(candidate);

        jobRepo.saveAndFlush(jobOffer);

        final String msg = String.format("%s applied for: %s", candidate.getId(), jobOffer.getId());
        log.info(msg);
        return msg;

    }
}
