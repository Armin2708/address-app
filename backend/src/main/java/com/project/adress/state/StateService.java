package com.project.adress.state;

import com.project.adress.exception.DuplicateResourceException;
import com.project.adress.exception.RequestValidationException;
import com.project.adress.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateService {

    private final StateDao stateDao;

    public StateService(@Qualifier("Statejdbc") StateDao stateDao){
        this.stateDao = stateDao;
    }
    public List<State> getAllStates(){return stateDao.selectAllStates();}

    public State getState(Integer id){
        return stateDao.selectStateById(id)
                .orElseThrow(()-> new ResourceNotFoundException("state with id [%s] not found".formatted(id)));
    }

    public void addState(StateRegistrationRequest stateRegistrationRequest){
        String name = stateRegistrationRequest.name();
        Integer id = stateRegistrationRequest.state_id();
        if (stateDao.existStateById(id)){
            throw new DuplicateResourceException("state id already taken");
        }
        if (stateDao.existStateWithName(name)){
            throw new DuplicateResourceException("state name already taken");
        }
        String countryId = stateRegistrationRequest.country_id();
        if (stateDao.existCountryWithCountryId(countryId)==false){
            throw new ResourceNotFoundException("country id does not exist");
        }
        State state = new State(
                stateRegistrationRequest.state_id(),
                stateRegistrationRequest.name(),
                stateRegistrationRequest.country_id()
        );
        stateDao.insertState(state);
    }

    public void deleteState(Integer id){
        if(!stateDao.existStateById(id)){
            throw new ResourceNotFoundException("state with id [%s] not found".formatted(id));
        }
        stateDao.deleteStateById(id);
    }

    public void updateState(Integer id, StateUpdateRequest stateUpdateRequest){
        State state = stateDao.selectStateById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "state with id [%s] not found".formatted(id)
                ));
        boolean changes=false;

        if (stateUpdateRequest.name()!=null && !stateUpdateRequest.name().equals(state.getName())) {
            if(stateDao.existStateWithName(stateUpdateRequest.name())) {
                throw new DuplicateResourceException("state name already taken");
            }
            state.setName(stateUpdateRequest.name());
            changes=true;
        }

        if (stateUpdateRequest.country_id()!=null && !stateUpdateRequest.country_id().equals(state.getCountry_id())) {
            if (stateDao.existCountryWithCountryId(stateUpdateRequest.country_id())==false){
                throw new ResourceNotFoundException("country with id [%s] not found");
            }
            state.setCountry_id(stateUpdateRequest.country_id());
            changes=true;
        }

        if(!changes) {
            throw new RequestValidationException("no data changes found");
        }

        stateDao.updateState(state);
    }
}
