package game.components;

import org.joml.Vector2f;
import org.joml.Vector4f;

import game.editor.JImGui;
import game.engine.Transform;
import game.renderer.Texture;

public class SpriteRenderer extends Component {
	private Vector4f color = new Vector4f(1, 1, 1, 1);
	private Sprite sprite = new Sprite();

	private transient Transform lastTransform;
	private transient boolean isDirty = true;

	// public SpriteRenderer(Vector4f color) {
	// this.color = color;
	// // this.sprite = new Sprite(null);
	// this.sprite = new Sprite(null);
	// }
	//
	// public SpriteRenderer(Sprite sprite) {
	// this.sprite = sprite;
	// this.color = new Vector4f(1, 1, 1, 1);
	// // this.lastTransform = gameObject.transform;
	// }

	@Override
	public void start() {
		this.lastTransform = gameObject.transform.copy();
	}

	@Override
	public void update(float dt) {
		if (!this.lastTransform.equals(this.gameObject.transform)) {
			this.gameObject.transform.copy(this.lastTransform);

			isDirty = true;
		}
	}

	@Override
	public void editorUpdate(float dt) {
		if (!this.lastTransform.equals(this.gameObject.transform)) {
			this.gameObject.transform.copy(this.lastTransform);

			isDirty = true;
		}
	}

	@Override
	public void imgui() {
		if(JImGui.colorPicker4("Color Picker: ", this.color)) {
			this.isDirty = true;
		}


	}

	public Vector4f getColor() {
		return this.color;
	}

	public Texture getTexture() {
		return sprite.getTexture();
	}

	public Vector2f[] getTexCoords() {
		return sprite.getTexCoords();
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
		isDirty = true;
	}

	public void setColor(Vector4f color) {
		if (!this.color.equals(color)) {
			this.color.set(color);
			isDirty = true;
		}
	}

	public boolean isDirty() {
		return this.isDirty;
	}

	public void setDirty() {
		this.isDirty = true;
	}

	public void setClean() {
		this.isDirty = false;
	}

	public void setTexture(Texture texture) {
		this.sprite.setTexture(texture);
	}

}
