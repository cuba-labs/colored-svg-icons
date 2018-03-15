package com.company.demo.web.screens;

import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.web.app.mainwindow.AppMainWindow;

import javax.inject.Inject;
import java.util.Map;

public class ExtAppMainWindow extends AppMainWindow {
    @Inject
    private Button demoBtn;

    public static final String GENERATED_SVG_PATH = "../../../dispatch/svggen?icon=%s&color=%s&v";

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        demoBtn.setIcon(getSvgIcon("app", "blue"));
    }

    public String getSvgIcon(String icon, String color) {
        return String.format(GENERATED_SVG_PATH, icon, color);
    }
}