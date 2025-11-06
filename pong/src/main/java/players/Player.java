package players;

import static utils.Shapes.*;

public class Player {
    public float x;
    public float y;
    public float width;
    public float height;
    public float playerStartPos;
    public float moveSpeed;

    private float r, g, b;

    public Player(float x, float y, float width, float height, float r, float g, float b) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.playerStartPos = y;
        this.moveSpeed = 0.01f;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void moveUp() {
        this.y += this.moveSpeed;
    }
    public void resetPlayerPos() {
        this.y = this.playerStartPos;
    }
    public void moveDown() {
        this.y -= this.moveSpeed;
    }
    private void drawPlayers() {
        drawRect(this.x, this.y, this.width, this.height, this.r, this.g, this.b);
    }
    public void updatePlayers() {
        drawPlayers();
  }

}
