package ru.planetnails.partnerslk.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import ru.planetnails.partnerslk.model.partner.Partner;
import ru.planetnails.partnerslk.service.PartnerService;

@PermitAll
@Route(value = "partners", layout = MainLayout.class)
@PageTitle("Партнёры")
public class PartnersView extends VerticalLayout {
    Grid<Partner> grid = new Grid<>(Partner.class);
    TextField filterText = new TextField();
    PartnerService partnerService;

    public PartnersView(PartnerService partnerService) {
        this.partnerService=partnerService;
        addClassName("partners-list-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateList();
    }

    private void configureForm() {

    }
    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }
    private void configureGrid() {
        grid.addClassNames("partner-grid");
        grid.setSizeFull();
        grid.setColumns("name", "discount", "account" );
        grid.getColumnByKey("name").setHeader("Наименование");
        grid.getColumnByKey("account").setHeader("Менеджер");
        grid.getColumnByKey("discount").setHeader("Скидка, %");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Поиск");
        filterText.setClearButtonVisible(true);
        filterText.setWidthFull();
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        var toolbar = new HorizontalLayout(filterText);
        toolbar.addClassName("toolbar");
        return toolbar;
    }
    private void updateList() {
        grid.setItems(partnerService.findAllPartners());
    }
}
