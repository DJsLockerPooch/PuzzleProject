package mygame;

import com.jme3.math.Vector3f;
import com.jme3.scene.shape.Box;

/**
 * A helper class to the RoomAppState that holds the ugly code
 *
 * @author Jason Van Dyke
 */
public class RoomHelper {

    private final float[] doorTemplate = {2f, 8f}; // Represents half the width and height of the door
    private final float[] doorPlatformTemplate = {6f, 1f, 4f};
    private final int roomCode;
    private int numWalls;
    private Vector3f entryLocation;
    private Vector3f exitLocation;

    protected RoomHelper(int code) {
        this.roomCode = code;
        numWalls = wallCount();
    }

    /**
     * Returns the number of panels that make up the room
     *
     * @return
     */
    protected int getNumWalls() {
        return numWalls;
    }

    /**
     * Determines the number of panels needed to make up the room based on the
     * provided code.
     *
     * @return an int representing the number of panels for the room walls
     */
    private int wallCount() {
        switch (roomCode) {
            case 0:
                numWalls = 8; // Beginning end room, exit level with ground
                break;
            case 1:
                numWalls = 8; // Final end room, entrance level with ground
                break;
            case 2:
                numWalls = 10; // Beginning end room, elevated exit
                break;
            case 3:
                numWalls = 10; // Final end room, elevated entrance
                break;
            case 4:
                numWalls = 10; // Middle room, level entrance and exit
                break;
            case 5:
                numWalls = 12; // Middle room, elevated entrance, level exit
                break;
            case 6:
                numWalls = 12; // Middle room, level entrance, elevated exit
                break;
            case 7:
                numWalls = 14; // Middle room, elevated entrance and exit
                break;
            case 21: // Special case for tutorial room
                numWalls = 11; // Beginning end room, elevated exit and elevated start minus the door
                break;
            default:
                numWalls = 8; // Default: Beginning end room, exit level with ground
                break;
        }
        return numWalls;
    }

    /**
     * Determines dimensions of all the walls and initial door platforms
     *
     * @param length a float for the room length
     * @param height a float for the room height
     * @param width a float for the room width
     * @return an array of Box Spatials making up the walls and door platforms
     */
    protected Box[] findWallDims(float length, float height, float width) {
        Box[] wallDims = new Box[numWalls];
        float wallThiccness = .05f;

        // Pre-determined panel sizes
        Box normalWallA = new Box(wallThiccness, height, width); // left/right
        Box normalWallB = new Box(length, wallThiccness, width); // top/bottom
        Box normalWallC = new Box(length, height, wallThiccness); // front/back
        Box doorSidePanel = new Box((length / 2) - doorTemplate[0], height, wallThiccness); // left/right of door
        Box doorFullTop = new Box(doorTemplate[0] * 2, height - doorTemplate[1], wallThiccness); // above door that's level with ground
        Box doorHalfTop = new Box(doorTemplate[0] * 2, (height - doorTemplate[1]) / 2, wallThiccness); // above/below elevated door
        Box doorMat = new Box(doorPlatformTemplate[0], doorPlatformTemplate[1], doorPlatformTemplate[2]); // panel at doorstep

        switch (roomCode) {
            case 0: // Beginning end room, exit level with ground
                wallDims[0] = normalWallC; // 1 back
                wallDims[1] = doorSidePanel; // 2.1 frontLeft
                wallDims[2] = doorSidePanel; // 2.2  frontRight
                wallDims[3] = doorFullTop; // 2.3  frontTop
                break;
            case 1: // Final end room, entrance level with ground
                wallDims[0] = doorSidePanel; // 1.1 backRight
                wallDims[1] = doorSidePanel; // 1.2 backLeft
                wallDims[2] = doorFullTop; // 1.3 backTop
                wallDims[3] = normalWallC; // 2 front
                break;
            case 2: // Beginning end room, elevated exit
                wallDims[0] = normalWallC; // 1 back
                wallDims[1] = doorSidePanel; // 2.1 frontLeft
                wallDims[2] = doorSidePanel; // 2.2  frontRight
                wallDims[3] = doorHalfTop; // 2.3  frontTop
                wallDims[4] = doorHalfTop; // 2.4 frontBottom
                wallDims[5] = doorMat; // front door platform
                break;
            case 3: // Final end room, elevated entrance
                wallDims[0] = doorSidePanel; // 1.1 backRight
                wallDims[1] = doorSidePanel; // 1.2  backLeft
                wallDims[2] = doorHalfTop; // 1.3  backTop
                wallDims[3] = doorHalfTop; // 1.4 backBottom
                wallDims[4] = doorMat; // back door platform
                wallDims[5] = normalWallC; // 2 front
                break;
            case 4: // Middle room, level entrance and exit
                wallDims[0] = doorSidePanel; // 1.1 backRight
                wallDims[1] = doorSidePanel; // 1.2  backLeft
                wallDims[2] = doorFullTop; // 1.3  backTop
                wallDims[3] = doorSidePanel; // 2.1 frontLeft
                wallDims[4] = doorSidePanel; // 2.2  frontRight
                wallDims[5] = doorFullTop; // 2.3  frontTop
                break;
            case 5: // Middle room, elevated entrance, level exit
                wallDims[0] = doorSidePanel; // 1.1 backRight
                wallDims[1] = doorSidePanel; // 1.2  backLeft
                wallDims[2] = doorHalfTop; // 1.3  backTop
                wallDims[3] = doorHalfTop; // 1.4 backBottom
                wallDims[4] = doorMat; // back door platform
                wallDims[5] = doorSidePanel; // 2.1 frontLeft
                wallDims[6] = doorSidePanel; // 2.2  frontRight
                wallDims[7] = doorFullTop; // 2.3  frontTop
                break;
            case 6: // Middle room, level entrance, elevated exit
                wallDims[0] = doorSidePanel; // 1.1 backRight
                wallDims[1] = doorSidePanel; // 1.2 backLeft
                wallDims[2] = doorFullTop; // 1.3 backTop
                wallDims[3] = doorSidePanel; // 2.1 frontLeft
                wallDims[4] = doorSidePanel; // 2.2  frontRight
                wallDims[5] = doorHalfTop; // 2.3  frontTop
                wallDims[6] = doorHalfTop; // 2.4 frontBottom
                wallDims[7] = doorMat; // front door platform
                break;
            case 7: // Middle room, elevated entrance and exit
                wallDims[0] = doorSidePanel; // 1.1 backRight
                wallDims[1] = doorSidePanel; // 1.2  backLeft
                wallDims[2] = doorHalfTop; // 1.3  backTop
                wallDims[3] = doorHalfTop; // 1.4 backBottom
                wallDims[4] = doorMat; // back door platform
                wallDims[5] = doorSidePanel; // 2.1 frontLeft
                wallDims[6] = doorSidePanel; // 2.2  frontRight
                wallDims[7] = doorHalfTop; // 2.3  frontTop
                wallDims[8] = doorHalfTop; // 2.4 frontBottom
                wallDims[9] = doorMat; // front door platform
                break;
            case 21: // Special case for tutorial room
                wallDims[0] = normalWallC; // 1 back
                wallDims[1] = doorSidePanel; // 2.1 frontLeft
                wallDims[2] = doorSidePanel; // 2.2  frontRight
                wallDims[3] = doorHalfTop; // 2.3  frontTop
                wallDims[4] = doorHalfTop; // 2.4 frontBottom
                wallDims[5] = doorMat; // front door platform
                wallDims[6] = doorMat; // back door platform for spawn
                break;
            default:
                wallDims[0] = normalWallC; // 1 back
                wallDims[1] = doorSidePanel; // 2.1 frontLeft
                wallDims[2] = doorSidePanel; // 2.2  frontRight
                wallDims[3] = doorFullTop; // 2.3  frontTop
                break;
        }

        wallDims[wallDims.length - 4] = normalWallB; // 3 top
        wallDims[wallDims.length - 3] = normalWallB; // 4 bottom
        wallDims[wallDims.length - 2] = normalWallA; // 5 right
        wallDims[wallDims.length - 1] = normalWallA; // 6 left

        return wallDims;
    }

    /**
     * Determines the locations of the walls and initial door platforms
     *
     * @param length a float for the room length
     * @param height a float for the room height
     * @param width a float for the room width
     * @return a 2-D array holding all wall locations
     */
    protected float[][] findWallLoc(float length, float height, float width) {
        float[][] wallLocs = new float[numWalls][3];

        // Pre-determined wall locations
        float[] frontLoc = {0, 0, -width};
        float[] frontLeftLoc = {(-doorTemplate[0] * (float) 1.5) - ((length - doorTemplate[0]) / 2), 0, -width};
        float[] frontRightLoc = {(doorTemplate[0] * (float) 1.5) + ((length - doorTemplate[0]) / 2), 0, -width};
        float[] frontFullTopLoc = {0, doorTemplate[1], -width};
        float[] frontHalfTopLoc = {0, height - ((height - doorTemplate[1]) / 2), -width};
        float[] frontHalfBottomLoc = {0, -height + ((height - doorTemplate[1]) / 2), -width};
        float[] frontDoorMatLoc = {0, -doorTemplate[1] - doorPlatformTemplate[1], -width + doorPlatformTemplate[2]};

        float[] backLoc = {0, 0, width}; // back
        float[] backRightLoc = {(-doorTemplate[0] * (float) 1.5) - ((length - doorTemplate[0]) / 2), 0, width};
        float[] backLeftLoc = {(doorTemplate[0] * (float) 1.5) + ((length - doorTemplate[0]) / 2), 0, width};
        float[] backFullTopLoc = {0, doorTemplate[1], width};
        float[] backHalfTopLoc = {0, height - ((height - doorTemplate[1]) / 2), width};
        float[] backHalfBottomLoc = {0, -height + ((height - doorTemplate[1]) / 2), width};
        float[] backDoorMatLoc = {0, -doorTemplate[1] - doorPlatformTemplate[1], width - doorPlatformTemplate[2]};

        float[] topLoc = {0, height, 0}; // top
        float[] bottomLoc = {0, -height, 0}; // bottom
        float[] rightLoc = {length, 0, 0}; // right
        float[] leftLoc = {-length, 0, 0}; // left

        Vector3f levelEntryVector = new Vector3f(0, -height, width);
        Vector3f levelExitVector = new Vector3f(0, -height, -width);
        Vector3f elevatedEntryVector = new Vector3f(0, -doorTemplate[1], width);
        Vector3f elevatedExitVector = new Vector3f(0, -doorTemplate[1], -width);

        switch (roomCode) {
            case 0: // Beginning end room, exit level with ground
                wallLocs[0] = backLoc;
                wallLocs[1] = frontLeftLoc;
                wallLocs[2] = frontRightLoc;
                wallLocs[3] = frontFullTopLoc;
                entryLocation = levelEntryVector.add(new Vector3f(0, 0, -1)); // -1 to move the entry location of the first room in 1 unit from the wall
                exitLocation = levelExitVector;
                break;
            case 1: // Final end room, entrance level with ground
                wallLocs[0] = backRightLoc;
                wallLocs[1] = backLeftLoc;
                wallLocs[2] = backFullTopLoc;
                wallLocs[3] = frontLoc;
                entryLocation = levelEntryVector; // -1 to move the entry location of the first room in 1 unit
                exitLocation = levelExitVector.add(new Vector3f(0, 0, 1)); // Moving the exit location in 1 unit from the wall
                break;
            case 2: // Beginning end room, elevated exit
                wallLocs[0] = backLoc;
                wallLocs[1] = frontLeftLoc;
                wallLocs[2] = frontRightLoc;
                wallLocs[3] = frontHalfTopLoc;
                wallLocs[4] = frontHalfBottomLoc;
                wallLocs[5] = frontDoorMatLoc;
                entryLocation = levelEntryVector.add(new Vector3f(0, 0, -1));
                exitLocation = elevatedExitVector;
                break;
            case 3: // Final end room, elevated entrance
                wallLocs[0] = backRightLoc;
                wallLocs[1] = backLeftLoc;
                wallLocs[2] = backHalfTopLoc;
                wallLocs[3] = backHalfBottomLoc;
                wallLocs[4] = backDoorMatLoc;
                wallLocs[5] = frontLoc;
                entryLocation = elevatedEntryVector;
                exitLocation = levelExitVector.add(new Vector3f(0, 0, 1));
                break;
            case 4: // Middle room, level entrance and exit
                wallLocs[0] = backRightLoc;
                wallLocs[1] = backLeftLoc;
                wallLocs[2] = backFullTopLoc;
                wallLocs[3] = frontLeftLoc;
                wallLocs[4] = frontRightLoc;
                wallLocs[5] = frontFullTopLoc;
                entryLocation = levelEntryVector;
                exitLocation = levelExitVector;
                break;
            case 5: // Middle room, elevated entrance, level exit
                wallLocs[0] = backRightLoc;
                wallLocs[1] = backLeftLoc;
                wallLocs[2] = backHalfTopLoc;
                wallLocs[3] = backHalfBottomLoc;
                wallLocs[4] = backDoorMatLoc;
                wallLocs[5] = frontLeftLoc;
                wallLocs[6] = frontRightLoc;
                wallLocs[7] = frontFullTopLoc;
                entryLocation = elevatedEntryVector;
                exitLocation = levelExitVector;
                break;
            case 6: // Middle room, level entrance, elevated exit
                wallLocs[0] = backRightLoc;
                wallLocs[1] = backLeftLoc;
                wallLocs[2] = backFullTopLoc;
                wallLocs[3] = frontLeftLoc;
                wallLocs[4] = frontRightLoc;
                wallLocs[5] = frontHalfTopLoc;
                wallLocs[6] = frontHalfBottomLoc;
                wallLocs[7] = frontDoorMatLoc;
                entryLocation = levelEntryVector;
                exitLocation = elevatedExitVector;
                break;
            case 7: // Middle room, elevated entrance and exit
                wallLocs[0] = backRightLoc;
                wallLocs[1] = backLeftLoc;
                wallLocs[2] = backHalfTopLoc;
                wallLocs[3] = backHalfBottomLoc;
                wallLocs[4] = backDoorMatLoc;
                wallLocs[5] = frontLeftLoc;
                wallLocs[6] = frontRightLoc;
                wallLocs[7] = frontHalfTopLoc;
                wallLocs[8] = frontHalfBottomLoc;
                wallLocs[9] = frontDoorMatLoc;
                entryLocation = elevatedEntryVector;
                exitLocation = elevatedExitVector;
                break;
            case 21: // Beginning end room, elevated exit
                wallLocs[0] = backLoc;
                wallLocs[1] = frontLeftLoc;
                wallLocs[2] = frontRightLoc;
                wallLocs[3] = frontHalfTopLoc;
                wallLocs[4] = frontHalfBottomLoc;
                wallLocs[5] = frontDoorMatLoc;
                wallLocs[6] = backDoorMatLoc;
                entryLocation = levelEntryVector.add(new Vector3f(0, 0, -1));
                exitLocation = elevatedExitVector;
                break;
            default:
                wallLocs[0] = backLoc;
                wallLocs[1] = frontLeftLoc;
                wallLocs[2] = frontRightLoc;
                wallLocs[3] = frontFullTopLoc;
                entryLocation = levelEntryVector.add(new Vector3f(0, 0, -1));
                exitLocation = levelExitVector;
                break;
        }

        wallLocs[numWalls - 4] = topLoc;
        wallLocs[numWalls - 3] = bottomLoc;
        wallLocs[numWalls - 2] = rightLoc;
        wallLocs[numWalls - 1] = leftLoc;

        return wallLocs;
    }

    protected void updateDoorLocations(Vector3f roomLocation, float length, float height, float width) {
        Vector3f tempVector = roomLocation;
        switch (roomCode) {
            case 0: // Beginning end room, exit level with ground
                entryLocation = new Vector3f(0, -height, width - 1); // width-1 to place player further along -z axis (that's the way the rooms extend)
                exitLocation = new Vector3f(0, -height, -width);
                break;
            case 1: // Final end room, entrance level with ground
                entryLocation = new Vector3f(0, -height, width);
                exitLocation = new Vector3f(0, -height, -width + 1);
                break;
            case 2: // Beginning end room, elevated exit
                entryLocation = new Vector3f(0, -height, width - 1);
                exitLocation = new Vector3f(0, -doorTemplate[1], -width);
                break;
            case 3: // Final end room, elevated entrance
                entryLocation = new Vector3f(0, -doorTemplate[1], width);
                exitLocation = new Vector3f(0, -height, -width + 1);
                break;
            case 4: // Middle room, level entrance and exit
                entryLocation = new Vector3f(0, -height, width);
                exitLocation = new Vector3f(0, -height, -width);
                break;
            case 5: // Middle room, elevated entrance, level exit
                entryLocation = new Vector3f(0, -doorTemplate[1], width);
                exitLocation = new Vector3f(0, -height, -width);
                break;
            case 6: // Middle room, level entrance, elevated exit
                entryLocation = new Vector3f(0, -height, width);
                exitLocation = new Vector3f(0, -doorTemplate[1], -width);
                break;
            case 7: // Middle room, elevated entrance and exit
                entryLocation = new Vector3f(0, -doorTemplate[1], width);
                exitLocation = new Vector3f(0, -doorTemplate[1], -width);
                break;
            case 21: //Special case for tutorial room
                entryLocation = new Vector3f(0, 0, width);
                exitLocation = new Vector3f(0, -doorTemplate[1], -width);
                break;
            default:
                entryLocation = new Vector3f(0, -height, width - 1);
                exitLocation = new Vector3f(0, -height, -width);
                break;
        }

        // Set entry and exit according to where the room currently is in space
        entryLocation = tempVector.add(entryLocation);
        exitLocation = tempVector.add(exitLocation);
    }

    /**
     * Returns the location of the center of the entrance's threshold
     *
     * @return a Vector3f for the location of the threshold
     */
    protected Vector3f getEntryLocation() {
        return entryLocation;
    }

    /**
     * Returns the location of the center of the exit's threshold
     *
     * @return a Vector3f for the location of the threshold
     */
    protected Vector3f getExitLocation() {
        return exitLocation;
    }
}