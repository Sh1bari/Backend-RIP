package com.example.rip.services;

import com.example.rip.exceptions.application.*;
import com.example.rip.exceptions.event.EventAlreadyInApplicationException;
import com.example.rip.exceptions.event.EventNotFoundException;
import com.example.rip.exceptions.event.EventNotInApplicationException;
import com.example.rip.exceptions.user.UserNotFoundException;
import com.example.rip.models.dtos.request.ApplicationVote;
import com.example.rip.models.dtos.response.ApplicationAllRes;
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
import java.util.ArrayList;
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

    public ApplicationRes getApplicationById(Integer id, String username){
        Application application = applicationRepo.findById(id)
                .orElseThrow(()->new ApplicationNotFoundException(id));
        User user = userRepo.findByUsername(username)
                .orElseThrow(()->new UserNotFoundException(username));
        if(!isAdmin(user) || !application.getCreatorUser().getId().equals(user.getId())){
            throw new PermissionDeniedException();
        }
        ApplicationRes res = ApplicationRes.mapFromEntity(application);
        return res;
    }

    private boolean isAdmin(User user){
        return user.getRoles().stream().anyMatch(o->o.getName().equals("ROLE_ADMIN"));
    }

    public List<ApplicationAllRes> getAllApplications(LocalDateTime dateFrom){
        List<Application> applications = applicationRepo.findAllByFormationTimeAfter(dateFrom).stream()
                .filter(o->(!o.getStatus().equals(ApplicationStatus.DELETED) && !o.getStatus().equals(ApplicationStatus.FORMED)))
                .toList();
        List<ApplicationAllRes> res = applications.stream()
                .map(ApplicationAllRes::mapFromEntity)
                .toList();
        return res;
    }
    public List<ApplicationAllRes> getAllApplicationsByUser(String username, LocalDateTime dateFrom){
        User user = userRepo.findByUsername(username)
                .orElseThrow(()->new UserNotFoundException(username));
        List<Application> applications = applicationRepo.findAllByCreatorUser_IdAndFormationTimeAfter(user.getId(), dateFrom).stream()
                .filter(o->(!o.getStatus().equals(ApplicationStatus.DELETED) && !o.getStatus().equals(ApplicationStatus.FORMED)))
                .toList();
        List<ApplicationAllRes> res = applications.stream()
                .map(ApplicationAllRes::mapFromEntity)
                .toList();
        return res;
    }

    @Transactional
    public ApplicationRes formApplication(String username, Integer id){
        User user = userRepo.findByUsername(username)
                .orElseThrow(()->new UserNotFoundException(username));
        Application application = applicationRepo.findById(id)
                .orElseThrow(()->new ApplicationNotFoundException(id));
        if(!application.getCreatorUser().getId().equals(user.getId())){
            throw new PermissionDeniedException();
        }
        if(!application.getStatus().equals(ApplicationStatus.DRAFT)){
            throw new ApplicationNotInDraftException();
        }
        if(application.getEvents().size() == 0){
            throw new ApplicationMustContainsEventsException();
        }
        application.setFormationTime(LocalDateTime.now());
        application.setStatus(ApplicationStatus.FORMED);
        applicationRepo.save(application);
        return ApplicationRes.mapFromEntity(application);
    }
    public ApplicationRes voteApplication(Integer id, ApplicationVote vote, String username) {
        Application application = applicationRepo.findById(id)
                .orElseThrow(()->new ApplicationNotFoundException(id));
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

    public Application deleteApplication(String username, Integer id){
        Application application = applicationRepo.findById(id)
                .orElseThrow(()->new ApplicationNotFoundException(id));
        User user = userRepo.findByUsername(username)
                .orElseThrow(()->new UserNotFoundException(username));
        if(!application.getCreatorUser().getId().equals(user.getId())){
            throw new PermissionDeniedException();
        }
        if(!application.getStatus().equals(ApplicationStatus.FORMED) && !application.getStatus().equals(ApplicationStatus.DRAFT)){
            throw new ApplicationNotFormedException();
        }
        application.setEndTime(LocalDateTime.now());
        application.setStatus(ApplicationStatus.DELETED);
        return applicationRepo.save(application);
    }

    public ApplicationRes addEvent(Integer id, Integer eId, String username){
        User user = userRepo.findByUsername(username)
                .orElseThrow(()-> new UserNotFoundException(username));
        Application application;
        Event event = eventRepo.findById(eId)
                .orElseThrow(()->new EventNotFoundException(id));
        if(id != 0) {
            application = applicationRepo.findById(id)
                    .orElseThrow(() -> new ApplicationNotFoundException(id));
            if(!application.getCreatorUser().getId().equals(user.getId())){
                throw new PermissionDeniedException();
            }
        }else{
            if(applicationRepo.findByCreatorUser_UsernameAndStatus(username, ApplicationStatus.DRAFT).isPresent()){
                throw new ApplicationDraftPresentsException();
            }
            Application newApplication = new Application();
            newApplication.setStatus(ApplicationStatus.DRAFT);
            newApplication.setCreatorUser(user);
            newApplication.setCreateTime(LocalDateTime.now());
            newApplication.setEvents(new ArrayList<>());
            application = applicationRepo.save(newApplication);
        }
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
    public ApplicationRes deleteEvent(Integer id, Integer eId, String username){
        User user = userRepo.findByUsername(username)
                .orElseThrow(()-> new UserNotFoundException(username));
        Application application = applicationRepo.findById(id)
                .orElseThrow(()->new ApplicationNotFoundException(id));

        if(!application.getCreatorUser().getId().equals(user.getId())){
            throw new PermissionDeniedException();
        }

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
