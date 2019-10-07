package org.golemites.example.service;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants.JAX_RS_APPLICATION_SELECT;
import static org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants.JAX_RS_RESOURCE;

@Component(
        property = {
                JAX_RS_APPLICATION_SELECT + "=(osgi.jaxrs.name=.default)",
                JAX_RS_RESOURCE + "=true",
        },
        scope = ServiceScope.PROTOTYPE,
        service = StaticBridge.class
)
@Path( "/" )
public class StaticBridge
{
    @Context
    UriInfo uriInfo;

    private final Logger LOG = LoggerFactory.getLogger( StaticBridge.class );

    private final String RESOURCE_STORAGE = "/static/";

    @GET
    @Path("/{path: .*}")
    @Produces( MediaType.TEXT_HTML)
    public Response home(){
        LOG.info( "Received: " + uriInfo.getPath() );
        CacheControl cc = new CacheControl();
        //cc.setMaxAge(86400);
        cc.setPrivate(true);
        cc.setNoCache( true );

        String view = "unconfigured.html";
        //if (state.isConfigured()) {
            view = "index.html";
        //}
        Response.ResponseBuilder builder = Response.ok(
                getClass().getResourceAsStream(RESOURCE_STORAGE + view)).type( MediaType.TEXT_HTML_TYPE );
        builder.cacheControl(cc);

        return builder.build();
    }

    @GET
    @Path("/{select: .*}/{path: .*}")
    public Response res(@Context UriInfo uriInfo, @PathParam ("path") String path, @PathParam ("select") String select){
        LOG.info( "Received selected: " + path );
        CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
        cc.setPrivate(true);

        String type = selectMediaType(select,path);
        Response.ResponseBuilder builder = Response.ok(
                getClass().getResourceAsStream(RESOURCE_STORAGE + select + "/" + path)).type( type );
        builder.cacheControl(cc);

        return builder.build();
    }

    private String selectMediaType( String select, String path )
    {
        String mediaType = "text/" + select; // default..

        if (path.endsWith( ".js" )) {
            return "text/javascript";
        }else if (path.endsWith( ".jpeg" )) {
            return "image/jpeg";
        }else if (path.endsWith( ".jpg" )) {
            return "image/jpeg";
        }else if (path.endsWith( ".png" )) {
            return "image/png";
        }
        return mediaType;
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response configure(@FormParam("projectname") String projectname, @FormParam("gitrepo") String gitrepo) {
        LOG.warn("--- XX Got POST: " + uriInfo.getAbsolutePath() + " for " + gitrepo + " for repo " + projectname);

        return Response.seeOther( uriInfo.getAbsolutePath() ).build();
    }

}

