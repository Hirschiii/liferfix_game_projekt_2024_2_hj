package game.editor;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

import game.engine.GameObject;
import game.engine.MouseListener;
import game.renderer.PickingTexture;
import game.scene.Scene;
import imgui.ImGui;

public class PropertiesWindow {
	private GameObject activeGameObject = null;
	private PickingTexture pickingTexture;

	private float debounce = 0.2f;

	public PropertiesWindow(PickingTexture pickingTexture) {
		this.pickingTexture = pickingTexture;
	}

	public void update(float dt, Scene currentScene) {
		debounce -= dt;

		if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT) && debounce < 0) {
			int x = (int) MouseListener.getScreenX();
			int y = (int) MouseListener.getScreenY();

			int gameObjectId = pickingTexture.readPixel(x, y);
			System.out.println(gameObjectId);
			activeGameObject = currentScene.getGameObject(gameObjectId);
			this.debounce = 0.2f;
		}

	}

	public void imgui() {

		if (activeGameObject != null) {
			ImGui.begin("Properties");
			activeGameObject.imgui();
			ImGui.end();
		}
	}

	public void setActiveGameObject(int gameObjectId) {
		// this.activeGameObject.setActiveGameObject(false);
		// this.activeGameObject.getComponent(SpriteRenderer.class).setDirty();
		// this.activeGameObject = getGameObject(gameObjectId);
		// this.activeGameObject.setActiveGameObject(true);
		// this.activeGameObject.getComponent(SpriteRenderer.class).setDirty();
	}

	public GameObject getActiveGameObject() {
		return this.activeGameObject;
	}

	public int readPixel(int x, int y) {
		return pickingTexture.readPixel(x, y);
	}
}