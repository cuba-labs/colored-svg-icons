package com.company.demo.web.screens;

import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.components.PickerField;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.web.app.mainwindow.AppMainWindow;
import com.haulmont.cuba.web.toolkit.ui.CubaPickerField;
import com.vaadin.server.ThemeResource;

import javax.inject.Inject;
import java.util.Map;

public class ExtAppMainWindow extends AppMainWindow {
    @Inject
    private Button demoBtn;

    public static final String GENERATED_SVG_PATH =
            "../../../dispatch/svggen?icon=%s&primaryColor=%s&secondaryColor=%s&";
    @Inject
    private PickerField pickerField;
    @Inject
    private PickerField pickerFieldSingle;
    @Inject
    private Table<User> usersTable;
    @Inject
    private ComponentsFactory componentsFactory;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        demoBtn.setIcon(getSvgIcon("app", "blue", "green"));

        setIconToPickerField(pickerField, getSvgIcon("app", "blue", "green"));
        setIconToPickerField(pickerFieldSingle, getSvgIcon("app", "blue", "green"));

        // Show user group with icon
        usersTable.addGeneratedColumn("group", entity -> {
            Label label = componentsFactory.createComponent(Label.class);
            label.setStyleName("svg-icon");
            label.setValue(entity.getGroup());
            label.setIcon(getSvgIcon("app", "blue", "green"));
            return label;
        });
    }

    // Helper method to set icon into PickerField
    protected void setIconToPickerField(PickerField pickerField, String iconPath) {
        CubaPickerField impl = pickerField.unwrap(CubaPickerField.class);
        impl.getField().addStyleName("inline-icon");
        impl.getField().setIcon(new ThemeResource(iconPath));
    }

    public String getSvgIcon(String icon, String primaryColor, String secondaryColor) {
        return String.format(GENERATED_SVG_PATH, icon, primaryColor, secondaryColor);
    }
}