package twimp3.core

import spock.lang.Specification

class GameRepositorySpec extends Specification {

    def 'Create Random Game IDs'() {
        def repository = new GameRepository()
        expect:
        repository.createGameId().length() == 10
    }

    def 'New Game has ID'() {
        def repository = new GameRepository()

        expect:
        def game = repository.create()
        game.id
    }
}
