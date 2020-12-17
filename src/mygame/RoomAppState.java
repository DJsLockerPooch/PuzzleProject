package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Node;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

/**
 * Our custom AppState that handles room construction
 *
 * @author Jason Van Dyke
 */
public class RoomAppState extends AbstractAppState {

    // Constant declaration
    private final float roomLength;
    private final float roomHeight;
    private final float roomWidth;
    private final Geometry[] walls;
    private final int numWalls;
    private final int roomCode;
    private final RigidBodyControl[] wallPhy;
    private final RoomHelper roomHelp;

    // Variable declaration
    private AppStateManager stateManager;
    private AssetManager assetManager;
    private boolean roomEnabled = true; // Begin with room present for now
    private BulletAppState physics;
    private Node nodeRoom;
    private Node rootNode;
    private SimpleApplication app;

    /**
     * Overloaded constructor
     *
     * @param length half the length of the room (x-axis)
     * @param height half the height of the room (y-axis)
     * @param width half the width of the room (z-axis)
     * @param code an int designating the number of walls and placement of doors
     */
    public RoomAppState(float length, float height, float width, int code) {
        roomLength = length;
        roomHeight = height;
        roomWidth = width;
        roomCode = code;
        roomHelp = new RoomHelper(roomCode); // Create the helper object
        numWalls = roomHelp.getNumWalls();
        walls = new Geometry[this.numWalls];
        wallPhy = new RigidBodyControl[numWalls];
    }

    /**
     * Default init method from example appState in jME3 documentation
     *
     * @param stateManager
     * @param app
     */
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;          // cast to a more specific class
        this.nodeRoom = new Node("nodeRoom");
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        this.physics = this.stateManager.getState(BulletAppState.class);

        createWalls();
    }

    /**
     * Creates the walls and sets their materials
     */
    private void createWalls() {
        // Note: The player initially is facing -z when the game starts
        Box[] wallDims = roomHelp.findWallDims(roomLength, roomHeight, roomWidth);
        float[][] wallLoc = roomHelp.findWallLoc(roomLength, roomHeight, roomWidth);
        Material[] wallMats = new Material[numWalls];
        TextureKey wallTexture = new TextureKey("Materials/WallMat.png");
        wallTexture.setGenerateMips(true);

        for (int i = 0; i < walls.length; i++) {
            walls[i] = new Geometry("Wall", wallDims[i]);
            wallMats[i] = new Material(assetManager,
                    "Common/MatDefs/Misc/Unshaded.j3md");
            walls[i].setShadowMode(ShadowMode.CastAndReceive);
            Texture tex = assetManager.loadTexture(wallTexture);

            wallMats[i].setTexture("ColorMap", tex);
            walls[i].setMaterial(wallMats[i]);
            walls[i].setLocalTranslation(wallLoc[i][0], wallLoc[i][1], wallLoc[i][2]);
        }
    }

    /**
     * Adds collisions to the walls
     */
    protected void addPhysics() {
        for (int i = 0; i < walls.length; i++) {
            wallPhy[i] = new RigidBodyControl(0.0f);
            nodeRoom.attachChild(walls[i]);
            walls[i].addControl(wallPhy[i]);
            physics.getPhysicsSpace().add(wallPhy[i]);
        }
    }

    /**
     * Returns the length of the room (x-axis)
     *
     * @return roomLenght a float representing the room length
     */
    public float getLength() {
        return this.roomLength;
    }

    /**
     * Returns the height of the room (y-axis)
     *
     * @return roomHeight a float representing the room height
     */
    public float getHeight() {
        return this.roomHeight;
    }

    /**
     * Returns the width of the room (z-axis)
     *
     * @return roomWidth a float representing the room width
     */
    public float getWidth() {
        return this.roomWidth;
    }

    /**
     * Provides access to a specific room's node for easy attachment of
     * obstacles
     *
     * @return nodeRoom the node that controls the room
     */
    public Node getNodeRoom() {
        return nodeRoom;
    }

    /**
     * Determines entry and exit locations for the rooms once they've been
     * placed
     */
    protected void updateDoorLocations() {
        roomHelp.updateDoorLocations(nodeRoom.getLocalTranslation(), roomLength, roomHeight, roomWidth);
    }

    /**
     * Returns the location of the bottom center of the room's entrance
     *
     * @return entryLocation a Vector3f representing the center of the threshold
     * of the room's entrance
     */
    public Vector3f getEntryLocation() {
        return roomHelp.getEntryLocation();
    }

    /**
     * Returns the location of the bottom center of the room's exit
     *
     * @return exitLocation a Vector3f representing the center of the threshold
     * of the room's exit
     */
    public Vector3f getExitLocation() {
        return roomHelp.getExitLocation();
    }

    /**
     * Returns if the room is active; made to eliminate a variable in the main
     * class
     *
     * @return roomEnabled A boolean indicating if room is attached to rootNode
     * or not
     */
    public boolean getEnabled() {
        return roomEnabled;
    }

    /**
     * Attach or detach the walls from the rootNode
     *
     * @param enable
     */
    @Override
    public void setEnabled(boolean enable) {
        if (this.rootNode != null) {
            if (enable) {
                // Since we're enabling the room, we are going to attach all the children
                this.rootNode.attachChild(nodeRoom);
                for (RigidBodyControl rbc : wallPhy) {
                    physics.getPhysicsSpace().add(rbc);
                }
                roomEnabled = true;
            } // Since we're disabling the room, we are going to detach all the children
            else {
                this.rootNode.detachChild(nodeRoom);
                for (RigidBodyControl rbc : wallPhy) {
                    physics.getPhysicsSpace().remove(rbc);
                }
                roomEnabled = false;
            }
        }
    }

    // Note that update is only called while the state is both attached and enabled.
//    @Override
//    public void update(float tpf) {
//        // do the following while game is RUNNING
////      this.app.getRootNode().getChild("blah").scale(tpf); // modify scene graph...
//    }
//    @Override
//    public void cleanup() {
//        super.cleanup();
//        rootNode.detachAllChildren();
//    }
}
