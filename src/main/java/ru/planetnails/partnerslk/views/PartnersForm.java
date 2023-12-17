package ru.planetnails.partnerslk.views;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import ru.planetnails.partnerslk.model.partner.Partner;

import java.util.List;


public class PartnersForm extends FormLayout {
    private Partner partner;
    TextField name = new TextField("name");
    TextField discount = new TextField("discount");
    EmailField account = new EmailField("account");

    Binder<Partner> binder = new BeanValidationBinder<>(Partner.class);
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    public PartnersForm(List<Partner> partners) {
        addClassName("contact-form");
        binder.bindInstanceFields(this);

        add(name,
                discount,
                account,
                account,
                createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout( delete, close);
    }
    public void setPartner(Partner partner) {
        this.partner = partner;
        binder.readBean(partner);
    }
    public static abstract class PartnersFormEvent extends ComponentEvent<PartnersForm> {
        private Partner partner;

        protected PartnersFormEvent(PartnersForm source, Partner partner) {
            super(source, false);
            this.partner = partner;
        }

        public Partner getPartner() {
            return partner;
        }
    }

    public static class SaveEvent extends PartnersFormEvent {
        SaveEvent(PartnersForm source, Partner partner) {
            super(source, partner);
        }
    }

    public static class DeleteEvent extends PartnersFormEvent {
        DeleteEvent(PartnersForm source, Partner partner) {
            super(source, partner);
        }

    }

    public static class CloseEvent extends PartnersFormEvent {
        CloseEvent(PartnersForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}

