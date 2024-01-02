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
@Route(value = "users",layout = MainLayout.class)
@PageTitle("Пользователи")
public class UsersView extends VerticalLayout {
    Grid<User> grid = new Grid<>(User.class);
    TextField filterText = new TextField();
    UserService userService;
    UsersForm form;
    public UsersView(UserService userService) {
        this.userService=userService;
        addClassName("contacts-list-view");
        setSizeFull();
        configureGrid();
        configureForm();
        closeEditor();

        add(getToolbar(), getContent());
        updateList();
    }

    private void configureForm() {
        form = new UsersForm(userService.findAllUsers());
        form.setWidth("25em");

        form.addListener(UsersForm.DeleteEvent.class, this::deleteUser);
        form.addListener(UsersForm.CloseEvent.class, e -> closeEditor());
    }
    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1,form);
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

        grid.asSingleSelect().addValueChangeListener(event ->
                editUser(event.getValue()));
    }
    private void closeEditor() {
        form.setUser(null);
        form.setVisible(false);
        removeClassName("editing");
    }
    public void editUser(User user) {
        if (user == null) {
            closeEditor();
        } else {
            form.setUser(user);
            form.setVisible(true);
            addClassName("editing");
        }
    }
    private void deleteUser(UsersForm.DeleteEvent event) {
        userService.delete(event.getUser().getId());
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
        grid.setItems(userService.findAllUsers());
    }
}
