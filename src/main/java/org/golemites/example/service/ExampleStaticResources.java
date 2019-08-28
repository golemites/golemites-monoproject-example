package org.golemites.example.service;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.http.whiteboard.propertytypes.HttpWhiteboardResource;

import static org.osgi.service.http.whiteboard.HttpWhiteboardConstants.HTTP_WHITEBOARD_RESOURCE_PATTERN;
import static org.osgi.service.http.whiteboard.HttpWhiteboardConstants.HTTP_WHITEBOARD_RESOURCE_PREFIX;

// Propertytypes are not supported yet.
//@HttpWhiteboardResource(pattern = "/web/*", prefix = "/static")
@Component(
        service = ExampleStaticResources.class,
        property = {
                HTTP_WHITEBOARD_RESOURCE_PATTERN + "=/web/*",
                HTTP_WHITEBOARD_RESOURCE_PREFIX + "=/static"
        })
public class ExampleStaticResources {
}
