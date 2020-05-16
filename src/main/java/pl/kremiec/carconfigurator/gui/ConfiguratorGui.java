package pl.kremiec.carconfigurator.gui;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kremiec.carconfigurator.objects.CarModels;
import pl.kremiec.carconfigurator.objects.Engines;
import pl.kremiec.carconfigurator.repo.CarModelsRepo;
import pl.kremiec.carconfigurator.repo.EnginesRepo;

import java.util.ArrayList;


@Route("gui")
public class ConfiguratorGui extends FormLayout {

    CarModelsRepo carModelsRepo;
    EnginesRepo enginesRepo;
    Image carImage;
    Image engineImage;
    ArrayList<CarModels> modelsArrayList;


    @Autowired
    ConfiguratorGui(CarModelsRepo carModelsRepo, EnginesRepo enginesRepo){
        this.carModelsRepo = carModelsRepo;
        this.enginesRepo = enginesRepo;

        carImage = new Image();
        engineImage = new Image();

        setResponsiveSteps(new ResponsiveStep("32em", 1));

        add(modelChoose());

    }


    public HorizontalLayout modelChoose(){

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        HorizontalLayout horizontalLayoutForEngine = new HorizontalLayout();
        modelsArrayList = new ArrayList<>(); //initializing

        carModelsRepo.findAll().forEach(s->modelsArrayList.add(s)); //adding models list to arraylist

        ComboBox<CarModels> comboBox = new ComboBox<>();
        comboBox.setLabel("Choose your model");
        comboBox.setItemLabelGenerator(CarModels::getModelName);
        comboBox.setItems(modelsArrayList);
        comboBox.setClearButtonVisible(true);


        comboBox.addValueChangeListener(click -> {
            if(click.getValue() != null){

                carImage.setSrc(click.getValue().getLink());
                horizontalLayoutForEngine.removeAll();
                engineChoose(click.getValue().getModelName(),horizontalLayoutForEngine); //if user choose model name, then show engines
                carImage.setWidth("30%");
            }
        });

        horizontalLayout.add(comboBox,carImage);
        return horizontalLayout;

    }

    public void engineChoose(String modelName, HorizontalLayout horizontalLayoutFromInput){

        ArrayList<Engines> enginesArrayList = new ArrayList<>();
        enginesRepo.findAll().forEach(s->enginesArrayList.add(s));
        TextArea engineTextArea = new TextArea();

        ComboBox<String> engineComboBox = new ComboBox<>();
        engineComboBox.setLabel("Choose your engine");
        ArrayList<String> englineArrayList = new ArrayList<>();
        for(Engines enginesCounter : enginesArrayList){
            if(enginesCounter.getDecicatedToModel().equals(modelName)){
                englineArrayList.add(String.valueOf(enginesCounter.getEngineSize()));
            }
        }
        engineComboBox.setItems(englineArrayList);
        engineComboBox.setClearButtonVisible(true);

        engineComboBox.addValueChangeListener(click -> {
            if (click.getValue() != null){

                enginesArrayList.forEach(s->{
                    if(s.getEngineSize()==Double.parseDouble(click.getValue()) && s.getDecicatedToModel().equals(modelName)){
                        engineImage.setSrc(s.getLink());
                        engineImage.setAlt("");
                        engineImage.setWidth("30%");
                        engineTextArea.setValue(showEngineInfo(s));
                        horizontalLayoutFromInput.add(engineImage,engineTextArea);
                    }
                });
            }
        });

            horizontalLayoutFromInput.add(engineComboBox);
            add(horizontalLayoutFromInput);

    }

    public String showEngineInfo(Engines engine){
        return String.format("Choosed Mercedes-Benz %s class, has %.1f %s engine",engine.getDecicatedToModel(), engine.getEngineSize(), engine.getTypeOfFuel());
    }




}
