///usr/bin/env jbang "$0" "$@" ; exit $?
// Update the Quarkus version to what you want here or run jbang with
// `-Dquarkus.version=<version>` to override it.
//DEPS io.quarkus:quarkus-bom:${quarkus.version:2.8.0.Final}@pom
//DEPS io.quarkus:quarkus-resteasy-reactive-qute
//DEPS io.quarkus:quarkus-mutiny
//JAVAC_OPTIONS -parameters
//FILES META-INF/resources/templates/Main/index.html=index.html
//FILES META-INF/resources/templates/Main/board.html=board.html
//FILES META-INF/resources/templates/Main/chats.html=chats.html

//SOURCES *.java

package yolo.chatapp.controllers;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.mutiny.Multi;
import org.jboss.resteasy.reactive.RestSseElementType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yolo.chatapp.model.Chat;
import yolo.chatapp.model.Message;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.Instant;

@Path("/chat")
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    @Inject
    Chat chat;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance index(Chat chat);
        public static native TemplateInstance board(Chat chat);
        public static native TemplateInstance chats(Chat chat);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        logger.info("New client");
        return Templates.index(chat);
    }

    @POST
    @Path("send")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance newMessage(@FormParam("newMessage") String newMessage) {
        if (newMessage.isBlank()) {
            logger.info("Blank message, discarding");
        } else {
            logger.info("New message: {}", newMessage);
            chat.push(new Message(Instant.now(), newMessage));
        }
        return Templates.board(chat);
    }

    @GET
    @Path("updates")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @RestSseElementType(MediaType.TEXT_HTML)
    public Multi<String> updates() {
        logger.info("New SSE client");
        return chat.updatesBroadcaster()
                .onItem().invoke(() -> logger.info("Pushing updates"))
                .onItem().transform(tick -> Templates.chats(chat).render());
    }
}
