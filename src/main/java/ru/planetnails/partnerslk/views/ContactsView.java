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
import ru.planetnails.partnerslk.model.user.User;
import ru.planetnails.partnerslk.service.UserService;

@PermitAll
@Route(value = "contacts",layout = MainLayout.class)
@PageTitle("Контакты")
public class ContactsView extends VerticalLayout {
    Grid<User> grid = new Grid<>(User.class);
    TextField filterText = new TextField();
    UserService userService;

    public ContactsView(UserService userService) {
        this.userService=userService;
        addClassName("contacts-list-view");
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
        grid.setColumns("firstName", "lastName", "mobile","email","name","status" );
        grid.getColumnByKey("firstName").setHeader("Имя");
        grid.getColumnByKey("lastName").setHeader("Фамилия");
        grid.getColumnByKey("mobile").setHeader("Моб.");
        grid.getColumnByKey("email").setHeader("email");
        grid.getColumnByKey("name").setHeader("Логин");
        grid.getColumnByKey("status").setHeader("Статус");
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
        grid.setItems(userService.findAllUsers());
    }
}
