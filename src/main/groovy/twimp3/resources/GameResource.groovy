package twimp3.resources

import com.google.common.collect.Sets
import groovy.util.logging.Slf4j
import twimp3.api.Game
import twimp3.core.GameRepository

import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.core.GenericEntity
import javax.ws.rs.core.Response

@Slf4j
@Path('game')
class GameResource {

    GameRepository repo

    GameResource(GameRepository repo) {
        this.repo = repo
    }

    @GET
    Response get() {
        def entity = new GenericEntity<Set<Game>>(Sets.newHashSet(repo.all)) {}
        return Response.ok(entity).build()
    }

    @POST
    Response create() {
        def game = repo.create()
        return Response.created("/game/${game.id}").build()
    }

    @GET
    @Path('{id}')
    Game get(@PathParam('id') String id) {
        return repo.getById(id)
    }
}
