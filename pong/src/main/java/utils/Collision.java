package utils;

import ball.Ball;
import players.Player;

public class Collision {
    public static boolean pBCollision(Ball ball, Player player) {
        return ball.x + ball.radius >= player.x && ball.x - ball.radius <=player.x + player.width && ball.y >= player.y && ball.y <= player.y + player.height;
    }
}
