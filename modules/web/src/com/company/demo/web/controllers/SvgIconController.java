package com.company.demo.web.controllers;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.haulmont.cuba.core.global.Resources;
import org.slf4j.Logger;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Controller
public class SvgIconController {
    public static final String IMAGE_SVG_CONTENT_TYPE = "image/svg+xml";

    public static final String PRIMARY_COLOR = "magenta";
    public static final String SECONDARY_COLOR = "purple";

    @Inject
    private Logger log;
    @Inject
    private Resources resources;

    // Supported icons
    public static final Map<String, String> icons = ImmutableMap.of(
            "app", "/com/company/demo/web/controllers/sample-icon.svg"
    );

    // Supported colors
    public static final Set<String> colors = Sets.newHashSet(
            "blue",
            "black",
            "green"
    );

    @RequestMapping("svggen")
    @ResponseBody
    public ResponseEntity<String> getIcon(
            @RequestParam(name = "icon") String iconName,
            @RequestParam(name = "primaryColor") String primaryColor,
            @RequestParam(name = "secondaryColor") String secondaryColor) {

        if (!colors.contains(primaryColor)
                || !colors.contains(secondaryColor)) {
            log.debug("Unsupported primaryColor {}", primaryColor);

            return ResponseEntity.notFound().build();
        }

        String iconPath = icons.get(iconName);
        if (iconPath == null) {
            log.debug("Icon not found {}", iconName);

            return ResponseEntity.notFound().build();
        }

        String iconSvgContent = resources.getResourceAsString(iconPath);
        if (iconSvgContent == null) {
            log.warn("Icon resource not found in classpath {}", iconName);

            return ResponseEntity.notFound().build();
        }

        // you can replace
        String iconSvgContentWithColor = iconSvgContent
                .replace(PRIMARY_COLOR, primaryColor)
                .replace(SECONDARY_COLOR, secondaryColor);

        // return content with cache options
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(3600, TimeUnit.SECONDS))
                .header(HttpHeaders.PRAGMA, "cache")
                .header(HttpHeaders.EXPIRES, String.valueOf(System.currentTimeMillis() + 3600))
                .contentType(MediaType.valueOf(IMAGE_SVG_CONTENT_TYPE))
                .body(iconSvgContentWithColor);
    }
}