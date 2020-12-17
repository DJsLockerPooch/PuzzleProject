package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * Class to create a parkour level in the PuzzleProject game
 *
 * @author jackson-lamansky
 */
public class EndRoom {

    Node nodeToAttach;
    AssetManager assetManager;
    BulletAppState bulletAppState;
    PuzzleSpatial stairs;

    PuzzleBox tutBox;

    Material unshaded, defMaterial;

    private Vector3f entryLoc;

    public EndRoom(Node nodeToAttach, AssetManager assetManager, BulletAppState bulletAppState, Vector3f entryLoc) {
        this.nodeToAttach = nodeToAttach;
        this.assetManager = assetManager;
        this.bulletAppState = bulletAppState;
        this.defMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        unshaded = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

        this.entryLoc = entryLoc;
        Spatial stair = assetManager.loadModel("Models/stair.obj");

        stairs = new PuzzleSpatial(stair, defMaterial, nodeToAttach, entryLoc);

        createLevel();
    }

    private void createLevel() {
        //creating victory block
        tutBox = new PuzzleBox(new Vector3f(10, 6f, 1), defMaterial, nodeToAttach, entryLoc);
        tutBox.attachBox(new Vector3f(0, 8f + this.entryLoc.y, -15f + this.entryLoc.z), 0f);
        bulletAppState.getPhysicsSpace().add(tutBox.getSpatialControl());

        tutBox.setTexture(assetManager.loadTexture(new TextureKey("Materials/Finish.png")));

    }
}
