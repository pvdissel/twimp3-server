package twimp3

import com.yammer.dropwizard.config.Environment
import com.yammer.dropwizard.config.FilterBuilder
import org.eclipse.jetty.servlets.CrossOriginFilter
import spock.lang.Specification
import spock.lang.Unroll
import twimp3.resources.GameResource

import static com.yammer.dropwizard.config.HttpConfiguration.ConnectorType.NONBLOCKING
import static org.eclipse.jetty.servlets.CrossOriginFilter.ALLOWED_ORIGINS_PARAM

@Unroll
class ClientApiServiceSpec extends Specification {

    def 'Serves the GameResource'() {
        given:
        def env = Mock(Environment)
        def filterBuilder = Mock(FilterBuilder)
        def service = new TwimpApiService()
        def config = new TwimpApiConfiguration()

        when:
        service.run(config, env)

        then:
        _ * env.addFilter(_, _) >> filterBuilder
        1 * env.addResource(_ as GameResource)
    }

    def 'HTTP connection type is set to non-blocking'() {
        given:
        def env = Mock(Environment)
        def filterBuilder = Mock(FilterBuilder)
        def service = new TwimpApiService()
        def config = new TwimpApiConfiguration()

        when:
        service.run(config, env)

        then:
        _ * env.addFilter(_, _) >> filterBuilder
        config.httpConfiguration.connectorType == NONBLOCKING
    }

    def 'Cross-Origin resource sharing is configured on /*'() {
        given:
        def env = Mock(Environment)
        def filterBuilder = Mock(FilterBuilder)
        def service = new TwimpApiService()
        def config = new TwimpApiConfiguration()

        when:
        service.run(config, env)

        then:
        1 * env.addFilter(CrossOriginFilter, '/*') >> filterBuilder
        1 * filterBuilder.setInitParam(ALLOWED_ORIGINS_PARAM, '*')
    }
}
