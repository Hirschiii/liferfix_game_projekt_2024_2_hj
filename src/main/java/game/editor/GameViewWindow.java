package game.editor;

import org.joml.Vector2f;

import game.engine.MouseListener;
import game.engine.Window;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiWindowFlags;

public class GameViewWindow {

    private boolean isPlaying = false;
    private boolean windowIsHovered;


	public void imgui() {
        ImGui.begin("Game Viewport", ImGuiWindowFlags.NoScrollbar | ImGuiWindowFlags.NoScrollWithMouse
                        | ImGuiWindowFlags.MenuBar);

        ImGui.beginMenuBar();
        if (ImGui.menuItem("Play", "", isPlaying, !isPlaying)) {
            isPlaying = true;
            // EventSystem.notify(null, new Event(EventType.GameEngineStartPlay));
        }
        if (ImGui.menuItem("Stop", "", !isPlaying, isPlaying)) {
            isPlaying = false;
            // EventSystem.notify(null, new Event(EventType.GameEngineStopPlay));
        }
        ImGui.endMenuBar();

        ImGui.setCursorPos(ImGui.getCursorPosX(), ImGui.getCursorPosY());
        ImVec2 windowSize = getLargestSizeForWindow();
        ImVec2 windowPos = getCenteredPositionForViewPort(windowSize);
        ImGui.setCursorPos(windowPos.x, windowPos.y);

        int textureId = Window.getFramebuffer().getTextureID();
        ImGui.imageButton(textureId, windowSize.x, windowSize.y, 0, 1, 1, 0);
        windowIsHovered = ImGui.isItemHovered();

        MouseListener.setGameViewportPos(new Vector2f(windowPos.x + 10, windowPos.y + 30));
        MouseListener.setGameViewportSize(new Vector2f(windowSize.x, windowSize.y));

		ImGui.end();
	}

	private static ImVec2 getLargestSizeForWindow() {
        ImVec2 windowSize = new ImVec2();
        ImGui.getContentRegionAvail(windowSize);

		float aspectWidth = windowSize.x;
		float aspectHeight = aspectWidth / Window.getTargetAspectRatio();

		if (aspectHeight > windowSize.y) {
			aspectHeight = windowSize.y;
			aspectWidth = aspectHeight * Window.getTargetAspectRatio();
		} 
		// return new ImVec2(aspectWidth + ImGui.getCursorPosX(), aspectHeight + ImGui.getCursorPosY());
		return new ImVec2(aspectWidth, aspectHeight);
	}

	private static ImVec2 getCenteredPositionForViewPort(ImVec2 aspectSize) {
        ImVec2 windowSize = new ImVec2();
        ImGui.getContentRegionAvail(windowSize);

        float viewportX = (windowSize.x / 2.0f) - (aspectSize.x / 2.0f);
        float viewportY = (windowSize.y / 2.0f) - (aspectSize.y / 2.0f);

        return new ImVec2(viewportX + ImGui.getCursorPosX(), viewportY + ImGui.getCursorPosY());
	}

}
