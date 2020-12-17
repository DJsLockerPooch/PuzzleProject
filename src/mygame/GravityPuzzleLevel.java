package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import static mygame.PuzzleProject.boxZone;
import static mygame.PuzzleProject.boxZone2;
import static mygame.PuzzleProject.boxZone3;

/**
 * Class to create a gravity/block level in the PuzzleProject game
 *
 * @author brady.lamansky
 */
public class GravityPuzzleLevel {

    Node nodeToAttach;
    AssetManager assetManager;
    BulletAppState bulletAppState;
    PuzzleSpatial stairs;
    PuzzleBox boxs;
    PuzzleBox abBoxs;
    PuzzleBox zoneBarriers;
    static PuzzleBox box1;
    static PuzzleBox box2;
    static PuzzleBox box3;
    static PuzzleBox boxZoneBlock;
    static PuzzleBox boxZoneBlock2;
    static PuzzleBox boxZoneBlock3;
    Material defMaterial;
    Material defMaterial2;
    Material unshaded1;
    Material unshaded2;
    Material unshaded3;
    private Vector3f entryLoc;

    public GravityPuzzleLevel(Node nodeToAttach, AssetManager assetManager, BulletAppState bulletAppState, Vector3f entryLoc) {
        this.nodeToAttach = nodeToAttach;
        this.assetManager = assetManager;
        this.bulletAppState = bulletAppState;
        this.defMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        this.defMaterial2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        this.entryLoc = entryLoc;
        unshaded1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        unshaded2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        unshaded3 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Spatial stair = assetManager.loadModel("Models/stair.obj");

        stairs = new PuzzleSpatial(stair, defMaterial, nodeToAttach, entryLoc);

        createLevel();
    }

    private void createLevel() {

        //creating regular platforms
        boxs = createBox(newVector(10f, 1f, 20f), defMaterial);
        boxs.attachBox(newVector(0f, -10f, -30f), 0f);
        bulletAppState.getPhysicsSpace().add(boxs.getSpatialControl());

        boxs = createBox(newVector(10f, 1f, 20f), defMaterial);
        boxs.attachBox(newVector(-60f, -10f, -50f), 0f);
        bulletAppState.getPhysicsSpace().add(boxs.getSpatialControl());

        boxs = createBox(newVector(10f, 1f, 50f), defMaterial);
        boxs.attachBox(newVector(-50f, -10f, -250f), 0f);
        bulletAppState.getPhysicsSpace().add(boxs.getSpatialControl());

        //creating upside-down platforms
        abBoxs = createBox(newVector(40f, 1f, 10f), defMaterial2);
        abBoxs.attachBox(newVector(-30f, 20f, -40f), 0f);
        bulletAppState.getPhysicsSpace().add(abBoxs.getSpatialControl());

        abBoxs = createBox(newVector(10f, 1f, 50f), defMaterial2);
        abBoxs.attachBox(newVector(75f, 20f, -80f), 0f);
        bulletAppState.getPhysicsSpace().add(abBoxs.getSpatialControl());

        abBoxs = createBox(newVector(50f, 1f, 10f), defMaterial2);
        abBoxs.attachBox(newVector(35f, 20f, -140f), 0f);
        bulletAppState.getPhysicsSpace().add(abBoxs.getSpatialControl());

        abBoxs = createBox(newVector(2f, 1f, 2f), defMaterial2);
        abBoxs.attachBox(newVector(75f, 10f, -235f), 0f);
        bulletAppState.getPhysicsSpace().add(abBoxs.getSpatialControl());

        abBoxs = createBox(newVector(35f, 1f, 5f), defMaterial2);
        abBoxs.attachBox(newVector(-30f, 20f, -295f), 0f);
        bulletAppState.getPhysicsSpace().add(abBoxs.getSpatialControl());

        zoneBarriers = createBox(newVector(2f, 6f, 1f), unshaded2);
        zoneBarriers.attachBox(newVector(5f, 14f, -149f), 0f);
        bulletAppState.getPhysicsSpace().add(zoneBarriers.getSpatialControl());

        zoneBarriers = createBox(newVector(2f, 6f, 1f), unshaded3);
        zoneBarriers.attachBox(newVector(-10f, 14f, -149f), 0f);
        bulletAppState.getPhysicsSpace().add(zoneBarriers.getSpatialControl());

        //creating boxZones that move platforms
        boxZoneBlock = createBox(newVector(2f, 1f, 2f), unshaded1);
        boxZoneBlock.attachBox(newVector(5f, -9.5f, -45f), 0f);
        bulletAppState.getPhysicsSpace().add(boxZoneBlock.getSpatialControl());
        boxZone = newVector(5f, -9.5f, -45f);
        boxZoneBlock.setTexture(assetManager.loadTexture(new TextureKey("Materials/NormInteract.png")));

        boxZoneBlock2 = createBox(newVector(2f, 1f, 2f), unshaded2);
        boxZoneBlock2.attachBox(newVector(5f, 9f, -146f), 0f);
        bulletAppState.getPhysicsSpace().add(boxZoneBlock2.getSpatialControl());
        boxZone2 = newVector(5f, 9f, -146f);
        boxZoneBlock2.setTexture(assetManager.loadTexture(new TextureKey("Materials/NormInteract.png")));

        boxZoneBlock3 = createBox(newVector(2f, 1f, 2f), unshaded3);
        boxZoneBlock3.attachBox(newVector(-10f, 9f, -146f), 0f);
        bulletAppState.getPhysicsSpace().add(boxZoneBlock3.getSpatialControl());
        boxZone3 = newVector(-10f, 9f, -146f);
        boxZoneBlock3.setTexture(assetManager.loadTexture(new TextureKey("Materials/NormInteract.png")));

        //creating moving platforms
        box1 = createBox(newVector(20f, 1f, 10f), defMaterial);
        box1.attachBox(newVector(65f, -10f, -40f), 0f);
        bulletAppState.getPhysicsSpace().add(box1.getSpatialControl());

        box2 = createBox(newVector(10f, 1f, 50f), defMaterial);
        box2.attachBox(newVector(75f, -10f, -230f), 0f);
        bulletAppState.getPhysicsSpace().add(box2.getSpatialControl());

        box3 = createBox(newVector(30f, 1f, 10f), defMaterial2);
        box3.attachBox(newVector(55f, 20f, -270f), 0f);
        bulletAppState.getPhysicsSpace().add(box3.getSpatialControl());

        boxs.setTexture(assetManager.loadTexture(new TextureKey("Materials/NormGravMat.png")));
        abBoxs.setTexture(assetManager.loadTexture(new TextureKey("Materials/AbGravMat.png")));
    }

    /**
     * Function to create a new Vector3f with "x (length)", "y (height)" and "z
     * (width)"
     *
     * @param x: length of the vector
     * @param y: height of the vector
     * @param z: width of the vector
     * @return the vector created
     */
    public Vector3f newVector(float x, float y, float z) {
        return new Vector3f(x, y, z);
    }

    /**
     * Function to create and return a new box
     *
     * @param scale: the scale of the box
     * @param mat: the material of the box
     * @return the PuzzleBox being created
     */
    public PuzzleBox createBox(Vector3f scale, Material mat) {
        return new PuzzleBox(scale, mat, nodeToAttach, entryLoc);
    }

    /**
     * Adds the "box" parameter to the bulletAppState
     *
     * @param box: PuzzleBox being added to the bulletAppState
     */
    public void addToPhysics(PuzzleBox box) {
        bulletAppState.getPhysicsSpace().add(box.getSpatialControl());
    }
}
