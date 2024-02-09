package game.renderer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.*;

import game.engine.Window;
import game.util.AssetPool;
import net.sourceforge.plantuml.graphic.Line;

public class DebugDraw {
	private static int MAX_LINES = 500;

	private static List<Line2D> lines = new ArrayList<>();
	// 6 Floats per vertex, 2 per line
	private static float[] vertexArray = new float[MAX_LINES * 6 * 2];
	private static Shader shader = AssetPool.getShader("assets/shaders/debugLine2D.glsl");

	private static int vaoID;
	private static int vboID;


	private static boolean started = false;

	public static void start() {
		// Generate VAO
		vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);

		// Create vbo buffer mem
		vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, vertexArray.length * Float.BYTES, GL_DYNAMIC_DRAW);

		// Enable VertexArray attributs
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * Float.BYTES, 0);
		glEnableVertexAttribArray(0);

		glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * Float.BYTES, 3 * Float.BYTES);
		glEnableVertexAttribArray(1);

		glLineWidth(2.0f);
	}


	public static void beginFrame() {
		if(!started) {
			start();
			started = true;
		}

		// Remove dead lines
		for (int i=0; i < lines.size(); i++) {
			if(lines.get(i).beginFrame() < 0) {
				// System.out.println("REmove line");
				lines.remove(i);
				i--;
			}
		}

	}
	public static void draw() {
		if(lines.size() <= 0) return;

		int index = 0;
		for (Line2D line : lines) {
			for (int i=0; i < 2; i++) {
				Vector2f position = i == 0 ? line.getFrom() : line.getTo();
				Vector3f color = line.getColor();

				// Load Position
				vertexArray[index] = position.x;
				vertexArray[index + 1] = position.y;
				vertexArray[index + 2] = 10.0f;

				// Load Color
				vertexArray[index + 3] = color.x;
				vertexArray[index + 4] = color.y;
				vertexArray[index + 5] = color.z;

				index += 6;
			}
		}

		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferSubData(GL_ARRAY_BUFFER, 0, Arrays.copyOfRange(vertexArray, 0, lines.size() * 6 * 2));
		// glBufferSubData(GL_ARRAY_BUFFER, 0, vertexArray);

		// Use our shader
		shader.use();
		shader.uploadMat4f("uProjection", Window.getScene().camera().getProjectionMatrix());
		shader.uploadMat4f("uView", Window.getScene().camera().getViewMatrix());

		glBindVertexArray(vaoID);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);

		// Draw
		glDrawArrays(GL_LINES, 0, lines.size() * 2);

		// Disable location
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glBindVertexArray(0);

		shader.detach();
	}

	// ================================
	// Add Line2D Methods
	// ================================
	public static void addLine2D(Vector2f from, Vector2f to) {
		//TODO add color for constatn color
		addLine2D(from, to, new Vector3f(0, 1, 0), 1);
	}

	public static void addLine2D(Vector2f from, Vector2f to, Vector3f color) {
		addLine2D(from, to, color, 1);
	}

	public static void addLine2D(Vector2f from, Vector2f to, Vector3f color, int lifetime) {
		// System.out.println("New Line");
		if(lines.size() >= MAX_LINES) return;
		DebugDraw.lines.add(new Line2D(from, to, color, lifetime));
	}
}