package com.project.adress.state;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/{countryId}/states")
public class StateController {

    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping
    public List<State> getStatesByCountry(
            @PathVariable String countryId) {
        return stateService.getAllStates(countryId);
    }

    @GetMapping("{stateId}")
    public State getStateByCountry(
            @PathVariable("stateId") Integer stateId) {

        return stateService.getState(stateId);
    }

    @PostMapping
    public void registerState(
            @PathVariable String countryId,
            @RequestBody StateRegistrationRequest request) {
        StateRegistrationRequest stateRegistrationRequest = new StateRegistrationRequest(
                request.stateId(), request.stateName(), countryId);

        stateService.addState(stateRegistrationRequest);
    }

    @DeleteMapping("{stateId}")
    public void deleteState(
            @PathVariable("stateId") Integer stateId) {

        stateService.deleteState(stateId);
    }

    @PutMapping("{stateId}")
    public void updateState(
            @PathVariable("stateId") String stateId,
            @RequestBody StateUpdateRequest updateRequest) {
        try {
            Integer stateIdInt = Integer.parseInt(stateId);
            stateService.updateState(stateIdInt, updateRequest);
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
    }
}
