package com.company.demo.web.screens;

import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.web.app.mainwindow.AppMainWindow;

import javax.inject.Inject;
import java.util.Map;

public class ExtAppMainWindow extends AppMainWindow {
    @Inject
    private Button demoBtn;

    public static final String GENERATED_SVG_PATH =
            "../../../dispatch/svggen?icon=%s&primaryColor=%s&secondaryColor=%s&";

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        demoBtn.setIcon(getSvgIcon("app", "blue", "green"));
    }

    public String getSvgIcon(String icon, String primaryColor, String secondaryColor) {
        return String.format(GENERATED_SVG_PATH, icon, primaryColor, secondaryColor);
    }
}