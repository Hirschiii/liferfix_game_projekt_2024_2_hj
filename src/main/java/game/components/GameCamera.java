package game.components;

import org.joml.Vector4f;

import game.components.enums.CameraModi;
import game.engine.Camera;
import game.engine.GameObject;
import game.engine.Window;

/**
 * GameCamera
 */
public class GameCamera extends Component {
    private transient GameObject player;
    private transient Camera gameCamera;
    private transient float cameraBuffer = 1.5f;
    private transient float playerBuffer = 0.25f;
    public transient CameraModi camMode;

    private Vector4f skyColor = new Vector4f(104.0f / 255.0f, 159.0f / 255.0f, 56.0f / 255.0f, 1.0f);
    private Vector4f undergroundColor = new Vector4f(0, 0, 0, 1);

    public GameCamera(Camera gameCamera) {
        this.gameCamera = gameCamera;
        this.camMode = CameraModi.FOLLOWING;
    }

    @Override
    public void start() {
        this.player = Window.getScene().getGameObjectWith(PlayerController.class);
        this.gameCamera.clearColor.set(skyColor);
    }

    @Override
    public void update(float dt) {
        switch (camMode) {
            case CameraModi.FOLLOWING:
                followGO(player);
                break;
            case CameraModi.STATIC:
                break;

            default:
                break;
        }
    }

    private void followGO(GameObject go) {
        if (go == null)
            return;

        gameCamera.position.x = go.transform.position.x - (gameCamera.getProjectionSize().x / 2);
        gameCamera.position.y = go.transform.position.y - (gameCamera.getProjectionSize().y / 2);

    }
}