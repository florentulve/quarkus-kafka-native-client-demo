package dev.flo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.OnOverflow;

import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RoutingExchange;
import io.smallrye.mutiny.Uni;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.Context;

@Path("/word")
public class WordResource{

    @Inject @Channel("words-internal")
    Emitter<String> emitter;
    
    @Inject
    Vertx vertx;

    @GET
    @Path("/say")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Word> say(@QueryParam("word") String word){
        /*CompletableFuture<String> ret = new CompletableFuture<>();
        vertx.setTimer(100, res -> {
            ret.complete(Context.isOnEventLoopThread() ? Thread.currentThread().getName()+"OK" : "not on event loop");
        });*/
        //System.out.println(Thread.currentThread().getName());
        Word w = new Word();
        w.setContent(Thread.currentThread().getName()+" "+word);
        //return Uni.createFrom().item(Response.status(Status.OK).entityw).build());
        return Uni.createFrom().item(w);
        //return Uni.createFrom().completionStage(ret);
    }

}