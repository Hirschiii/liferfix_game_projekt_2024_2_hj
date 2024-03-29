package game.engine;

import org.joml.Vector2f;

import game.components.AnimationState;
import game.components.InteraktiveGizmo;
import game.components.Item;
import game.components.PlayerController;
import game.components.PlayerController_two;
import game.components.PositionAbsolut;
import game.components.Rigidbody;
import game.components.Sprite;
import game.components.SpriteRenderer;
import game.components.Spritesheet;
import game.components.StateMachine;
import game.components.Message;
import game.components.interactives.breakOnItem;
import game.components.interactives.change_color;
import game.components.interactives.grow_in_size;
import game.util.AssetPool;

public class Prefabs {
    public static GameObject generateSpriteObject(Sprite sprite, float sizeX, float sizeY) {
        GameObject block = Window.getScene().createGameObject("Sprite_Object_Gen");
        block.transform.scale = new Vector2f(sizeX, sizeY);

        SpriteRenderer renderer = new SpriteRenderer();
        renderer.setSprite(sprite);
        block.addComponent(renderer);

        return block;
    }

    public static GameObject generateSpriteObject(Sprite sprite, String name, float sizeX, float sizeY) {
        GameObject block = Window.getScene().createGameObject(name);
        block.transform.scale = new Vector2f(sizeX, sizeY);

        SpriteRenderer renderer = new SpriteRenderer();
        renderer.setSprite(sprite);
        block.addComponent(renderer);

        return block;
    }

    public static GameObject generatePlayer_Two() {
        Spritesheet playerSprites = AssetPool.getSpritesheet("assets/spriteSheets/CharacterAnimation.png");
        GameObject player = generateSpriteObject(playerSprites.getSprite(0), 1, 1);
        player.name = "Player";
        AnimationState idleFront = new AnimationState();

        idleFront.title = "IdleFront";
        float defaultFrameTime = 0.35f;

        idleFront.addFrame(playerSprites.getSprite(0), defaultFrameTime);
        idleFront.addFrame(playerSprites.getSprite(1), defaultFrameTime);
        idleFront.addFrame(playerSprites.getSprite(2), defaultFrameTime);
        idleFront.addFrame(playerSprites.getSprite(3), defaultFrameTime);

        idleFront.setLoop(true);

        AnimationState idleBack = new AnimationState();

        idleBack.title = "IdleBack";

        int idleBackOffset = 9;
        idleBack.addFrame(playerSprites.getSprite(idleBackOffset), defaultFrameTime);
        idleBack.addFrame(playerSprites.getSprite(idleBackOffset + 1), defaultFrameTime);
        idleBack.addFrame(playerSprites.getSprite(idleBackOffset + 2), defaultFrameTime);
        idleBack.addFrame(playerSprites.getSprite(idleBackOffset + 3), defaultFrameTime);

        idleBack.setLoop(true);

        AnimationState idleSideLeft = new AnimationState();

        idleSideLeft.title = "IdleSideLeft";

        int idleSideLeftOffset = 25;
        idleSideLeft.addFrame(playerSprites.getSprite(idleSideLeftOffset), defaultFrameTime);
        idleSideLeft.addFrame(playerSprites.getSprite(idleSideLeftOffset + 1), defaultFrameTime);
        idleSideLeft.addFrame(playerSprites.getSprite(idleSideLeftOffset + 2), defaultFrameTime);
        idleSideLeft.addFrame(playerSprites.getSprite(idleSideLeftOffset + 3), defaultFrameTime);

        idleSideLeft.setLoop(true);

        AnimationState idleSideRight = new AnimationState();

        idleSideRight.title = "IdleSideRight";

        int idleSideRightOffset = 17;
        idleSideRight.addFrame(playerSprites.getSprite(idleSideRightOffset), defaultFrameTime);
        idleSideRight.addFrame(playerSprites.getSprite(idleSideRightOffset + 1), defaultFrameTime);
        idleSideRight.addFrame(playerSprites.getSprite(idleSideRightOffset + 2), defaultFrameTime);
        idleSideRight.addFrame(playerSprites.getSprite(idleSideRightOffset + 3), defaultFrameTime);

        idleSideRight.setLoop(true);

        AnimationState runDown = new AnimationState();
        runDown.title = "RunDown";

        int runDownOffset = 4;
        runDown.addFrame(playerSprites.getSprite(runDownOffset), defaultFrameTime);
        runDown.addFrame(playerSprites.getSprite(runDownOffset + 1), defaultFrameTime);
        runDown.addFrame(playerSprites.getSprite(runDownOffset + 2), defaultFrameTime);
        runDown.addFrame(playerSprites.getSprite(runDownOffset + 3), defaultFrameTime);

        runDown.setLoop(true);

        AnimationState runUp = new AnimationState();
        runUp.title = "RunUp";

        int runUpOffset = 12;
        runUp.addFrame(playerSprites.getSprite(runUpOffset), defaultFrameTime);
        runUp.addFrame(playerSprites.getSprite(runUpOffset + 1), defaultFrameTime);
        runUp.addFrame(playerSprites.getSprite(runUpOffset + 2), defaultFrameTime);
        runUp.addFrame(playerSprites.getSprite(runUpOffset + 3), defaultFrameTime);

        runUp.setLoop(true);

        AnimationState runRight = new AnimationState();
        runRight.title = "RunRight";

        int runRightOffset = 20;
        runRight.addFrame(playerSprites.getSprite(runRightOffset), defaultFrameTime);
        runRight.addFrame(playerSprites.getSprite(runRightOffset + 1), defaultFrameTime);

        runRight.setLoop(true);

        AnimationState runLeft = new AnimationState();
        runLeft.title = "RunLeft";

        int runLeftOffset = 28;
        runLeft.addFrame(playerSprites.getSprite(runLeftOffset), defaultFrameTime);
        runLeft.addFrame(playerSprites.getSprite(runLeftOffset + 1), defaultFrameTime);

        runLeft.setLoop(true);

        AnimationState switchDirection = new AnimationState();
        switchDirection.title = "Switch Direction";
        switchDirection.addFrame(playerSprites.getSprite(0), 0.1f);
        switchDirection.setLoop(false);

        StateMachine stateMachine = new StateMachine();

        stateMachine.addState(idleFront);
        stateMachine.addState(idleBack);
        stateMachine.addState(idleSideLeft);
        stateMachine.addState(idleSideRight);
        stateMachine.addState(switchDirection);
        stateMachine.addState(runDown);
        stateMachine.addState(runUp);
        stateMachine.addState(runLeft);
        stateMachine.addState(runRight);

        stateMachine.setDefaultState(idleFront.title);

        stateMachine.addState(switchDirection.title, runRight.title, "StartRunRight");
        stateMachine.addState(switchDirection.title, runLeft.title, "StartRunLeft");
        stateMachine.addState(switchDirection.title, runUp.title, "StartRunUp");
        stateMachine.addState(switchDirection.title, runDown.title, "StartRunDown");

        stateMachine.addState(switchDirection.title, idleFront.title, "StopRun");

        stateMachine.addState(idleBack.title, runRight.title, "StartRunRight");
        stateMachine.addState(idleBack.title, runLeft.title, "StartRunLeft");
        stateMachine.addState(idleBack.title, runUp.title, "StartRunUp");
        stateMachine.addState(idleBack.title, runDown.title, "StartRunDown");

        stateMachine.addState(idleFront.title, runRight.title, "StartRunRight");
        stateMachine.addState(idleFront.title, runLeft.title, "StartRunLeft");
        stateMachine.addState(idleFront.title, runUp.title, "StartRunUp");
        stateMachine.addState(idleFront.title, runDown.title, "StartRunDown");

        stateMachine.addState(idleSideRight.title, runRight.title, "StartRunRight");
        stateMachine.addState(idleSideRight.title, runLeft.title, "StartRunLeft");
        stateMachine.addState(idleSideRight.title, runUp.title, "StartRunUp");
        stateMachine.addState(idleSideRight.title, runDown.title, "StartRunDown");

        stateMachine.addState(idleSideLeft.title, runRight.title, "StartRunRight");
        stateMachine.addState(idleSideLeft.title, runLeft.title, "StartRunLeft");
        stateMachine.addState(idleSideLeft.title, runUp.title, "StartRunUp");
        stateMachine.addState(idleSideLeft.title, runDown.title, "StartRunDown");

        stateMachine.addState(runRight.title, idleSideRight.title, "StopRun");
        stateMachine.addState(runRight.title, switchDirection.title, "SwitchDirection");
        stateMachine.addState(runLeft.title, idleSideLeft.title, "StopRun");
        stateMachine.addState(runLeft.title, switchDirection.title, "SwitchDirection");
        stateMachine.addState(runDown.title, idleFront.title, "StopRun");
        stateMachine.addState(runDown.title, switchDirection.title, "SwitchDirection");
        stateMachine.addState(runUp.title, idleBack.title, "StopRun");
        stateMachine.addState(runUp.title, switchDirection.title, "SwitchDirection");

        player.addComponent(stateMachine);

        player.addComponent(new PlayerController_two());
        player.addComponent(new Rigidbody(new Vector2f(0f, -0.34f), new Vector2f(0.7f, 0.3f)));
        player.transform.zIndex = 2;

        return player;
    }


    public static GameObject generatePlayer() {
        Spritesheet playerSprites = AssetPool.getSpritesheet("assets/spriteSheets/CharacterAnimation.png");
        GameObject player = generateSpriteObject(playerSprites.getSprite(0), 1, 1);
        player.name = "Player";
        AnimationState idleFront = new AnimationState();

        idleFront.title = "IdleFront";
        float defaultFrameTime = 0.35f;

        idleFront.addFrame(playerSprites.getSprite(0), defaultFrameTime);
        idleFront.addFrame(playerSprites.getSprite(1), defaultFrameTime);
        idleFront.addFrame(playerSprites.getSprite(2), defaultFrameTime);
        idleFront.addFrame(playerSprites.getSprite(3), defaultFrameTime);

        idleFront.setLoop(true);

        AnimationState idleBack = new AnimationState();

        idleBack.title = "IdleBack";

        int idleBackOffset = 9;
        idleBack.addFrame(playerSprites.getSprite(idleBackOffset), defaultFrameTime);
        idleBack.addFrame(playerSprites.getSprite(idleBackOffset + 1), defaultFrameTime);
        idleBack.addFrame(playerSprites.getSprite(idleBackOffset + 2), defaultFrameTime);
        idleBack.addFrame(playerSprites.getSprite(idleBackOffset + 3), defaultFrameTime);

        idleBack.setLoop(true);

        AnimationState idleSideLeft = new AnimationState();

        idleSideLeft.title = "IdleSideLeft";

        int idleSideLeftOffset = 25;
        idleSideLeft.addFrame(playerSprites.getSprite(idleSideLeftOffset), defaultFrameTime);
        idleSideLeft.addFrame(playerSprites.getSprite(idleSideLeftOffset + 1), defaultFrameTime);
        idleSideLeft.addFrame(playerSprites.getSprite(idleSideLeftOffset + 2), defaultFrameTime);
        idleSideLeft.addFrame(playerSprites.getSprite(idleSideLeftOffset + 3), defaultFrameTime);

        idleSideLeft.setLoop(true);

        AnimationState idleSideRight = new AnimationState();

        idleSideRight.title = "IdleSideRight";

        int idleSideRightOffset = 17;
        idleSideRight.addFrame(playerSprites.getSprite(idleSideRightOffset), defaultFrameTime);
        idleSideRight.addFrame(playerSprites.getSprite(idleSideRightOffset + 1), defaultFrameTime);
        idleSideRight.addFrame(playerSprites.getSprite(idleSideRightOffset + 2), defaultFrameTime);
        idleSideRight.addFrame(playerSprites.getSprite(idleSideRightOffset + 3), defaultFrameTime);

        idleSideRight.setLoop(true);

        AnimationState runDown = new AnimationState();
        runDown.title = "RunDown";

        int runDownOffset = 4;
        runDown.addFrame(playerSprites.getSprite(runDownOffset), defaultFrameTime);
        runDown.addFrame(playerSprites.getSprite(runDownOffset + 1), defaultFrameTime);
        runDown.addFrame(playerSprites.getSprite(runDownOffset + 2), defaultFrameTime);
        runDown.addFrame(playerSprites.getSprite(runDownOffset + 3), defaultFrameTime);

        runDown.setLoop(true);

        AnimationState runUp = new AnimationState();
        runUp.title = "RunUp";

        int runUpOffset = 12;
        runUp.addFrame(playerSprites.getSprite(runUpOffset), defaultFrameTime);
        runUp.addFrame(playerSprites.getSprite(runUpOffset + 1), defaultFrameTime);
        runUp.addFrame(playerSprites.getSprite(runUpOffset + 2), defaultFrameTime);
        runUp.addFrame(playerSprites.getSprite(runUpOffset + 3), defaultFrameTime);

        runUp.setLoop(true);

        AnimationState runRight = new AnimationState();
        runRight.title = "RunRight";

        int runRightOffset = 20;
        runRight.addFrame(playerSprites.getSprite(runRightOffset), defaultFrameTime);
        runRight.addFrame(playerSprites.getSprite(runRightOffset + 1), defaultFrameTime);

        runRight.setLoop(true);

        AnimationState runLeft = new AnimationState();
        runLeft.title = "RunLeft";

        int runLeftOffset = 28;
        runLeft.addFrame(playerSprites.getSprite(runLeftOffset), defaultFrameTime);
        runLeft.addFrame(playerSprites.getSprite(runLeftOffset + 1), defaultFrameTime);

        runLeft.setLoop(true);

        AnimationState switchDirection = new AnimationState();
        switchDirection.title = "Switch Direction";
        switchDirection.addFrame(playerSprites.getSprite(0), 0.1f);
        switchDirection.setLoop(false);

        StateMachine stateMachine = new StateMachine();

        stateMachine.addState(idleFront);
        stateMachine.addState(idleBack);
        stateMachine.addState(idleSideLeft);
        stateMachine.addState(idleSideRight);
        stateMachine.addState(switchDirection);
        stateMachine.addState(runDown);
        stateMachine.addState(runUp);
        stateMachine.addState(runLeft);
        stateMachine.addState(runRight);

        stateMachine.setDefaultState(idleFront.title);

        stateMachine.addState(switchDirection.title, runRight.title, "StartRunRight");
        stateMachine.addState(switchDirection.title, runLeft.title, "StartRunLeft");
        stateMachine.addState(switchDirection.title, runUp.title, "StartRunUp");
        stateMachine.addState(switchDirection.title, runDown.title, "StartRunDown");

        stateMachine.addState(switchDirection.title, idleFront.title, "StopRun");

        stateMachine.addState(idleBack.title, runRight.title, "StartRunRight");
        stateMachine.addState(idleBack.title, runLeft.title, "StartRunLeft");
        stateMachine.addState(idleBack.title, runUp.title, "StartRunUp");
        stateMachine.addState(idleBack.title, runDown.title, "StartRunDown");

        stateMachine.addState(idleFront.title, runRight.title, "StartRunRight");
        stateMachine.addState(idleFront.title, runLeft.title, "StartRunLeft");
        stateMachine.addState(idleFront.title, runUp.title, "StartRunUp");
        stateMachine.addState(idleFront.title, runDown.title, "StartRunDown");

        stateMachine.addState(idleSideRight.title, runRight.title, "StartRunRight");
        stateMachine.addState(idleSideRight.title, runLeft.title, "StartRunLeft");
        stateMachine.addState(idleSideRight.title, runUp.title, "StartRunUp");
        stateMachine.addState(idleSideRight.title, runDown.title, "StartRunDown");

        stateMachine.addState(idleSideLeft.title, runRight.title, "StartRunRight");
        stateMachine.addState(idleSideLeft.title, runLeft.title, "StartRunLeft");
        stateMachine.addState(idleSideLeft.title, runUp.title, "StartRunUp");
        stateMachine.addState(idleSideLeft.title, runDown.title, "StartRunDown");

        stateMachine.addState(runRight.title, idleSideRight.title, "StopRun");
        stateMachine.addState(runRight.title, switchDirection.title, "SwitchDirection");
        stateMachine.addState(runLeft.title, idleSideLeft.title, "StopRun");
        stateMachine.addState(runLeft.title, switchDirection.title, "SwitchDirection");
        stateMachine.addState(runDown.title, idleFront.title, "StopRun");
        stateMachine.addState(runDown.title, switchDirection.title, "SwitchDirection");
        stateMachine.addState(runUp.title, idleBack.title, "StopRun");
        stateMachine.addState(runUp.title, switchDirection.title, "SwitchDirection");

        player.addComponent(stateMachine);

        player.addComponent(new PlayerController());
        player.addComponent(new Rigidbody(new Vector2f(0f, -0.34f), new Vector2f(0.7f, 0.3f)));
        player.transform.zIndex = 2;

        return player;
    }

    public static GameObject generateFullRigid(Sprite sprite) {
        GameObject street = generateSpriteObject(sprite, 1, 1);
        street.addComponent(new Rigidbody(new Vector2f(0, 0), new Vector2f(1, 1)));

        return street;
    }

    public static GameObject generateFullRigid(Sprite sprite, float width, float height) {
        GameObject street = generateSpriteObject(sprite, width, height);
        street.addComponent(new Rigidbody(new Vector2f(0, 0), new Vector2f(1, 1)));

        return street;
    }

    public static GameObject generateMessage(Sprite sprite, float width, float height) {
        GameObject street = generateSpriteObject(sprite, width, height);
        street.addComponent(new Message());

        return street;
    }


    public static GameObject generateAbsolut(Sprite sprite) {
        GameObject street = generateSpriteObject(sprite, 1, 1);
       street.addComponent(new PositionAbsolut());

        return street;
    }

    public static GameObject generateChest(Sprite sprite) {
        GameObject street = generateSpriteObject(sprite, 1, 1);
       street.addComponent(new PositionAbsolut());

        return street;
    }

    public static GameObject generateDoor(Sprite sprite) {
        GameObject street = generateSpriteObject(sprite, 1, 1);
       street.addComponent(new PositionAbsolut());

        return street;
    }

    public static GameObject generateItem(Sprite sprite, String name) {
        GameObject item = generateSpriteObject(sprite, 1, 1);

        SpriteRenderer renderer = new SpriteRenderer();
        renderer.setSprite(sprite);
        item.addComponent(renderer);
        item.addComponent(new PositionAbsolut());
        item.addComponent(new Item());

        return item;
    }

    public static GameObject generateFigure(Spritesheet sheet) {
        Spritesheet playerSprites = sheet;
        GameObject player = generateSpriteObject(playerSprites.getSprite(0), 1, 1);
        player.name = "Player";
        AnimationState idleFront = new AnimationState();

        idleFront.title = "IdleFront";
        float defaultFrameTime = 0.35f;

        idleFront.addFrame(playerSprites.getSprite(0), defaultFrameTime);
        idleFront.addFrame(playerSprites.getSprite(1), defaultFrameTime);
        idleFront.addFrame(playerSprites.getSprite(2), defaultFrameTime);
        idleFront.addFrame(playerSprites.getSprite(3), defaultFrameTime);

        idleFront.setLoop(true);

        StateMachine stateMachine = new StateMachine();

        stateMachine.addState(idleFront);
        stateMachine.setDefaultState(idleFront.title);

        player.addComponent(stateMachine);

        player.addComponent(new Rigidbody(new Vector2f(0f, -0.34f), new Vector2f(0.7f, 0.3f)));
        player.transform.zIndex = 2;

        return player;
    }

    public static GameObject generateZaun(Spritesheet sheet) {
        Spritesheet playerSprites = sheet;
        GameObject player = generateSpriteObject(playerSprites.getSprite(0), 1, 1);
        player.name = "Zaun";

        float defaultFrameTime = 0.35f;

        AnimationState open = new AnimationState();
        open.title = "Open";

        open.addFrame(playerSprites.getSprite(0), defaultFrameTime);
        open.addFrame(playerSprites.getSprite(1), defaultFrameTime);
        open.addFrame(playerSprites.getSprite(2), defaultFrameTime);

        open.setLoop(false);

        AnimationState close = new AnimationState();
        close.title = "Close";

        close.addFrame(playerSprites.getSprite(2), defaultFrameTime);
        close.addFrame(playerSprites.getSprite(1), defaultFrameTime);
        close.addFrame(playerSprites.getSprite(0), defaultFrameTime);

        close.setLoop(false);

        StateMachine stateMachine = new StateMachine();

        stateMachine.addState(open);
        stateMachine.addState(close);

        stateMachine.setDefaultState(close.title);

        stateMachine.addState(open.title, close.title, "Break");
        stateMachine.addState(close.title, open.title, "Break");

        player.addComponent(stateMachine);

        player.addComponent(new Rigidbody(new Vector2f(0f, 0f), new Vector2f(1f, 1f)));
        player.addComponent(new breakOnItem());

        return player;
    }

}
