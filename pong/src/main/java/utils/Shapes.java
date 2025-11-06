package utils;

import static org.lwjgl.opengl.GL11.*;

public class Shapes {
    public static float aspectRatio  = 1920f / 1080f;

    public static void drawRect(float x, float y, float width, float height, float r, float g, float b) {
        glColor3f(r, g, b);

        glBegin(GL_QUADS);
        glVertex2f(x,  y);
        glVertex2f(x + width,  y);
        glVertex2f(x + width, y + height);
        glVertex2f( x, y + height);
        glEnd();
    }
    public static void Triangle(float x1, float y1, float x2, float y2, float x3, float y3) {
        glColor3f(1f, 0.5f, 0f);

        glBegin(GL_TRIANGLES);
        glVertex2f(x1, y1);
        glVertex2f(x2, y2);
        glVertex2f(x3, y3);
        glEnd();
    }

    public static void drawCircle(float cx, float cy, float r, int num_segments) {
        glColor3f(1f, 1f, 1f);

        glBegin(GL_TRIANGLE_FAN);
        glVertex2f(cx, cy); // Center of circle

        for (int i = 0; i <= num_segments; i++) {
            float angle = (float)(2 * Math.PI * i / num_segments);
            float x = cx + r * (float)Math.cos(angle) ;
            float y = cy + r * (float)Math.sin(angle) * aspectRatio;
            glVertex2f(x, y);
        }

        glEnd();
    }
}
