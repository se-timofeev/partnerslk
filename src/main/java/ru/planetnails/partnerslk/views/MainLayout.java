package ru.planetnails.partnerslk.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.security.AuthenticationContext;
import com.vaadin.flow.theme.lumo.LumoUtility;
import ru.planetnails.partnerslk.security.config.SecurityService;

public class MainLayout extends AppLayout {
    private final transient AuthenticationContext authContext;
    private final SecurityService securityService;
    public MainLayout(AuthenticationContext authContext, SecurityService securityService) {
        this.authContext = authContext;
        this.securityService=securityService;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("b2b портал Planet Nails");
        logo.addClassNames(
                LumoUtility.FontSize.LARGE,
                LumoUtility.Margin.MEDIUM);


        Button logout = new Button("Выход ", e -> securityService.logout());

        var header = new HorizontalLayout(new DrawerToggle(), logo, logout);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidthFull();
        header.addClassNames(
                LumoUtility.Padding.Vertical.NONE,
                LumoUtility.Padding.Horizontal.MEDIUM);

        addToNavbar(header);

    }

    private void createDrawer() {
        addToDrawer(new VerticalLayout(
                new RouterLink("Партнёры", PartnersView.class),
                new RouterLink("Контрагенты", ContractorsView.class),
                new RouterLink("Пользователи", UsersView.class),
                new RouterLink("Номенклатура", ItemsView.class)
        ));
    }
}
