package pl.kremiec.carconfigurator.gui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kremiec.carconfigurator.objects.CompletedCar;
import pl.kremiec.carconfigurator.objects.Models;
import pl.kremiec.carconfigurator.objects.Colors;
import pl.kremiec.carconfigurator.objects.Engines;
import pl.kremiec.carconfigurator.repo.CarModelsRepo;
import pl.kremiec.carconfigurator.repo.ColorsRepo;
import pl.kremiec.carconfigurator.repo.CompletedCarRepo;
import pl.kremiec.carconfigurator.repo.EnginesRepo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


@Route("gui")
public class ConfiguratorGui extends VerticalLayout {

    CarModelsRepo carModelsRepo; //repository of car
    EnginesRepo enginesRepo; //repository of engines
    ColorsRepo colorsRepo; //repository of colors
    CompletedCarRepo completedCarRepo;

    ArrayList<Models> modelsArrayList; //arraylist of models
    ArrayList<Engines> enginesArrayList; //arraylist of engines
    ArrayList<Colors> colorsArrayList; //arraylist of colors

    HorizontalLayout horizontalLayoutForEngine; //horizontal layout for engine choosement
    HorizontalLayout horizontalLayoutForCarChoose; //horizontal layout for car choosement
    VerticalLayout verticalLayoutForColor; //vertical layout for color choosment
    VerticalLayout verticalLayoutForConfirmation;

    Models choosedModel = new Models(); //choosed model
    Engines choosedEngine = new Engines();   //choosed engine
    Colors choosedColor = new Colors();     //choosed color


    @Autowired
    ConfiguratorGui(CarModelsRepo carModelsRepo, EnginesRepo enginesRepo, ColorsRepo colorsRepo, CompletedCarRepo completedCarRepo){
        this.carModelsRepo = carModelsRepo;
        this.enginesRepo = enginesRepo;
        this.colorsRepo = colorsRepo;
        this.completedCarRepo = completedCarRepo;

        horizontalLayoutForCarChoose = new HorizontalLayout();
        horizontalLayoutForEngine = new HorizontalLayout();
        verticalLayoutForColor = new VerticalLayout();
        verticalLayoutForConfirmation = new VerticalLayout();


        modelChoose(); //here first method starts

    }


    public void modelChoose(){
        Image carImage = new Image();
        modelsArrayList = new ArrayList<>();
        carModelsRepo.findAll().forEach(s->modelsArrayList.add(s)); //adding models list to arraylist

        methodAddHorizontalLayoutCarChoose(carImage);
    }

    public void colorChoose(){

        colorsArrayList = new ArrayList<>();
        colorsRepo.findAll().forEach(s->colorsArrayList.add(s)); //adding all colors to repo

        Set<String> categoriesSet = new HashSet<>();
        colorsArrayList.stream().filter(s->s.getDecicatedToCar().contains(choosedModel.getModelName())) //adding color categories to set
                .forEach(x->categoriesSet.add(x.getCategory()));

        methodAddVerticalLayoutColorChoose(categoriesSet);

    }

    public void engineChoose(){

        Image engineImage = new Image();
        TextArea engineTextArea = new TextArea();
        enginesArrayList = new ArrayList<>();
        enginesRepo.findAll().forEach(s->enginesArrayList.add(s)); //adding engines to arraylist

        methodAddHorizontalLayoutEngineChoose(engineImage,engineTextArea);
    }

    public void addCarToRepo( ){

        Button confirmButton = new Button();
        confirmButton.setText("Make order!");
        verticalLayoutForConfirmation.add(confirmButton);
        add(verticalLayoutForConfirmation);

        confirmButton.addClickListener(click->{

            CompletedCar completedCar = new CompletedCar.Builder()
                    .modelName(choosedModel.getModelName())
                    .basicPrice(choosedModel.getBasicPrice())
                    .amountOfDoors((short)choosedModel.getAmountOfDoors())
                    .modelSrc(choosedModel.getLink())
                    .engineSize(choosedEngine.getEngineSize())
                    .hpAmount((short)choosedEngine.getHpAmount())                           //putting all data to object
                    .enginePrice(choosedEngine.getPrice())
                    .fuelType(choosedEngine.getTypeOfFuel())
                    .engineSrc(choosedEngine.getLink())
                    .colorName(choosedColor.getColor())
                    .colorCategory(choosedColor.getCategory())
                    .colorPrice(choosedColor.getPrice())
                    .colorSrc(choosedColor.getLink())
                    .build();

            completedCarRepo.save(completedCar);        //saving to database


            removeAll();    //removing everything
            TextField succesField = new TextField();
            succesField.setWidth("50%");
            succesField.setValue("Succes! Your car order is now made!");
            add(succesField); //Succes field is showed

        });

    }


    public void methodAddHorizontalLayoutCarChoose(Image carImage){ //method which adds horizontal layout of car choosment to main vertical layout

        ComboBox <Models> comboBoxCar = new ComboBox<>(); //combobox of car choosment
        comboBoxCar.setLabel("Choose your model");
        comboBoxCar.setWidth("140px");
        comboBoxCar.setItemLabelGenerator(Models::getModelName);
        comboBoxCar.setItems(modelsArrayList);
        comboBoxCar.setClearButtonVisible(true);

        comboBoxCar.addValueChangeListener(click -> {
            if(click.getValue() != null){
                carImage.setSrc(click.getValue().getLink()); //setting src of car from database to Image
                carImage.setWidth("280px");
                choosedModel=click.getValue(); //saving choosed car to variable
                horizontalLayoutForCarChoose.add(carImage); //adding car image to car horizontal layout
                horizontalLayoutForEngine.removeAll(); //removing everything from engine layout because when you changed your car selection, it need to reload new photo
                verticalLayoutForColor.removeAll(); //removing everything from engine layout because when you changed your car selection, it need to reload new photo
                verticalLayoutForConfirmation.removeAll();
                colorChoose();
            }
        });
        horizontalLayoutForCarChoose.add(comboBoxCar); //adding combobox to horizontal car layout
        add(horizontalLayoutForCarChoose); //adding horizontal car layout to main vertical layout
    }

    public void methodAddVerticalLayoutColorChoose(Set<String> categoriesSet){

        Tabs tabs = new Tabs();
            Tab tabNormal = new Tab("Normal");
            Tab tabMetallic = new Tab("Metalic");       //tabs of cathegories
            Tab tabDesigno = new Tab("Designo");
        tabs.add(tabNormal,tabMetallic,tabDesigno);

        verticalLayoutForColor.add(tabs);   //adding tabs to main layout

        VerticalLayout verticalLayoutForNormal = new VerticalLayout();
        VerticalLayout verticalLayoutForMetallic = new VerticalLayout();  //vertical layouts for every category
        VerticalLayout verticalLayoutForDesigno = new VerticalLayout();

        for (Colors colorsCounter : colorsArrayList) {      //loop through every color
            for (String categoryCounter : categoriesSet) {      //loop through every category
                Image colorImage = new Image();                                     //
                colorImage.setSrc(colorsCounter.getLink());                         //  creating objects for every color
                Button colorButton = new Button(colorsCounter.getColor());
                colorButton.setMinWidth("140px");
                TextField colorPriceTextField = new TextField();
                colorPriceTextField.setWidth("140px");
                colorPriceTextField.setValue("Price: " + colorsCounter.getPrice() + "pln");
                colorButton.addClickListener(click->{                               //
                    colorButton.setText(colorButton.getText() + " choosed -->");
                    choosedColor=colorsCounter;
                    horizontalLayoutForEngine.removeAll();
                    engineChoose();
                });

                if (colorsCounter.getCategory().equals(categoryCounter)) {
                    HorizontalLayout horizontalLayoutForActualColor = new HorizontalLayout();

                    if(categoryCounter.equals("normal") && colorsCounter.getDecicatedToCar().contains(choosedModel.getModelName())){ //adding every color to its category
                        horizontalLayoutForActualColor.add(colorButton);
                        horizontalLayoutForActualColor.add(colorImage);
                        horizontalLayoutForActualColor.add(colorPriceTextField);
                        verticalLayoutForNormal.add(horizontalLayoutForActualColor);
                    } else if(categoryCounter.equals("metallic") && colorsCounter.getDecicatedToCar().contains(choosedModel.getModelName())){ //adding every color to its category
                        horizontalLayoutForActualColor.add(colorButton);
                        horizontalLayoutForActualColor.add(colorImage);
                        horizontalLayoutForActualColor.add(colorPriceTextField);
                        verticalLayoutForMetallic.add(horizontalLayoutForActualColor);
                    }else if(categoryCounter.equals("designo") && colorsCounter.getDecicatedToCar().contains(choosedModel.getModelName())){ //adding every color to its category
                        horizontalLayoutForActualColor.add(colorButton);
                        horizontalLayoutForActualColor.add(colorImage);
                        horizontalLayoutForActualColor.add(colorPriceTextField);
                        verticalLayoutForDesigno.add(horizontalLayoutForActualColor);
                    }
                }
            }
        }

        tabs.addSelectedChangeListener(event -> {

            if (event.getSelectedTab().equals(tabNormal)) {         //if user clicks normal it shows every color of this cathegory
                remove(horizontalLayoutForEngine);
                verticalLayoutForConfirmation.removeAll();
                verticalLayoutForColor.removeAll();
                verticalLayoutForColor.add(tabs);
                verticalLayoutForColor.add(verticalLayoutForNormal);
                add(verticalLayoutForColor);
            }
            if (event.getSelectedTab().equals(tabMetallic)) {       //if user clicks metallic it shows every color of this cathegory
                remove(horizontalLayoutForEngine);
                verticalLayoutForConfirmation.removeAll();
                verticalLayoutForColor.removeAll();
                verticalLayoutForColor.add(tabs);
                verticalLayoutForColor.add(verticalLayoutForMetallic);
                add(verticalLayoutForColor);
            }
            if (event.getSelectedTab().equals(tabDesigno)) {        //if user clicks designo it shows every color of this cathegory
                remove(horizontalLayoutForEngine);
                verticalLayoutForConfirmation.removeAll();
                verticalLayoutForColor.removeAll();
                verticalLayoutForColor.add(tabs);
                verticalLayoutForColor.add(verticalLayoutForDesigno);
                add(verticalLayoutForColor);
            }
        });
        add(verticalLayoutForColor);
    }

    public void methodAddHorizontalLayoutEngineChoose(Image engineImage, TextArea engineTextArea) {

        ComboBox<String> engineComboBox = new ComboBox<>(); //combobox of engine choosment
        engineComboBox.setLabel("Choose your engine");
        engineComboBox.setWidth("260px");
        ArrayList<String> engineArrayList = new ArrayList<>(); //auxiliary arraylist for custom combobox options
        for (Engines enginesCounter : enginesArrayList) {
            if (enginesCounter.getDecicatedToModel().contains(choosedModel.getModelName())) {
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
                    if (s.getEngineSize() == Double.parseDouble(str) && s.getDecicatedToModel().contains(choosedModel.getModelName())) {
                        engineImage.setSrc(s.getLink()); //get src of engine
                        engineImage.setWidth("280px");
                        choosedEngine=s; //save choosed engine to variable
                        engineTextArea.setValue(InfoNextToEngine(s)); //setting custom string to textarea
                        engineTextArea.setWidth("350px");
                        horizontalLayoutForEngine.add(engineImage,engineTextArea); //adding image and textarea every time when user changes engine
                        verticalLayoutForConfirmation.removeAll();
                        addCarToRepo();
                    }
                });
            }
        });
        horizontalLayoutForEngine.add(engineComboBox);
        add(horizontalLayoutForEngine);//adding horizontal engine layout to main vertical layout
    }


    public String InfoNextToEngine(Engines engine){
        return String.format("Choosed Mercedes-Benz %s class in basic costs %.2fpln.\nYou choosed color %s %s, addictional price for this color is %dpln.\nEngine you choosed is %.1f %s engine with %dhp and addicitonal price %dpln.\nPrice for car is now %.2fpln.",
                choosedModel.getModelName(), choosedModel.getBasicPrice(),choosedColor.getCategory(), choosedColor.getColor(), choosedColor.getPrice(), engine.getEngineSize(), engine.getTypeOfFuel(), engine.getHpAmount(), engine.getPrice(), choosedModel.getBasicPrice()+(double)engine.getPrice()+choosedColor.getPrice());
    }




}
