package com.example.week06.repository;

import com.example.week06.pojo.Wizard;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WizardService {
    @Autowired
    private WizardRepository repository;

    public WizardService(WizardRepository repository){
        this.repository = repository;
    }
    public List<Wizard> retrieveWizard(){
        return repository.findAll();
    }
    public Wizard retrieveWizardById(String id){
        return repository.findBy_id(id);
    }
    public Wizard createWizard(Wizard wizard) {
        return repository.insert(wizard);
    }
    public boolean deleteWizard(Wizard wizard) {
        try { repository.delete(wizard);
            return true;}
        catch (Exception exception){return false;}
    }
    public Wizard updateWizard(Wizard wizard) {
        return repository.save(wizard);
    }
}
