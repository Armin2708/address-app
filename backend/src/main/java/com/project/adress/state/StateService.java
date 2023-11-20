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
    public List<State> getAllStates(String countryId){return stateDao.selectAllStates(countryId);}

    public State getState(Integer id){
        return stateDao.selectStateById(id)
                .orElseThrow(()-> new ResourceNotFoundException("state with id [%s] not found".formatted(id)));
    }

    public void addState(StateRegistrationRequest stateRegistrationRequest){
        String name = stateRegistrationRequest.stateName();
        Integer id = stateRegistrationRequest.stateId();
        if (stateDao.existStateById(id)){
            throw new DuplicateResourceException("state id already taken");
        }
        if (stateDao.existStateWithName(name)){
            throw new DuplicateResourceException("state name already taken");
        }
        String countryId = stateRegistrationRequest.countryId();
        if (stateDao.existCountryWithCountryId(countryId)==false){
            throw new ResourceNotFoundException("country id "+countryId+" does not exist");
        }
        State state = new State(
                stateRegistrationRequest.stateId(),
                stateRegistrationRequest.stateName(),
                stateRegistrationRequest.countryId()
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

        if(!changes) {
            throw new RequestValidationException("no data changes found");
        }

        stateDao.updateState(state);
    }
}
