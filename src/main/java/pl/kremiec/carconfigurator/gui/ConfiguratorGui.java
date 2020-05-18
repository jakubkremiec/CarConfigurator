package pl.kremiec.carconfigurator.gui;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kremiec.carconfigurator.objects.CarModels;
import pl.kremiec.carconfigurator.objects.Engines;
import pl.kremiec.carconfigurator.repo.CarModelsRepo;
import pl.kremiec.carconfigurator.repo.EnginesRepo;

import java.util.ArrayList;


@Route("gui")
public class ConfiguratorGui extends VerticalLayout {

    CarModelsRepo carModelsRepo; //repository of car
    EnginesRepo enginesRepo; //repository of engines
    ArrayList<CarModels> modelsArrayList; //arraylist of models
    ArrayList<Engines> enginesArrayList; //arraylist of engines
    HorizontalLayout horizontalLayoutForEngine; //horizontal layout for engine choosement
    HorizontalLayout horizontalLayoutForCarChoose; //horizontal layout for car choosement

    CarModels choosedModel = new CarModels();
    String choosedEngine;
    
    @Autowired
    ConfiguratorGui(CarModelsRepo carModelsRepo, EnginesRepo enginesRepo){
        this.carModelsRepo = carModelsRepo;
        this.enginesRepo = enginesRepo;

        horizontalLayoutForCarChoose = new HorizontalLayout();
        horizontalLayoutForEngine = new HorizontalLayout();


        modelChoose();

    }


    public void modelChoose(){
        Image carImage = new Image();
        modelsArrayList = new ArrayList<>();
        carModelsRepo.findAll().forEach(s->modelsArrayList.add(s)); //adding models list to arraylist

        methodAddHorizontalLayoutCarChoose(carImage);
    }

    public void engineChoose(String modelName){

        Image engineImage = new Image();
        TextArea engineTextArea = new TextArea();
        enginesArrayList = new ArrayList<>();
        enginesRepo.findAll().forEach(s->enginesArrayList.add(s)); //adding engines to arraylist

        methodAddHorizontalLayoutEngineChoose(modelName,engineImage,engineTextArea);
    }

    public void methodAddHorizontalLayoutCarChoose(Image carImage){ //method which adds horizontal layout of car choosment to main vertical layout

        ComboBox <CarModels> comboBoxCar = new ComboBox<>(); //combobox of car choosment
        comboBoxCar.setLabel("Choose your model");
        comboBoxCar.setWidth("130px");
        comboBoxCar.setItemLabelGenerator(CarModels::getModelName);
        comboBoxCar.setItems(modelsArrayList);
        comboBoxCar.setClearButtonVisible(true);

        comboBoxCar.addValueChangeListener(click -> {
            if(click.getValue() != null){
                carImage.setSrc(click.getValue().getLink()); //setting src of car from database to Image
                carImage.setWidth("280px");
                choosedModel=click.getValue(); //saving choosed car to variable
                horizontalLayoutForCarChoose.add(carImage); //adding car image to car horizontal layout
                horizontalLayoutForEngine.removeAll(); //removing everything from engine layout because when you changed your car selection, it need to reload new photo
                engineChoose(click.getValue().getModelName()); //if user choose model name, then show engines
            }
        });
        horizontalLayoutForCarChoose.add(comboBoxCar); //adding combobox to horizontal car layout
        add(horizontalLayoutForCarChoose); //adding horizontal car layout to main vertical layout
    }


    public void methodAddHorizontalLayoutEngineChoose(String modelName, Image engineImage, TextArea engineTextArea) {

        ComboBox<String> engineComboBox = new ComboBox<>(); //combobox of engine choosment
        engineComboBox.setLabel("Choose your engine");
        engineComboBox.setWidth("260px");
        ArrayList<String> engineArrayList = new ArrayList<>(); //auxiliary arraylist for custom combobox options
        for (Engines enginesCounter : enginesArrayList) {
            if (enginesCounter.getDecicatedToModel().equals(modelName)) {
                String str; //custom string
                if (enginesCounter.getTypeOfFuel().equals("diesel")) {
                    str = enginesCounter.getEngineSize() + "d"; //if engine.equals("diesel") it add ,,d" at end of engine size
                } else {
                    str = String.valueOf(enginesCounter.getEngineSize());
                }
                str = str + " / " + enginesCounter.getHpAmount() + "hp / +" + enginesCounter.getPrice() + "pln"; //custom string
                engineArrayList.add(str);
            }
        }
        engineComboBox.setItems(engineArrayList); //setting string to combobox
        engineComboBox.setClearButtonVisible(true);

        engineComboBox.addValueChangeListener(click -> {
            if (click.getValue() != null) {

                enginesArrayList.forEach(s -> {
                    String str = click.getValue().substring(0, 3);
                    if (s.getEngineSize() == Double.parseDouble(str) && s.getDecicatedToModel().equals(modelName)) {
                        engineImage.setSrc(s.getLink()); //get src of engine
                        engineImage.setWidth("280px");
                        choosedEngine=click.getValue(); //save choosed engine to variable
                        engineTextArea.setValue(showEngineInfo(s,choosedModel)); //setting custom string to textarea
                        engineTextArea.setWidth("350px");
                        horizontalLayoutForEngine.add(engineImage,engineTextArea); //adding image and textarea every time when user changes engine
                    }
                });
            }
        });
        horizontalLayoutForEngine.add(engineComboBox);
        add(horizontalLayoutForEngine);//adding horizontal engine layout to main vertical layout
    }


    public String showEngineInfo(Engines engine, CarModels model){
        return String.format("Choosed Mercedes-Benz %s class in basic costs %.2fpln.\nEngine you choosed is %.1f %s engine with %dhp and addicitonal price %dpln.\nPrice for car is now %.2fpln.",
                model.getModelName(), model.getBasicPrice(), engine.getEngineSize(), engine.getTypeOfFuel(), engine.getHpAmount(), engine.getPrice(), model.getBasicPrice()+(double)engine.getPrice());
    }




}
