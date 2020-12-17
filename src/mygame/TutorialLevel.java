package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import static mygame.PuzzleProject.boxZoneTut;

/**
 * Class to create a parkour level in the PuzzleProject game
 *
 * @author jackson-lamansky
 */
public class TutorialLevel {

    Node nodeToAttach;
    AssetManager assetManager;
    BulletAppState bulletAppState;
    PuzzleSpatial stairs;

    PuzzleBox unshadedBox, tutBox, abTutBox, moveSign, jumpSign, sprintSign, crouchSign, gravSign, boxSign2, buttonSign;
    static PuzzleBox tutBoxMove, abTutBoxMove, boxSign, tutBoxZone, tutBoxMove2, boxSignMove2;

    Material unshaded, defMaterial, defMaterial2, defMaterial3, moveTut, jumpTut, sprintTut, crouchTut, gravTut, boxTut, boxTut2, buttonTut;

    private Vector3f entryLoc;

    public TutorialLevel(Node nodeToAttach, AssetManager assetManager, BulletAppState bulletAppState, Vector3f entryLoc) {
        this.nodeToAttach = nodeToAttach;
        this.assetManager = assetManager;
        this.bulletAppState = bulletAppState;
        this.defMaterial = createMaterial();
        this.defMaterial2 = createMaterial();
        this.defMaterial3 = createMaterial();
        this.unshaded = createMaterial();
        
        this.moveTut = createMaterial();
        this.jumpTut = createMaterial();
        this.sprintTut = createMaterial();
        this.crouchTut = createMaterial();
        this.gravTut = createMaterial();
        this.boxTut = createMaterial();
        this.boxTut2 = createMaterial();
        this.buttonTut = createMaterial();

        this.entryLoc = entryLoc;
        Spatial stair = assetManager.loadModel("Models/stair.obj");

        stairs = new PuzzleSpatial(stair, defMaterial, nodeToAttach, entryLoc);

        createLevel();
    }

    private void createLevel() {

        //creating regular platforms
        tutBox = createBox(newVector(20f, 1f, 11.4f), defMaterial);
        tutBox.attachBox(newVector(10, -10f, -19f), 0f);
        bulletAppState.getPhysicsSpace().add(tutBox.getSpatialControl());

        tutBox = createBox(newVector(10f, 1f, 20f), defMaterial);
        tutBox.attachBox(newVector(20, -10f, -50f), 0f);
        bulletAppState.getPhysicsSpace().add(tutBox.getSpatialControl());

        tutBox = createBox(newVector(15f, 1f, 10f), defMaterial);
        tutBox.attachBox(newVector(-5f, -10f, -60f), 0f);
        bulletAppState.getPhysicsSpace().add(tutBox.getSpatialControl());

        tutBox = createBox(newVector(10f, 1f, 20f), defMaterial);
        tutBox.attachBox(newVector(-50f, -10f, -70f), 0f);
        bulletAppState.getPhysicsSpace().add(tutBox.getSpatialControl());

        tutBox = createBox(newVector(10f, 1f, 10f), defMaterial);
        tutBox.attachBox(newVector(-50f, -10f, -140f), 0f);
        bulletAppState.getPhysicsSpace().add(tutBox.getSpatialControl());

        tutBox = createBox(newVector(40f, 1f, 30f), defMaterial);
        tutBox.attachBox(newVector(-30f, -10f, -180f), 0f);
        bulletAppState.getPhysicsSpace().add(tutBox.getSpatialControl());

        //creating moving platforms
        tutBoxMove = createBox(newVector(10f, 1f, 5f), defMaterial);
        tutBoxMove.attachBox(newVector(-40f, -5f, -280f), 0f);
        bulletAppState.getPhysicsSpace().add(tutBoxMove.getSpatialControl());

        tutBoxMove2 = createBox(newVector(10f, 1f, 20f), defMaterial);
        tutBoxMove2.attachBox(newVector(-40f, -50f, -245f), 0f);
        bulletAppState.getPhysicsSpace().add(tutBoxMove2.getSpatialControl());

        //creating signs for controls
        moveSign = createBox(newVector(9f, 9f, .75f), moveTut);
        moveSign.attachBox(newVector(0, 0f, -30.5f), 0f);
        bulletAppState.getPhysicsSpace().add(moveSign.getSpatialControl());

        crouchSign = createBox(newVector(10f, 7.5f, .75f), crouchTut);
        crouchSign.attachBox(newVector(20f, 5f, -49f), 0f);
        bulletAppState.getPhysicsSpace().add(crouchSign.getSpatialControl());

        jumpSign = createBox(newVector(1f, 2f, 10f), jumpTut);
        jumpSign.attachBox(newVector(0f, -7f, -60f), 0f);
        bulletAppState.getPhysicsSpace().add(jumpSign.getSpatialControl());

        sprintSign = createBox(newVector(1f, 10f, 10f), sprintTut);
        sprintSign.attachBox(newVector(-60f, 0f, -60f), 0f);
        bulletAppState.getPhysicsSpace().add(sprintSign.getSpatialControl());

        gravSign = createBox(newVector(4f, 4f, 1f), gravTut);
        gravSign.attachBox(newVector(-56f, -5f, -90f), 0f);
        bulletAppState.getPhysicsSpace().add(gravSign.getSpatialControl());

        buttonSign = createBox(newVector(6f, 6f, 1f), buttonTut);
        buttonSign.attachBox(newVector(-60f, -4f, -210f), 0f);
        bulletAppState.getPhysicsSpace().add(buttonSign.getSpatialControl());

        boxSign = createBox(newVector(10f, 10f, 1f), boxTut);
        boxSign.attachBox(newVector(-40f, 5f, -285f), 0f);
        bulletAppState.getPhysicsSpace().add(boxSign.getSpatialControl());

        boxSign2 = createBox(newVector(1f, 10f, 10f), boxTut2);
        boxSign2.attachBox(newVector(10f, 0f, -200f), 0f);
        bulletAppState.getPhysicsSpace().add(boxSign2.getSpatialControl());

        //creating upside-down platforms
        abTutBox = createBox(newVector(10f, 1f, 50f), defMaterial2);
        abTutBox.attachBox(newVector(-50f, 20f, -100f), 0f);
        bulletAppState.getPhysicsSpace().add(abTutBox.getSpatialControl());

        abTutBox = createBox(newVector(10f, 1f, 4f), defMaterial2);
        abTutBox.attachBox(newVector(0f, 20f, -3.9f), 0f);
        bulletAppState.getPhysicsSpace().add(abTutBox.getSpatialControl());

        abTutBox = createBox(newVector(20f, 1f, 11.4f), defMaterial2);
        abTutBox.attachBox(newVector(10f, 20f, -19f), 0f);
        bulletAppState.getPhysicsSpace().add(abTutBox.getSpatialControl());

        abTutBox = createBox(newVector(10f, 1f, 20f), defMaterial2);
        abTutBox.attachBox(newVector(20f, 20f, -50f), 0f);
        bulletAppState.getPhysicsSpace().add(abTutBox.getSpatialControl());

        abTutBox = createBox(newVector(10f, 1f, 10f), defMaterial2);
        abTutBox.attachBox(newVector(20f, -1f, -60f), 0f);
        bulletAppState.getPhysicsSpace().add(abTutBox.getSpatialControl());

        abTutBox = createBox(newVector(15f, 1f, 10f), defMaterial2);
        abTutBox.attachBox(newVector(-5f, 20f, -60f), 0f);
        bulletAppState.getPhysicsSpace().add(abTutBox.getSpatialControl());

        abTutBox = createBox(newVector(42f, 1f, 30f), defMaterial2);
        abTutBox.attachBox(newVector(-30f, 20f, -180f), 0f);
        bulletAppState.getPhysicsSpace().add(abTutBox.getSpatialControl());

        //creating moving upside-down platforms
        abTutBoxMove = createBox(newVector(10f, 1f, 50f), defMaterial2);
        abTutBoxMove.attachBox(newVector(0f, 19f, -197.5f), 0f);
        bulletAppState.getPhysicsSpace().add(abTutBoxMove.getSpatialControl());

        //creating boxZones that move platforms
        tutBoxZone = createBox(newVector(2f, 1f, 2f), defMaterial3);
        tutBoxZone.attachBox(newVector(-5f, -9.5f, -200f), 0f);
        bulletAppState.getPhysicsSpace().add(tutBoxZone.getSpatialControl());
        boxZoneTut = newVector(-5f, -9.5f, -200f);

        //creating walls
        tutBox = createBox(newVector(1f, 50f, 13f), defMaterial);
        tutBox.attachBox(newVector(-10f, 0f, -17.5f), 0f);
        bulletAppState.getPhysicsSpace().add(tutBox.getSpatialControl());

        tutBox = createBox(newVector(12.8f, 50f, 4.1f), defMaterial);
        tutBox.attachBox(newVector(17.5f, 0f, -4f), 0f);
        bulletAppState.getPhysicsSpace().add(tutBox.getSpatialControl());

        tutBox = createBox(newVector(2.5f, 50f, 4.1f), defMaterial);
        tutBox.attachBox(newVector(-6.5f, 0f, -4f), 0f);
        bulletAppState.getPhysicsSpace().add(tutBox.getSpatialControl());

        tutBox = createBox(newVector(1f, 50f, 32f), defMaterial);
        tutBox.attachBox(newVector(30.5f, 0f, -38.5f), 0f);
        bulletAppState.getPhysicsSpace().add(tutBox.getSpatialControl());

        tutBox = createBox(newVector(25f, 50f, 10f), defMaterial);
        tutBox.attachBox(newVector(-15f, 0f, -40f), 0f);
        bulletAppState.getPhysicsSpace().add(tutBox.getSpatialControl());

        tutBox = createBox(newVector(35.5f, 50f, 40f), defMaterial);
        tutBox.attachBox(newVector(-4.9f, 0f, -110.25f), 0f);
        bulletAppState.getPhysicsSpace().add(tutBox.getSpatialControl());

        tutBox = createBox(newVector(10f, 50f, 2f), defMaterial);
        tutBox.attachBox(newVector(-49f, 0f, -47.5f), 0f);
        bulletAppState.getPhysicsSpace().add(tutBox.getSpatialControl());

        tutBox = createBox(newVector(5f, 50f, 55f), defMaterial);
        tutBox.attachBox(newVector(-65f, 0f, -95f), 0f);
        bulletAppState.getPhysicsSpace().add(tutBox.getSpatialControl());

        tutBox = createBox(newVector(.5f, 50f, 30f), defMaterial);
        tutBox.attachBox(newVector(-70f, 0f, -180f), 0f);
        bulletAppState.getPhysicsSpace().add(tutBox.getSpatialControl());

        tutBox = createBox(newVector(.5f, 50f, 30f), defMaterial);
        tutBox.attachBox(newVector(10f, 0f, -180f), 0f);
        bulletAppState.getPhysicsSpace().add(tutBox.getSpatialControl());

        tutBox = createBox(newVector(.5f, 50f, 45f), defMaterial);
        tutBox.attachBox(newVector(-70.5f, 0f, -252.5f), 0f);
        bulletAppState.getPhysicsSpace().add(tutBox.getSpatialControl());

        tutBox = createBox(newVector(.5f, 50f, 45f), defMaterial);
        tutBox.attachBox(newVector(-10.5f, 0f, -252.5f), 0f);
        bulletAppState.getPhysicsSpace().add(tutBox.getSpatialControl());

        tutBox = createBox(newVector(.5f, 50f, 45f), defMaterial);
        tutBox.attachBox(newVector(10.5f, 0f, -252.5f), 0f);
        bulletAppState.getPhysicsSpace().add(tutBox.getSpatialControl());

        tutBox = createBox(newVector(.5f, 19f, 10f), defMaterial);
        tutBox.attachBox(newVector(-19.5f, -30f, -60f), 0f);
        bulletAppState.getPhysicsSpace().add(tutBox.getSpatialControl());

        tutBox = createBox(newVector(.5f, 19f, 11f), defMaterial);
        tutBox.attachBox(newVector(-40.5f, -30f, -60f), 0f);
        bulletAppState.getPhysicsSpace().add(tutBox.getSpatialControl());

        tutBox = createBox(newVector(10f, 19f, .5f), defMaterial);
        tutBox.attachBox(newVector(-50f, -30f, -89f), 0f);
        bulletAppState.getPhysicsSpace().add(tutBox.getSpatialControl());

        tutBox = createBox(newVector(10f, 19f, .5f), defMaterial);
        tutBox.attachBox(newVector(-50f, -30f, -131f), 0f);
        bulletAppState.getPhysicsSpace().add(tutBox.getSpatialControl());

        abTutBox = createBox(newVector(.5f, 11f, 10f), defMaterial2);
        abTutBox.attachBox(newVector(10f, 8.5f, -60f), 0f);
        bulletAppState.getPhysicsSpace().add(abTutBox.getSpatialControl());

        abTutBox = createBox(newVector(.5f, 14.75f, 10f), defMaterial2);
        abTutBox.attachBox(newVector(-19f, 34.25f, -60f), 0f);
        bulletAppState.getPhysicsSpace().add(abTutBox.getSpatialControl());

        abTutBox = createBox(newVector(.5f, 14.75f, 11f), defMaterial2);
        abTutBox.attachBox(newVector(-40f, 34.25f, -60f), 0f);
        bulletAppState.getPhysicsSpace().add(abTutBox.getSpatialControl());

        unshadedBox = createBox(newVector(10f, 1f, 10f), unshaded);
        unshadedBox.attachBox(newVector(20f, 0f, -60f), 0f);
        bulletAppState.getPhysicsSpace().add(unshadedBox.getSpatialControl());

        //setting all block textures
        tutBox.setTexture(assetManager.loadTexture(new TextureKey("Materials/NormGravMat.png")));
        abTutBox.setTexture(assetManager.loadTexture(new TextureKey("Materials/AbGravMat.png")));
        tutBoxZone.setTexture(assetManager.loadTexture(new TextureKey("Materials/NormInteract.png")));
        unshadedBox.setTexture(assetManager.loadTexture(new TextureKey("Materials/Unshaded.png")));

        moveSign.setTexture(assetManager.loadTexture(new TextureKey("Materials/MoveSign.png")));
        jumpSign.setTexture(assetManager.loadTexture(new TextureKey("Materials/JumpSign.png")));
        sprintSign.setTexture(assetManager.loadTexture(new TextureKey("Materials/SprintSign.png")));
        crouchSign.setTexture(assetManager.loadTexture(new TextureKey("Materials/CrouchSign.png")));
        gravSign.setTexture(assetManager.loadTexture(new TextureKey("Materials/GravitySign.png")));
        boxSign.setTexture(assetManager.loadTexture(new TextureKey("Materials/BoxSign.png")));
        boxSign2.setTexture(assetManager.loadTexture(new TextureKey("Materials/BoxSign2.png")));
        buttonSign.setTexture(assetManager.loadTexture(new TextureKey("Materials/ButtonSign.png")));
    }

    /**
     * Function to create a newVector with "x (length)", "y (height)" and "z
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
     * Function to create and return the new box
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
    
    public Material createMaterial(){
        return new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    }
}
