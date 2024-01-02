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
import ru.planetnails.partnerslk.model.contractor.Contractor;

import java.util.List;


public class ContractorsForm extends FormLayout {
    private Contractor contractor;
    TextField name = new TextField("name");
    TextField description = new TextField("description");
    TextField partnername = new TextField("partnername");
    TextField inn = new TextField("inn");
    TextField kpp = new TextField("kpp");
    TextField legal_address = new TextField("legal_address");


    Binder<Contractor> binder = new BeanValidationBinder<>(Contractor.class);
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    public ContractorsForm(List<Contractor> contractors) {
        addClassName("contractros-form");
        binder.bindInstanceFields(this);

        add(name,
                description,
                partnername,
                inn,
                kpp,
                legal_address,
                createButtonsLayout());
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, contractor)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(contractor);
            fireEvent(new SaveEvent(this, contractor));
        } catch (ValidationException e) {
            e.printStackTrace();
        } catch (com.vaadin.flow.data.binder.ValidationException e) {
            throw new RuntimeException(e);
        }
    }
    public void SetContractor(Contractor contractor) {
        this.contractor = contractor;
        binder.readBean(contractor);
    }
    public static abstract class ContractorsFormEvent extends ComponentEvent<ContractorsForm> {
        private Contractor contractor;

        protected ContractorsFormEvent(ContractorsForm source, Contractor contractor) {
            super(source, false);
            this.contractor = contractor;
        }

        public Contractor getContractor() {
            return contractor;
        }
    }

    public static class SaveEvent extends ContractorsFormEvent {
        SaveEvent(ContractorsForm source, Contractor contractor) {
            super(source, contractor);
        }
    }

    public static class DeleteEvent extends ContractorsFormEvent {
        DeleteEvent(ContractorsForm source, Contractor contractor) {
            super(source, contractor);
        }

    }

    public static class CloseEvent extends ContractorsFormEvent {
        CloseEvent(ContractorsForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }


}

