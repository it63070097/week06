package com.example.week06.view;

import com.example.week06.pojo.Wizard;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;


@Route(value = "/mainPage.it")
public class MainWizardView extends VerticalLayout {
    private TextField fullname, dollars;
    private RadioButtonGroup<String> gender;
    private ComboBox<String> position, school, house;
    private Button backward, create , update, delete ,forward;
    private int num = 0;
    Wizard present;
    public MainWizardView() {
        fullname = new TextField("", "Fullname");

        gender = new RadioButtonGroup<>();
        gender.setLabel("Gender :");
        gender.setItems("Male", "Female");

        position = new ComboBox<>();
        position.setItems("Student", "Teacher");
        position.setPlaceholder("Position");

        dollars = new TextField("Dollors");
        dollars.setPrefixComponent(new Span("$"));

        school = new ComboBox<>();
        school.setItems("Hogwarts", "Beauxbatons", "Durmstrang");
        school.setPlaceholder("School");

        house = new ComboBox<>();
        house.setItems("Gryffindor", "Ravenclaw", "Hufflepuff", "Slyther");
        house.setPlaceholder("House");

        backward = new Button("<<");
        create = new Button("create");
        update = new Button("update");
        delete = new Button("delete");
        forward = new Button(">>");

        HorizontalLayout btnGroup = new HorizontalLayout(backward, create, update, delete, forward);

        this.add(fullname, gender, position, dollars, school, house, btnGroup);

        create.addClickListener(event -> {
            String sex = "";
            if (gender.getValue().equals("Male")){
                sex = "m";
            }else if (gender.getValue().equals("Female")) {
                sex = "f";
            }
            Wizard out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addWizard")
                    .body(Mono.just(new Wizard(null, sex, fullname.getValue(), school.getValue(), house.getValue(), Integer.parseInt(dollars.getValue()), position.getValue())), Wizard.class)
                    .retrieve()
                    .bodyToMono(Wizard.class)
                    .block();
            new Notification("create", 5000).open();
        });

        backward.addClickListener(event -> {
            List<Wizard> out = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/wizards")
                    .retrieve()
                    .bodyToMono(List.class)
                    .block();
            ObjectMapper mapper = new ObjectMapper();

            if (num != 0){
                num--;
            }
            Wizard wizard = mapper.convertValue(out.get(num), Wizard.class);
            present = wizard;
            fullname.setValue(wizard.getName());
            if (wizard.getSex().equals("m")){
                gender.setValue("Male");
            } else if (wizard.getSex().equals("f")) {
                gender.setValue("Female");
            }
            position.setValue(wizard.getPosition());
            dollars.setValue(String.valueOf(wizard.getMoney()));
            school.setValue(wizard.getSchool());
            house.setValue(wizard.getHouse());

            new Notification("Backward", 5000).open();
        });

        forward.addClickListener(event -> {
            List<Wizard> out = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/wizards")
                    .retrieve()
                    .bodyToMono(List.class)
                    .block();
            ObjectMapper mapper = new ObjectMapper();
            new Notification("Forward", 5000).open();

            if (num < out.size()-1){
                num++;
            }
            Wizard wizard = mapper.convertValue(out.get(num), Wizard.class);
            present = wizard;
            fullname.setValue(wizard.getName());
            if (wizard.getSex().equals("m")){
                gender.setValue("Male");
            } else if (wizard.getSex().equals("f")) {
                gender.setValue("Female");
            }
            position.setValue(wizard.getPosition());
            dollars.setValue(String.valueOf(wizard.getMoney()));
            school.setValue(wizard.getSchool());
            house.setValue(wizard.getHouse());
        });

        delete.addClickListener(event -> {
            boolean out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/deleteWizard")
                    .body(Mono.just(this.present.get_id()), String.class)
                    .retrieve()
                    .bodyToMono((boolean.class))
                    .block();
            new Notification("delete", 5000).open();
        });

        update.addClickListener(event -> {
            String sex = "";
            if (gender.getValue().equals("Male")){
                sex = "m";
            }else if (gender.getValue().equals("Female")) {
                sex = "f";
            }
            Wizard out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/updateWizard")
                    .body(Mono.just(new Wizard(this.present.get_id(), sex, fullname.getValue(), school.getValue(), house.getValue(), Integer.parseInt(dollars.getValue()), position.getValue())), Wizard.class)
                    .retrieve()
                    .bodyToMono(Wizard.class)
                    .block();
            new Notification("update", 5000).open();
        });


    }
}
