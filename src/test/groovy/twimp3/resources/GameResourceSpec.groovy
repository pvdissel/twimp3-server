package twimp3.resources

import com.sun.jersey.api.client.Client
import com.sun.jersey.api.client.ClientResponse
import com.sun.jersey.api.client.GenericType
import com.sun.jersey.api.client.WebResource
import com.yammer.dropwizard.testing.ResourceTest
import spock.lang.Specification
import spock.lang.Unroll
import twimp3.api.Game
import twimp3.core.GameRepository

@Unroll
class GameResourceSpec extends Specification {

    TestResource resource = new TestResource()
    GameRepository repo = Mock(GameRepository)

    def 'GET #path results in 200 OK and Game object'() {
        when:
        def response = request(path)
                .get(ClientResponse)
        then:
        repo.getAll() >> [] as Set
        response.status == ClientResponse.Status.OK.statusCode
        response.length == -1
        response.hasEntity()
        def games = response.getEntity(new GenericType<Set<Game>>() {})

        where:
        path     | _
        '/game'  | _
        '/game/' | _
    }

    def 'POST #path results in 201 OK and url of created resource'() {
        given:
        def response = request(path)
                .get(ClientResponse)
        expect:
        response.status == ClientResponse.Status.CREATED.statusCode
        response.length == -1
        response.location.toString().startsWith('/game/')
        response.location.toString().length() == 16
        response.hasEntity()
        def games = response.getEntity(new GenericType<Set<Game>>() {})

        where:
        path     | _
        '/game'  | _
        '/game/' | _
    }

    def setup() {
        resource.setUpJersey()
    }

    def cleanup() {
        resource.tearDownJersey()
    }

    private WebResource.Builder request(String path) {
        return resource.client().resource(path).requestBuilder
    }

    private class TestResource extends ResourceTest {

        @Override
        protected void setUpResources() throws Exception {
            addResource(new GameResource(repo))
        }

        @Override
        protected Client client() {
            return super.client()
        }
    }
}
