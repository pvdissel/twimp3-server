package twimp3.core

import twimp3.api.Game

class GameRepository {
    private Set<Game> games = new LinkedHashSet<>()

    Set<Game> getAll() {
        return new LinkedHashSet<Game>(games)
    }

    Game getById(String id) {
        return games.find { it.id == id }
    }

    boolean hasById(String id) {
        return games.find { it.id == id }
    }

    Game create() {
        def g = new Game()
        g.id = createGameId()
        return g
    }

    String createGameId() {
        def pool = ['a'..'z', 'A'..'Z', 0..9, '_'].flatten()
        Random rand = new Random(System.currentTimeMillis())

        def randChars = (0..9).collect { pool[rand.nextInt(pool.size())] }
        def id = randChars.join()
        if (hasById(id)) {
            id = createGameId()
        }
        return id
    }
}
