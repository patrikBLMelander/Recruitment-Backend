package com.recruitmentbackend.recruitmentbackend.services;

import com.recruitmentbackend.recruitmentbackend.controller.requests.ApplyForJobRequest;
import com.recruitmentbackend.recruitmentbackend.controller.requests.CreateNewJobOfferRequest;
import com.recruitmentbackend.recruitmentbackend.controller.requests.SetRateOnCandidateRequest;
import com.recruitmentbackend.recruitmentbackend.models.*;
import com.recruitmentbackend.recruitmentbackend.repositories.CandidateRepository;
import com.recruitmentbackend.recruitmentbackend.repositories.JobOfferRepository;
import com.recruitmentbackend.recruitmentbackend.repositories.RateRepository;
import com.recruitmentbackend.recruitmentbackend.utils.ServiceHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
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
    private final RateRepository rateRepo;

    public List<JobOffer> getAllJobOffers() {
        log.info("Fetching all jobOffers");
        return jobRepo.findAll();
    }

    @Transactional
    public String createJobOffer(CreateNewJobOfferRequest request) {
        JobOffer newJobOffer = new JobOffer();

        newJobOffer.setTitle(request.getTitle());
        newJobOffer.setPublishDate(LocalDate.now());
        newJobOffer.setApplyDate(request.getApplyDate());
        newJobOffer.setPreview(request.getPreview());
        newJobOffer.setCompanyDescription(request.getCompanyDescription());
        newJobOffer.setAboutRole(request.getAboutRole());

        newJobOffer.setLocation(request.getLocation());



        jobRepo.saveAndFlush(newJobOffer);
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
    @Transactional
    public String setNewRate(SetRateOnCandidateRequest request) {
        Candidate candidate = serviceHelper.getCandidateById(request.getCandidateId());

        for (Rate r : candidate.getRates()) {
            if(r.getJobOfferId().equals(request.getJobOfferId())){

                r.setValue(request.getRate());
                final String msg = String.format("%s rate for: %s is now updated to %s", candidate.getId(), request.getJobOfferId(), request.getRate());
                log.info(msg);
                return msg;
            }
        }

        Rate newRate = new Rate();
        newRate.setCandidate(candidate);
        newRate.setJobOfferId(request.getJobOfferId().toString());
        newRate.setValue(request.getRate());

        rateRepo.saveAndFlush(newRate);

        candidate.getRates().add(newRate);

        candidateRepo.saveAndFlush(candidate);


        final String msg = String.format("%s rate for: %s is now set to %s", candidate.getId(), request.getJobOfferId(), request.getRate());
        log.info(msg);
        return msg;
    }

}
