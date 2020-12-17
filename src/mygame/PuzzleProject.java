package mygame;

import com.jme3.bullet.BulletAppState;
import com.jme3.light.AmbientLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.app.SimpleApplication;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;

public class PuzzleProject extends SimpleApplication {

    // Initializing variables
    private BulletAppState bulletAppState;
    private static RoomAppState[] rasArray = new RoomAppState[6]; // Update this to increase amount of rooms
    private boolean placedRoomsYet = false;
    public static boolean boxHeld = false;
    public static boolean boxHeld2 = false;
    public static Vector3f boxZone = (new Vector3f(0, 0, 0));
    public static Vector3f boxZone2 = (new Vector3f(0, 0, 0));
    public static Vector3f boxZone3 = (new Vector3f(0, 0, 0));
    public static Vector3f boxZoneTut = (new Vector3f(0, 0, 0));
    public static int activeRoom = -1;
    public static Vector3f activeCoordinates;
    public static boolean evilButton;

    /**
     * Method that creates a new PuzzleProject
     *
     * @param args
     */
    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setFrameRate(500); //limit max frame rate ot 500, enable vsync, so theoretical max frame rate capped at 60
        settings.setVSync(true);
        PuzzleProject app = new PuzzleProject();
        app.setSettings(settings);
        app.start();
    }

    /**
     * Function to initialize our puzzle game
     */
    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
//        bulletAppState.setDebugEnabled(true);
        stateManager.attach(bulletAppState);

        createRoom(); // Create room before adding the ControlAppState so cas recognizes roomAppState
        createControl();

        viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));

        setUpLight();
    }

    /**
     * Function to give the map light to be able to see
     */
    private void setUpLight() {
        AmbientLight al = new AmbientLight();
        al.setColor(ColorRGBA.White.mult(1.3f));
        rootNode.addLight(al);
    }

    /**
     * Function to place the rooms if they haven't been placed yet
     *
     * @param tpf
     */
    @Override
    public void simpleUpdate(float tpf) {
        if (!placedRoomsYet) {
            placeRooms();

            TutorialLevel tutLevel = new TutorialLevel(rasArray[0].getNodeRoom(), assetManager, bulletAppState, rasArray[0].getExitLocation());
            ParkourLevel level = new ParkourLevel(rasArray[1].getNodeRoom(), assetManager, bulletAppState, rasArray[1].getExitLocation());
            GravityPuzzleLevel gravLevel = new GravityPuzzleLevel(rasArray[2].getNodeRoom(), assetManager, bulletAppState, rasArray[2].getExitLocation());
            EvilRoom evilRoom = new EvilRoom(rasArray[3].getNodeRoom(), assetManager, bulletAppState, rasArray[3].getExitLocation());
            GravityLevel level2 = new GravityLevel(rasArray[4].getNodeRoom(), assetManager, bulletAppState, rasArray[4].getExitLocation());
            EndRoom roomEnd = new EndRoom(rasArray[5].getNodeRoom(), assetManager, bulletAppState, rasArray[5].getExitLocation());
            
            activeRoom = 0;
            // If you want to respawn at a particular room, change the z coordinate to -300, -600, or -900
            activeCoordinates = new Vector3f(0, 0, 0);
            for (RoomAppState ras : rasArray) {
                ras.updateDoorLocations();
            }
            stateManager.getState(ControlAppState.class).setUpRooms();
            placedRoomsYet = true;
        }
    }

    /**
     * Function to use the RoomAppState to create rooms
     */
    public void createRoom() {
        // Change dimensions as needed. Room code definitions can be found in RoomHelper
        // Enter half of what you need because the dimensions get doubled in method calls
        rasArray[0] = new RoomAppState(100f, 50f, 150f, 21); // Tutorial room
        rasArray[1] = new RoomAppState(100f, 50f, 150f, 7); // Gravity/Puzzle room
        rasArray[2] = new RoomAppState(100f, 50f, 150f, 7); // Parkour room
        rasArray[3] = new RoomAppState(100f, 50f, 150f, 7); // Evil room
        rasArray[4] = new RoomAppState(200f, 100f, 200f, 4); // Gravity Room
        
        rasArray[5] = new RoomAppState(10, 6f, 15, 7);
        // Attaching each RoomAppState to the state manager
        for (RoomAppState state : rasArray) {
            stateManager.attach(state);
        }
    }

    /**
     * Function to place all of the rooms based on the locations of previous
     * rooms, and their entry and exit locations
     */
    private void placeRooms() {
        Node tempNode;
        // Placing the first room
        rasArray[0].getNodeRoom().setLocalTranslation(0, 0, -(rasArray[0].getWidth() - 1));
        rasArray[0].addPhysics();
        rasArray[0].setEnabled(true);
        // Current position will be the center of the next room
        Vector3f currentPosition = (rasArray[0].getExitLocation().add(new Vector3f(0, 0, -rasArray[0].getWidth())));
        // Placing the remaining rooms in rasArray
        for (int i = 1; i < rasArray.length; i++) {
            tempNode = rasArray[i].getNodeRoom();
            // Getting the entry location of the current room, and adding the negative entry location to get to the end of the first room
            currentPosition = currentPosition.add(rasArray[i].getEntryLocation().negate().add(new Vector3f(0, 0, 0.5f)));
            // Setting the node location to this position
            tempNode.setLocalTranslation(currentPosition);
            // If we're not at the end, add the exit location of the room to current position to get to the center of the next room
            if (i < rasArray.length - 1) {
                currentPosition = currentPosition.add(rasArray[i].getExitLocation());
            }
            // Add physics and enable the room
            rasArray[i].addPhysics();
            rasArray[i].setEnabled(true);
        }
    }

    /**
     * Function to create the ControlAppState (player/player functionality)
     */
    public void createControl() {
        ControlAppState cas = new ControlAppState(guiFont, settings, assetManager);
        stateManager.attach(cas);
    }

    /**
     * Sends rasArray to ControlAppState to track player's position
     *
     * @return
     */
    protected static RoomAppState[] getRooms() {
        return rasArray;
    }
}