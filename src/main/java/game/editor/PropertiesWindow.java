package game.editor;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector4f;

import game.components.Message;
import game.components.Rigidbody;
import game.components.SpriteRenderer;
import game.components.interactives.Amelie;
import game.components.interactives.Eric;
import game.components.interactives.Pipe;
import game.components.interactives.breakOnItem;
import game.components.interactives.getItem;
import game.engine.GameObject;
import game.renderer.PickingTexture;
import imgui.ImGui;

public class PropertiesWindow {
    private List<GameObject> activeGameObjects;
    private List<Vector4f> activeGameObjectsOgColor;
    private GameObject activeGameObject = null;
    private PickingTexture pickingTexture;

    public PropertiesWindow(PickingTexture pickingTexture) {
        this.activeGameObjects = new ArrayList<>();
        this.activeGameObjectsOgColor = new ArrayList<>();
        this.pickingTexture = pickingTexture;
    }

    public void imgui() {
        if (activeGameObjects.size() == 1 && activeGameObjects.get(0) != null) {
            activeGameObject = activeGameObjects.get(0);
            ImGui.begin("Properties");

            if (ImGui.beginPopupContextWindow("ComponentAdder")) {
                if (activeGameObject.getComponent(Rigidbody.class) == null) {
                    if (ImGui.menuItem("Add Rigidbody")) {
                        activeGameObject.addComponent(new Rigidbody(new Vector2f(0f, 0f), new Vector2f(1f, 1f)));
                    }
                }
                if (activeGameObject.getComponent(Pipe.class) == null) {
                    if (ImGui.menuItem("Add Pipe")) {
                        activeGameObject.addComponent(new Pipe());
                    }
                }
                if (activeGameObject.getComponent(getItem.class) == null) {
                    if (ImGui.menuItem("Add getItem")) {
                        activeGameObject.addComponent(new getItem());
                    }
                }
                if (activeGameObject.getComponent(breakOnItem.class) == null) {
                    if (ImGui.menuItem("Add breakOnItem")) {
                        activeGameObject.addComponent(new breakOnItem());
                    }
                }
                if (activeGameObject.getComponent(Message.class) == null) {
                    if (ImGui.menuItem("Add Message")) {
                        activeGameObject.addComponent(new Message());
                    }
                }
                if (activeGameObject.getComponent(Eric.class) == null) {
                    if (ImGui.menuItem("Add Eric")) {
                        activeGameObject.addComponent(new Eric());
                    }
                }
                if (activeGameObject.getComponent(Amelie.class) == null) {
                    if (ImGui.menuItem("Add Amelie")) {
                        activeGameObject.addComponent(new Amelie());
                    }
                }
                ImGui.endPopup();
            }

            activeGameObject.imgui();
            ImGui.end();
        }
    }

    public void setActiveGameObject(GameObject go) {
        if (go == null)
            return;
        SpriteRenderer spr = go.getComponent(SpriteRenderer.class);
        if (spr != null) {
            clearSelected();
            this.activeGameObjectsOgColor.add(new Vector4f(spr.getColor()));
            spr.setColor(new Vector4f(0.9f, 0.3f, 0.8f, 0.8f));
        } else {
            this.activeGameObjectsOgColor.add(new Vector4f());
        }
        this.activeGameObjects.add(go);

    }

    public GameObject getActiveGameObject() {
        return this.activeGameObjects.size() == 1 ? this.activeGameObjects.get(0) : null;
    }

    public void setInactive() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setInactive'");
    }

    public List<GameObject> getActiveGameObjects() {
        return this.activeGameObjects;
    }

    public void clearSelected() {
        if (activeGameObjectsOgColor.size() > 0) {
            int i = 0;
            for (GameObject go : activeGameObjects) {
                SpriteRenderer spr = go.getComponent(SpriteRenderer.class);
                if (spr != null) {
                    spr.setColor(activeGameObjectsOgColor.get(i));
                }
                i++;
            }
        }
        this.activeGameObjects.clear();
        this.activeGameObjectsOgColor.clear();
    }

    public void addActiveGameObjet(GameObject go) {
        SpriteRenderer spr = go.getComponent(SpriteRenderer.class);
        if (spr != null) {
            this.activeGameObjectsOgColor.add(new Vector4f(spr.getColor()));
            spr.setColor(new Vector4f(0.9f, 0.3f, 0.8f, 0.8f));
        } else {
            this.activeGameObjectsOgColor.add(new Vector4f());
        }
        this.activeGameObjects.add(go);
    }

    public PickingTexture getPickingTexture() {
        return this.pickingTexture;
    }

}
