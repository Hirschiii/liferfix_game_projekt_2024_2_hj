package game.components;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

import org.joml.Vector2f;
import org.joml.Vector4f;

import game.editor.PropertiesWindow;
import game.engine.GameObject;
import game.engine.MouseListener;
import game.engine.Prefabs;
import game.engine.Window;

public class Gizmo extends Component {
	private Vector4f xAxisColor = new Vector4f(1, 0, 0, 1);
	private Vector4f xAxisColorHover = new Vector4f(0.5f, 0, 0, 1);

	private Vector4f yAxisColor = new Vector4f(0, 1, 0, 1);
	private Vector4f yAxisColorHover = new Vector4f(0, 0.5f, 0, 1);

	private GameObject xAxisObject;
	private GameObject yAxisObject;

	private SpriteRenderer xAxisSprite;
	private SpriteRenderer yAxisSprite;

	protected GameObject activeGameObject = null;

	private Vector2f xAxisOffset = new Vector2f();
	private Vector2f yAxisOffset = new Vector2f();

	private float gizmoWidth = 0.5f;
	private float gizmoHeight = (48.0f / 16.0f) / 2;

	protected boolean xAxisActive = false;
	protected boolean yAxisActive = false;

	private PropertiesWindow propertiesWindow;

	public Gizmo(Sprite arrowSprite, PropertiesWindow propertiesWindow) {
		this.propertiesWindow = propertiesWindow;

		this.xAxisObject = Prefabs.generateSpriteObject(arrowSprite, gizmoWidth, gizmoHeight, 2);
		this.yAxisObject = Prefabs.generateSpriteObject(arrowSprite, gizmoWidth, gizmoHeight, 2);

        this.xAxisObject.addComponent(new NonPickable());
        this.yAxisObject.addComponent(new NonPickable());

		this.xAxisSprite = this.xAxisObject.getComponent(SpriteRenderer.class);
		this.yAxisSprite = this.yAxisObject.getComponent(SpriteRenderer.class);

		this.xAxisOffset = new Vector2f(1.75f, -0.25f);
		this.yAxisOffset = new Vector2f(0.25f, 1.75f);

		Window.getScene().addGameObject(this.xAxisObject);
		Window.getScene().addGameObject(this.yAxisObject);


	}

	@Override
	public void start() {
		this.xAxisObject.transform.rotation = 90;
		this.yAxisObject.transform.rotation = 180;
		this.xAxisObject.setNoSerialize();
		this.yAxisObject.setNoSerialize();

	}

	@Override
	public void update(float dt) {

		this.activeGameObject = propertiesWindow.getActiveGameObject();

		if (this.activeGameObject != null) {
			this.xAxisObject.transform.position.set(this.activeGameObject.transform.position);
			this.yAxisObject.transform.position.set(this.activeGameObject.transform.position);

			this.xAxisObject.transform.position.add(this.xAxisOffset);
			this.yAxisObject.transform.position.add(this.yAxisOffset);

			this.setActive();
		} else {
			this.setInactive();
			return;
		}

		boolean xAxisHot = checkXHoverState();
		boolean yAxisHot = checkYHoverState();

		if ((xAxisHot) && MouseListener.isDragging() && MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
			xAxisActive = true;
			yAxisActive = false;
		} else if ((yAxisHot) && MouseListener.isDragging() && MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
			xAxisActive = false;
			yAxisActive = true;
		} else {
			xAxisActive = false;
			yAxisActive = false;
		}
	}

	public void setActive() {
		this.xAxisSprite.setColor(this.xAxisColor);
		this.yAxisSprite.setColor(this.yAxisColor);
	}

	public void setInactive() {
		this.activeGameObject = null;
		this.xAxisSprite.setColor(new Vector4f(0, 0, 0, 0));
		this.yAxisSprite.setColor(new Vector4f(0, 0, 0, 0));
	}

	private boolean checkXHoverState() {
		Vector2f mousePos = MouseListener.getWorld();
		if (mousePos.x <= xAxisObject.transform.position.x &&
				mousePos.x >= xAxisObject.transform.position.x - gizmoHeight &&
				mousePos.y >= xAxisObject.transform.position.y &&
				mousePos.y <= xAxisObject.transform.position.y + gizmoWidth) {
			this.xAxisSprite.setColor(xAxisColorHover);
			return true;
		}
		this.xAxisSprite.setColor(xAxisColor);
		return false;
	}

	private boolean checkYHoverState() {
		Vector2f mousePos = MouseListener.getWorld();
		if (mousePos.x <= yAxisObject.transform.position.x &&
				mousePos.x >= yAxisObject.transform.position.x - gizmoWidth &&
				mousePos.y <= yAxisObject.transform.position.y &&
				mousePos.y >= yAxisObject.transform.position.y - gizmoHeight) {
			this.yAxisSprite.setColor(yAxisColorHover);
			return true;
		}
		this.yAxisSprite.setColor(yAxisColor);
		return false;
	}
}
