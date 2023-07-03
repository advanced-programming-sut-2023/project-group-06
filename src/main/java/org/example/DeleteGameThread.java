package org.example;

public class DeleteGameThread extends Thread {
    @Override
    public void run() {
        while (true) {
            for (int i = Data.getAllWaitingGames().size() - 1; i >= 0; i--) {
                Game game = Data.getAllWaitingGames().get(i);
                if ((System.currentTimeMillis() / 1000L) - game.getStartTime() > 300) Data.removeGame(game);
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
