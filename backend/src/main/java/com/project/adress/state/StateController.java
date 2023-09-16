package com.project.adress.state;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/countries/states")
public class StateController {

    private final StateService stateService;

    public StateController(StateService stateService){this.stateService = stateService;}

    @GetMapping
    public List<State> getState(){return stateService.getAllStates();}

    @GetMapping("{stateId}")
    public State getState(
            @PathVariable("stateId") Integer stateId){
        return stateService.getState(stateId);
    }

    @PostMapping
    public void registerState(@RequestBody StateRegistrationRequest request){
        stateService.addState(request);
    }

    @DeleteMapping("{stateId}")
    public void deleteState(@PathVariable("stateId") Integer stateId){
        stateService.deleteState(stateId);
    }

    @PutMapping("{stateId}")
    public void updateState(
            @PathVariable("stateId") Integer stateId,
            @RequestBody StateUpdateRequest updateRequest){
        stateService.updateState(stateId,updateRequest);
    }


}
