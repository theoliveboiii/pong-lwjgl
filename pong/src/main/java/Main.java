import ball.Ball;
import input.InputHandler;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import players.Player;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;
import static utils.Collision.pBCollision;
import static utils.Shapes.drawRect;

public class Main {

    private long window;
    public GLFWKeyCallback keyCallback;
    int windowWidth = 1920;
    int windowHeight = 1080;


    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {



        GLFWErrorCallback.createPrint(System.err).set();

        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        window = glfwCreateWindow(windowWidth, windowHeight, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, keyCallback = new InputHandler());

        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            glfwGetWindowSize(window, pWidth, pHeight);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);

        glfwShowWindow(window);
    }


    private void loop() {
        GL.createCapabilities();
        glClearColor(0.1f, 0.0f, 0.1f, 0.0f);

        float playerHeight = 0.4f;
        float playerWidth = 0.05f;
        float player1y = 0f - 0.075f - playerHeight/2;
        float player2y = 0f - 0.075f - playerHeight/2;

        Player p1 = new Player(-1 + playerWidth, player1y, playerWidth, playerHeight, 0.5f, 0.2f, 0.1f);
        Player p2 = new Player(1 - 2 * playerWidth, player2y, playerWidth, playerHeight, 0f, 0.5f, 0.8f);

        float bx = 0;
        float by = -0.075f;
        float r = 0.03f;

        Ball ball = new Ball(bx, by, r, -0.004f,-0.003f);

        while ( !glfwWindowShouldClose(window) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            ball.screwBounce = 1f;
            if(InputHandler.keys[GLFW_KEY_S] && p1.y >= -1) {
                p1.moveDown();
                ball.screwBounce = 1.2f;
            }
            if(InputHandler.keys[GLFW_KEY_W] && p1.y + playerHeight <= 0.85f) {
                p1.moveUp();
                ball.screwBounce = 1.2f;
            }
            if(InputHandler.keys[GLFW_KEY_DOWN] && p2.y >= -1) {
                p2.moveDown();
                ball.screwBounce = 1.2f;
            }
            if(InputHandler.keys[GLFW_KEY_UP] && p2.y + playerHeight <= 0.85f) {
                p2.moveUp();
                ball.screwBounce = 1.2f;
            }
            if (InputHandler.keys[GLFW_KEY_R]) {
                ball.resetBall();
            }
            if (pBCollision(ball, p1) || pBCollision(ball, p2)) {
                ball.playerBounce();
            }

            if (ball.y + ball.radius >= 0.85f || ball.y - ball.radius <= -1) {
                ball.wallBounce();
            }

            if (ball.x >= 1) {
                p2.moveSpeed *= 1.05f;
                ball.resetBall();
                p1.resetPlayerPos();
                p2.resetPlayerPos();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else if (ball.x <= -1) {
                ball.resetBall();
                p1.resetPlayerPos();
                p2.resetPlayerPos();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            p1.updatePlayers();
            p2.updatePlayers();

            ball.spawnBall();
            drawRect(-1,0.85f,2,0.15f, 0.4f, 0.4f, 0.4f);

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }


    public static void main(String[] args) {
        new Main().run();
    }

}