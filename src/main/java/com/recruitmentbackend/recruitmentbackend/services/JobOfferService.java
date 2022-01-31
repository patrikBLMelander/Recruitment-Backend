package com.recruitmentbackend.recruitmentbackend.services;

import com.recruitmentbackend.recruitmentbackend.controller.requests.*;
import com.recruitmentbackend.recruitmentbackend.models.*;
import com.recruitmentbackend.recruitmentbackend.models.DTO.CandidateJobOfferDTO;
import com.recruitmentbackend.recruitmentbackend.repositories.CandidateRepository;
import com.recruitmentbackend.recruitmentbackend.repositories.JobOfferRepository;
import com.recruitmentbackend.recruitmentbackend.repositories.RateRepository;
import com.recruitmentbackend.recruitmentbackend.repositories.RecruitmentRepository;
import com.recruitmentbackend.recruitmentbackend.utils.ServiceHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


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
    private final RecruitmentRepository recruitmentRepo;

    public List<CandidateJobOfferDTO> getAllJobOffers() {
        List<JobOffer> jobOffers = jobRepo.findAll();
        List<CandidateJobOfferDTO> jobOfferDTOList = new ArrayList<>();

        for (JobOffer j : jobOffers) {
            CandidateJobOfferDTO jobOfferDTO = serviceHelper.transferJobOfferToJobOfferDTO(j);
            jobOfferDTOList.add(jobOfferDTO);
        }
        log.info("Fetching all jobOffers");
        return jobOfferDTOList;
    }

    @Transactional
    public String createJobOffer(CreateNewJobOfferRequest request) {
        JobOffer newJobOffer = new JobOffer();
        Random random = new Random();
        int randomNumber = random.nextInt(30);

        newJobOffer.setTitle(request.getTitle());
        newJobOffer.setPublishDate(LocalDate.now());
        newJobOffer.setApplyDate(request.getApplyDate());
        newJobOffer.setPreview(request.getPreview());
        newJobOffer.setCompanyDescription(request.getCompanyDescription());
        newJobOffer.setAboutRole(request.getAboutRole());
        newJobOffer.setImageUrl("https://picsum.photos/250?random="+randomNumber);

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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
        }
        Rate newRate= new Rate();
        newRate.setValue(0);
        newRate.setCandidate(candidate);
        newRate.setJobOfferId(jobOffer.getId().toString());
        rateRepo.saveAndFlush(newRate);

        candidate.getRates().add(newRate);

        candidateRepo.saveAndFlush(candidate);
        jobOffer.getRecruitmentList().sort(Comparator.comparing(Recruitment::getIndex));

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
        newRate.setJobOfferId(request.getJobOfferId());
        newRate.setValue(request.getRate());

        rateRepo.saveAndFlush(newRate);

        candidate.getRates().add(newRate);

        candidateRepo.saveAndFlush(candidate);


        final String msg = String.format("%s rate for: %s is now set to %s", candidate.getId(), request.getJobOfferId(), request.getRate());
        log.info(msg);
        return msg;
    }


    public JobOffer getOneJobOffer(ApplyForJobRequest request) {


        JobOffer joboffer = serviceHelper.getJobOfferById(request.getJobOfferId());

        for (Recruitment r : joboffer.getRecruitmentList()) {
            System.out.println(r.getTitle());
        }

        return serviceHelper.getJobOfferById(request.getJobOfferId());
    }

    public String updateRecruitmentStepsOrder(ChangeRecruitmentIndex request) {
        Recruitment recruitmentToMove = serviceHelper.getRecruitmentById(request.getRecruitmentId());
        JobOffer jobOfferToUpdate = serviceHelper.getJobOfferById(request.getJobOfferId());

        int indexToRemove =-1;
        for (int i = 0; i < jobOfferToUpdate.getRecruitmentList().size(); i++) {
            if (jobOfferToUpdate.getRecruitmentList().get(i).getId()==request.getRecruitmentId()){
                final String msg = String.format("Moving: %s to index %s", recruitmentToMove.getTitle(), i);
                log.info(msg);
                indexToRemove=i;

            }
        }
        if (indexToRemove==-1){
            final String msg = String.format("Could not move: %s", recruitmentToMove.getTitle());
            log.info(msg);
            return msg;
        }
        jobOfferToUpdate.getRecruitmentList().remove(indexToRemove);

        jobOfferToUpdate.getRecruitmentList().add(request.getNewIndex(),recruitmentToMove);

        recruitmentRepo.saveAndFlush(recruitmentToMove);
        jobRepo.saveAndFlush(jobOfferToUpdate);


//        Integer oldIndex = recruitmentToMove.getIndex();
//        jobOfferToUpdate.getRecruitmentList().sort(Comparator.comparing(Recruitment::getIndex));
//        //Move To the right
//        if(oldIndex<request.getNewIndex()){
//            for (int i = oldIndex; i <=request.getNewIndex() ; i++) {
//                Recruitment recruitment = jobOfferToUpdate.getRecruitmentList().get(i);
//                recruitment.setIndex(recruitment.getIndex()-1);
//                recruitmentRepo.saveAndFlush(recruitment);
//            }
//        //Move to the left
//        }else if (oldIndex>request.getNewIndex()){
//            for (int i = request.getNewIndex(); i <oldIndex ; i++) {
//                Recruitment recruitment = jobOfferToUpdate.getRecruitmentList().get(i);
//                recruitment.setIndex(recruitment.getIndex()+1);
//                recruitmentRepo.saveAndFlush(recruitment);
//            }
//        }else{
//            final String msg = "no update was done";
//            log.info(msg);
//            return msg;
//        }
//
//        recruitmentToMove.setIndex(request.getNewIndex());
//        recruitmentRepo.saveAndFlush(recruitmentToMove);
//
//        jobOfferToUpdate.getRecruitmentList().sort(Comparator.comparing(Recruitment::getIndex));
//
//
//        jobRepo.saveAndFlush(jobOfferToUpdate);


        final String msg = String.format("%s is moved to index %s", recruitmentToMove.getTitle(), request.getNewIndex());
        log.info(msg);
        return msg;

    }

    public JobOffer addRecruitmentStep(AddRecruitmentsRequest request) {
        Recruitment newRecruitment = new Recruitment();
        JobOffer jobOfferToUpdate = serviceHelper.getJobOfferById(request.getJobOfferId());
        newRecruitment.setJobOffer(jobOfferToUpdate);
        newRecruitment.setCandidateList(new ArrayList<>());
        newRecruitment.setTitle(request.getTitle());
        newRecruitment.setIndex(jobOfferToUpdate.getRecruitmentList().size());

        recruitmentRepo.saveAndFlush(newRecruitment);

        jobOfferToUpdate.getRecruitmentList().add(newRecruitment);

        jobRepo.saveAndFlush(jobOfferToUpdate);


        final String msg = String.format("%s is added", newRecruitment.getTitle());
        log.info(msg);
        return jobOfferToUpdate;
    }

    public String deleteRecruitmentStep(RemoveRecruitmentsRequest request) {
        Recruitment recruitmentToDelete = serviceHelper.getRecruitmentById(request.getRecruitmentId());
        String title = recruitmentToDelete.getTitle();
        JobOffer jobOfferToUpdate = serviceHelper.getJobOfferById(request.getJobOfferId());

        Integer index = recruitmentToDelete.getIndex();

        for (int i = index; i <jobOfferToUpdate.getRecruitmentList().size() ; i++) {
            Recruitment recruitment = jobOfferToUpdate.getRecruitmentList().get(i);
            recruitment.setIndex(recruitment.getIndex()-1);
            recruitmentRepo.saveAndFlush(recruitment);
        }

        recruitmentRepo.delete(recruitmentToDelete);



        final String msg = String.format("%s is removed from %s", title, jobOfferToUpdate.getId());
        log.info(msg);
        return msg;
    }

    public String changListForCandidate(ChangListForCandidateRequest request) {
        Candidate candidate = serviceHelper.getCandidateById(request.getCandidateId());
        Recruitment recruitmentToMoveTo = serviceHelper.getRecruitmentById(request.getNewRecruitmentId());
        Recruitment oldRecruitment = serviceHelper.getRecruitmentById(request.getOldRecruitmentId());

        oldRecruitment.getCandidateList().remove(candidate);
        recruitmentRepo.saveAndFlush(oldRecruitment);

        recruitmentToMoveTo.getCandidateList().add(candidate);
        recruitmentRepo.saveAndFlush(recruitmentToMoveTo);

        final String msg = String.format("%s is moved to %s", candidate.getId(), recruitmentToMoveTo.getTitle());
        log.info(msg);
        return msg;
    }

    public List<CandidateJobOfferDTO> getAllMyProcesses(CandidateDetails request) {
        Candidate candidate = serviceHelper.getCandidateByEmail(request.getEmail());
        List<JobOffer> allJobOffers = jobRepo.findAll();

        List<CandidateJobOfferDTO> listToReturn = new ArrayList<>();

        for (JobOffer jo :  allJobOffers) {
            for (Recruitment r : jo.getRecruitmentList()) {
                for (Candidate c : r.getCandidateList()) {
                    if(c.getId()==candidate.getId()){
                        CandidateJobOfferDTO jDTO = serviceHelper.transferJobOfferToJobOfferDTO(jo);
                        listToReturn.add(jDTO);
                    }
                }
            }
        }
        final String msg = String.format("%s processes is found on %s", listToReturn.size(), candidate.getId());
        log.info(msg);
        return listToReturn;

    }
}
