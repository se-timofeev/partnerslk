package ru.planetnails.partnerslk.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import jakarta.validation.ValidationException;
import ru.planetnails.partnerslk.model.user.User;

import java.util.List;


public class UsersForm extends FormLayout {
    private User user;
    TextField name = new TextField("name");
    TextField id = new TextField("id");
    TextField mobile = new TextField("mobile");
    TextField email = new TextField("email");


    Binder<User> binder = new BeanValidationBinder<>(User.class);
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    public UsersForm(List<User> partners) {
        addClassName("users-form");
        binder.bindInstanceFields(this);

        add(name,
                mobile,
                email,
                id,
                createButtonsLayout());
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, user)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(user);
            fireEvent(new SaveEvent(this, user));
        } catch (ValidationException e) {
            e.printStackTrace();
        } catch (com.vaadin.flow.data.binder.ValidationException e) {
            throw new RuntimeException(e);
        }
    }
    public void setUser(User user) {
        this.user = user;
        binder.readBean(user);
    }
    public static abstract class UsersFormEvent extends ComponentEvent<UsersForm> {
        private User user;

        protected UsersFormEvent(UsersForm source, User user) {
            super(source, false);
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }

    public static class SaveEvent extends UsersFormEvent {
        SaveEvent(UsersForm source, User user) {
            super(source, user);
        }
    }

    public static class DeleteEvent extends UsersFormEvent {
        DeleteEvent(UsersForm source, User user) {
            super(source, user);
        }

    }

    public static class CloseEvent extends UsersFormEvent {
        CloseEvent(UsersForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }


}

