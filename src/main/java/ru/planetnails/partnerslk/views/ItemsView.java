package ru.planetnails.partnerslk.views;

import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import ru.planetnails.partnerslk.model.group.Group;
import ru.planetnails.partnerslk.model.item.Item;
import ru.planetnails.partnerslk.repository.groupRepository.CustomGroupRepository;
import ru.planetnails.partnerslk.repository.groupRepository.GroupRepository;
import ru.planetnails.partnerslk.repository.itemRepository.ItemRepository;


@PermitAll
@Route(value = "items", layout = MainLayout.class)
@PageTitle("Номенклатура")
public class ItemsView extends VerticalLayout {


    TextField filterText = new TextField();
    TreeGrid<Group> treeGrid = new TreeGrid<>(Group.class);
    Grid<Item> grid = new Grid<>(Item.class);
    CustomGroupRepository groupRepository;
    ItemRepository itemRepository;

    public ItemsView(GroupRepository groupRepository, ItemRepository itemRepository) {
        this.groupRepository = groupRepository;
        this.itemRepository = itemRepository;
        addClassName("items-list-view");
        setSizeFull();
        configureForm();
        updateList();
    }


    private void configureForm() {

        configureTreeGrid();
        configureGrid();

        HorizontalLayout layout = new HorizontalLayout(treeGrid, grid);
        layout.setMargin(true);
        layout.setPadding(false);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);
        layout.setSizeFull();

        layout.add(treeGrid);
        layout.add(grid);
        layout.addClassNames("content");
        layout.setSizeFull();
        add(layout);
    }

    public void configureTreeGrid() {

        treeGrid.addClassName("tree-grid");
        treeGrid.setSizeFull();
        treeGrid.setWidth("420px");
        treeGrid.setColumns("name");
        treeGrid.setHierarchyColumn("name").
                setHeader("Группы").
                setResizable(true).
                setWidth("400px");

    }

    public void configureGrid() {
        grid.addClassNames("items-grid");
        grid.setSizeFull();
        grid.setColumns("vendorCode", "description", "countryOfOrigin");

        grid.getColumnByKey("vendorCode")
                .setHeader("Артикул")
                .setAutoWidth(false)
                .setResizable(true)
                .setWidth("50px");

        grid.getColumnByKey("description")
                .setHeader("Наименование")
                .setResizable(true)
                .setAutoWidth(true);

        grid.getColumnByKey("countryOfOrigin")
                .setHeader("Страна")
                .setAutoWidth(false)
                .setResizable(true)
                .setWidth("50px");

        grid.addColumn("price.sale").setHeader("Цена опт.").setTextAlign(ColumnTextAlign.END);
        grid.addColumn("price.retail").setHeader("Цена розн.").setTextAlign(ColumnTextAlign.END);
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
        treeGrid.setItems(groupRepository.getRootGroups(), groupRepository::getChildGroups);

        treeGrid
                .asSingleSelect()
                .addValueChangeListener(
                        event -> {
                            refreshChildItems(event.getOldValue());
                            refreshChildItems(event.getValue());
                        }
                );

        treeGrid.setAllRowsVisible(true);
    }

    private void refreshChildItems(Group group) {
        if (group != null) {
            grid.setItems(itemRepository.findItemsBySecondLevelGroupAll(group.getId()));
        }
    }
}
