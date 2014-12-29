package twimp3

import com.yammer.dropwizard.Service

class Main {
    static void main(String... args) {
        new Main().createService().run(args)
    }

    Service createService() {
        new TwimpApiService()
    }
}
