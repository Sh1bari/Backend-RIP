package com.example.rip.services;

import com.example.rip.exceptions.application.*;
import com.example.rip.exceptions.event.EventAlreadyInApplicationException;
import com.example.rip.exceptions.event.EventNotFoundException;
import com.example.rip.exceptions.event.EventNotInApplicationException;
import com.example.rip.exceptions.user.UserNotFoundException;
import com.example.rip.models.dtos.request.ApplicationVote;
import com.example.rip.models.dtos.response.ApplicationRes;
import com.example.rip.models.entities.Application;
import com.example.rip.models.entities.Event;
import com.example.rip.models.entities.User;
import com.example.rip.models.enums.ApplicationStatus;
import com.example.rip.repos.ApplicationRepo;
import com.example.rip.repos.EventRepo;
import com.example.rip.repos.UserRepo;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepo applicationRepo;
    private final EventRepo eventRepo;
    private final UserRepo userRepo;

    public ApplicationRes getApplicationById(Integer id){
        Application application = applicationRepo.findById(id)
                .orElseThrow(()->new ApplicationNotFound(id));
        ApplicationRes res = ApplicationRes.mapFromEntity(application);
        return res;
    }

    public List<ApplicationRes> getAllApplications(ApplicationStatus status, LocalDateTime dateFrom){
        List<Application> applications = applicationRepo.findAllByStatusAndFormationTimeAfter(status, dateFrom);
        List<ApplicationRes> res = applications.stream()
                .map(ApplicationRes::mapFromEntity)
                .toList();
        return res;
    }

    @Transactional
    public ApplicationRes formApplication(Integer id){
        Application application = applicationRepo.findById(id)
                .orElseThrow(()->new ApplicationNotFound(id));
        if(!application.getStatus().equals(ApplicationStatus.DRAFT)){
            throw new ApplicationNotInDraftException();
        }
        if(application.getEvents().size() == 0){
            throw new ApplicationMustContainsEventsException();
        }
        application.setFormationTime(LocalDateTime.now());
        application.setStatus(ApplicationStatus.FORMED);
        applicationRepo.save(application);
        Application newApplication = new Application();
        newApplication.setStatus(ApplicationStatus.DRAFT);
        newApplication.setCreatorUser(application.getCreatorUser());
        newApplication.setCreateTime(LocalDateTime.now());
        applicationRepo.save(newApplication);
        return ApplicationRes.mapFromEntity(application);
    }
    public ApplicationRes voteApplication(Integer id, ApplicationVote vote, String username) {
        Application application = applicationRepo.findById(id)
                .orElseThrow(()->new ApplicationNotFound(id));
        User user = userRepo.findByUsername(username)
                        .orElseThrow(()->new UserNotFoundException(username));
        if(!application.getStatus().equals(ApplicationStatus.FORMED)){
            throw new ApplicationNotFormedException();
        }
        if(vote.getStatus().equals(ApplicationStatus.COMPLETED) ||
                vote.getStatus().equals(ApplicationStatus.REJECTED)){
            application.setStatus(vote.getStatus());
            application.setModeratorUser(user);
            application.setEndTime(LocalDateTime.now());
            applicationRepo.save(application);
            return ApplicationRes.mapFromEntity(application);
        }else throw new ApplicationNotValidVoteException();
    }

    public Application deleteApplication(Integer id){
        Application application = applicationRepo.findById(id)
                .orElseThrow(()->new ApplicationNotFound(id));
        if(!application.getStatus().equals(ApplicationStatus.FORMED)){
            throw new ApplicationNotFormedException();
        }
        application.setEndTime(LocalDateTime.now());
        application.setStatus(ApplicationStatus.DELETED);
        return applicationRepo.save(application);
    }

    public ApplicationRes addEvent(Integer id, Integer eId){
        Application application = applicationRepo.findById(id)
                .orElseThrow(()->new ApplicationNotFound(id));
        Event event = eventRepo.findById(eId)
                .orElseThrow(()->new EventNotFoundException(id));
        if(!application.getStatus().equals(ApplicationStatus.DRAFT)){
            throw new ApplicationNotInDraftException();
        }
        if(event.getApplications().contains(application)){
            throw new EventAlreadyInApplicationException();
        }
        event.getApplications().add(application);
        application.getEvents().add(event);
        eventRepo.save(event);
        return ApplicationRes.mapFromEntity(application);
    }
    public ApplicationRes deleteEvent(Integer id, Integer eId){
        Application application = applicationRepo.findById(id)
                .orElseThrow(()->new ApplicationNotFound(id));
        Event event = eventRepo.findById(eId)
                .orElseThrow(()->new EventNotFoundException(eId));
        if(!application.getStatus().equals(ApplicationStatus.DRAFT)){
            throw new ApplicationNotInDraftException();
        }
        if(!event.getApplications().contains(application)){
            throw new EventNotInApplicationException();
        }
        event.getApplications().remove(application);
        application.getEvents().remove(event);
        eventRepo.save(event);
        return ApplicationRes.mapFromEntity(application);
    }
}
