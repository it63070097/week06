package com.example.week06.controller;

import com.example.week06.pojo.Wizard;
import com.example.week06.repository.WizardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WizardController {
    @Autowired
    private WizardService wizardService;

    @RequestMapping(value = "/wizards", method = RequestMethod.GET)
    public ResponseEntity<?> getWizard(){
        List<Wizard> wizards = wizardService.retrieveWizard();
        return ResponseEntity.ok(wizards);
    }

    @RequestMapping(value = "/addWizard", method = RequestMethod.POST)
    public ResponseEntity<?> createWizard(@RequestBody Wizard wizard){
        Wizard n = wizardService.createWizard(wizard);
        return ResponseEntity.ok(n);
    }

    @RequestMapping(value = "/deleteWizard", method = RequestMethod.POST)
    public ResponseEntity<?> deleteWizard(@RequestBody String id){
        Wizard n = wizardService.retrieveWizardById(id);
        boolean status = wizardService.deleteWizard(n);
        return ResponseEntity.ok(status);
    }

    @RequestMapping(value = "/updateWizard", method = RequestMethod.POST)
    public ResponseEntity<?> updateWizard(@RequestBody Wizard wizard){
        Wizard n = wizardService.updateWizard(wizard);
        return ResponseEntity.ok(n);

    }
}
