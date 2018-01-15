package view;

import view.HomeScene;
import view.Scene;

public class SceneManager {

    private Scene scene;

    public SceneManager() {
        scene = new HomeScene();
    }

    public void main() {
        while (true) {
            while (!scene.requestTransition)
                scene.process();
            scene = scene.transitionNext();
        }
    }
}
