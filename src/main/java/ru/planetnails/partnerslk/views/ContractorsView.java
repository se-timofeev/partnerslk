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
import ru.planetnails.partnerslk.model.contractor.Contractor;
import ru.planetnails.partnerslk.service.ContractorService;

@PermitAll
@Route(value = "contractors",layout = MainLayout.class)
@PageTitle("Контрагенты")
public class ContractorsView extends VerticalLayout {
    Grid<Contractor> grid = new Grid<>(Contractor.class);
    TextField filterText = new TextField();
    ContractorService contractorService;

    public ContractorsView(ContractorService contractorService) {
        this.contractorService=contractorService;
        addClassName("contractors-list-view");
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
        grid.addClassNames("contractors-grid");
        grid.setSizeFull();
        grid.setColumns("name", "description", "inn","kpp","legalAddress","actualAddress","partnerName" );
        grid.getColumnByKey("name").setHeader("Наименование");
        grid.getColumnByKey("description").setHeader("Оф.наименование");
        grid.getColumnByKey("inn").setHeader("ИНН");
        grid.getColumnByKey("kpp").setHeader("КПП");
        grid.getColumnByKey("legalAddress").setHeader("Юр.адрес");
        grid.getColumnByKey("actualAddress").setHeader("Факт.адрес");
        grid.getColumnByKey("partnerName").setHeader("Партнёр");
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
        grid.setItems(contractorService.findAllContractors());
    }
}
