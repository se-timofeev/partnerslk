package ru.planetnails.partnerslk.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import ru.planetnails.partnerslk.model.group.Group;
import ru.planetnails.partnerslk.repository.groupRepository.CustomGroupRepository;
import ru.planetnails.partnerslk.repository.groupRepository.GroupRepository;

@PermitAll
@Route(value = "items",layout = MainLayout.class)
@PageTitle("Номенклатура")
public class ItemsView extends VerticalLayout {

    TextField filterText = new TextField();
    CustomGroupRepository groupRepository;

    public ItemsView(GroupRepository groupRepository) {
        this.groupRepository=groupRepository;
        addClassName("items-list-view");
        setSizeFull();
        configureGrid();
//        configureForm();
//
//        add(getToolbar(), getContent());
//        updateList();
    }

    private void configureForm() {

    }
    private Component getContent() {
//
        return null;
    }
    private void configureGrid() {
//        treeGrid.addClassNames("group-grid");
//        treeGrid.setSizeFull();
//        treeGrid.setColumns("name"  );
//        treeGrid.getColumnByKey("name").setHeader("Наименование");
//        treeGrid.getColumns().forEach(col -> col.setAutoWidth(true));

      //  Tree<Group> tree = new Tree<>(Group::getName);
        TreeGrid<Group> treeGrid =   new TreeGrid<>();
        treeGrid.setItems(groupRepository.getRootGroups(),groupRepository::getChildGroups);
        treeGrid.addHierarchyColumn(Group::getName).setHeader(" Name");
        //treeGrid.addColumn(Group::getManager).setHeader("Manager");

//        treeGrid
//                .asSingleSelect()
//                .addValueChangeListener(
//                        event -> {
//                            refreshChildItems(event.getOldValue());
//                            refreshChildItems(event.getValue());
//                        }
//                );
//
//        treeGrid.setClassNameGenerator(
//                department -> {
//                    if (
//                            treeGrid.asSingleSelect().getValue() != null &&
//                                    treeGrid.asSingleSelect().getValue().equals(department.getParent())
//                    ) {
//                        return "parent-selected";
//                    } else return null;
//                }
//        );
        treeGrid.setAllRowsVisible(true);
        add(treeGrid);
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
       // treeGrid.setItems(groupRepository.findAll());
    }
}
