package pl.kremiec.carconfigurator.gui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kremiec.carconfigurator.objects.CompletedCar;
import pl.kremiec.carconfigurator.repo.CompletedCarRepo;

import java.util.ArrayList;
import java.util.List;

@Route("orders")
public class MadeOrdersGui extends VerticalLayout {

    List<CompletedCar> carList = new ArrayList<>();
    CompletedCarRepo completedCarRepo;      //orders made repo
    HorizontalLayout horizontalLayoutForClickCar; //horizontal layout for car/color/engine photos

    @Autowired
    MadeOrdersGui(CompletedCarRepo completedCarRepo){

        horizontalLayoutForClickCar = new HorizontalLayout();
        this.completedCarRepo=completedCarRepo;

        gridInput(); //implementation of grid
    }

    private void gridInput(){

        completedCarRepo.findAll().forEach(s->carList.add(s));
        Grid<CompletedCar> grid = new Grid<>(CompletedCar.class);
        grid.setItems(carList);

        grid.addItemClickListener(click-> { //if user clicks it will show car/color/engine photo

            horizontalLayoutForClickCar.removeAll();

            Image carImage = new Image();
            carImage.setSrc(click.getItem().getModelSrc());
            carImage.setWidth("30%");
            carImage.setHeight("30%");
            Image colorImage = new Image();
            colorImage.setSrc(click.getItem().getColorSrc());
            colorImage.setWidth("17%");
            colorImage.setHeight("17%");
            Image engineImage = new Image();
            engineImage.setSrc(click.getItem().getEngineSrc());
            engineImage.setWidth("30%");
            engineImage.setHeight("30%");

            horizontalLayoutForClickCar.add(carImage, colorImage, engineImage);
            add(horizontalLayoutForClickCar);
        });

        grid.setColumns("modelName", "engineSize", "fuelType", "colorName", "colorCategory", "localDateTime");
        add(grid);
    }


}
