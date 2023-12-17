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
    PartnersForm form;

    public PartnersView(PartnerService partnerService) {
        this.partnerService=partnerService;
        addClassName("partners-list-view");
        setSizeFull();
        configureGrid();
        configureForm();
        closeEditor();

        add(getToolbar(), getContent());
        updateList();
    }

    private void configureForm() {
        form = new PartnersForm(partnerService.findAllPartners());
        form.setWidth("25em");

       form.addListener(PartnersForm.DeleteEvent.class, this::deletePartner);
        form.addListener(PartnersForm.CloseEvent.class, e -> closeEditor());

    }
    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid,form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1,form);
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

        grid.asSingleSelect().addValueChangeListener(event ->
                editPartner(event.getValue()));
    }
    private void closeEditor() {
        form.setPartner(null);
        form.setVisible(false);
        removeClassName("editing");
    }
    public void editPartner(Partner partner) {
        if (partner == null) {
            closeEditor();
        } else {
            form.setPartner(partner);
            form.setVisible(true);
            addClassName("editing");
        }
    }
    private void deletePartner(PartnersForm.DeleteEvent event) {
        partnerService.delete(event.getPartner().getId());
        updateList();
        closeEditor();
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
