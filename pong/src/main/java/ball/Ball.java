package ball;

import static utils.Shapes.*;

public class Ball {
    public float radius;
    public float x;
    public float y;
    public float ballSpeed;
    public float defaultBallSpeed;
    public float ballBounceAngle;
    public float screwBounce;
    public Ball(float x, float y, float radius, float ballSpeed, float ballBounceAngle) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.ballSpeed = ballSpeed;
        this.defaultBallSpeed = ballSpeed;
        this.ballBounceAngle = (float) (Math.random() * 2 - 1)/100;
        this.screwBounce = 0;
    }
    public void spawnBall() {
        drawCircle(this.x, this.y, this.radius, 20);
        this.x += this.ballSpeed;
        this.y += this.ballBounceAngle;
    }

    public void playerBounce() {
        this.ballBounceAngle *= this.screwBounce;
        this.ballSpeed *= -1.1f;
    }
    public void wallBounce() {
        this.ballBounceAngle *= -1;
    }
    public void resetBall() {
        this.x = 0;
        this.y = 0;
        this.ballSpeed = defaultBallSpeed;
        if ((int) (Math.random() * 2) == 0 ) {
            this.ballSpeed *= -1;
        }
        this.ballBounceAngle = (float) (Math.random() * 2 - 1)/100;
    }
}