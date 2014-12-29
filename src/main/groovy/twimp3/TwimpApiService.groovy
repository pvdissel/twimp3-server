package twimp3

import com.yammer.dropwizard.Service
import com.yammer.dropwizard.config.Bootstrap
import com.yammer.dropwizard.config.Environment
import com.yammer.dropwizard.config.FilterBuilder
import org.eclipse.jetty.servlets.CrossOriginFilter
import twimp3.core.GameRepository
import twimp3.resources.GameResource

import static com.yammer.dropwizard.config.HttpConfiguration.ConnectorType.NONBLOCKING

class TwimpApiService extends Service<TwimpApiConfiguration> {
    @Override
    void initialize(Bootstrap<TwimpApiConfiguration> bootstrap) {
        bootstrap.name = 'twimp3-servers'
    }

    @Override
    void run(TwimpApiConfiguration config, Environment env) throws Exception {
        forceNonBlockingConnectionType(config);
        addCrossOriginSupport(env)

        def repository = new GameRepository()

        env.addResource(new GameResource(repository))
    }

    private void addCrossOriginSupport(Environment env) {
        FilterBuilder filterConfig = env.addFilter(CrossOriginFilter, "/*");
        filterConfig.setInitParam(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
    }

    private void forceNonBlockingConnectionType(TwimpApiConfiguration config) {
        config.getHttpConfiguration().setConnectorType(NONBLOCKING)
    }
}
