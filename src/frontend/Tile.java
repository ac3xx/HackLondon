package frontend;

/**
 * Created by James on 28/02/15.
 */
public enum Tile {
    ROCK("rock"),
    WATER("water"),
    TREE("tree"),
    TREEALT("treealt"),
    GRASS("grass"),
    SAND("sand");

    private final String fileName;
    Tile(String fileName) {
        this.fileName = fileName;
    }
    public String getFileName() {
        return fileName;
    }

    public String getFile() {
        return fileName + ".png";
    }
    public String getFile(String file) {
        return file + ".png";
    }

    public String getTransitionWith(Tile otherTile, TileTransition transition) {
        int myOrdinal = ordinal();
        int otherOrdinal = otherTile.ordinal();
        String format = "";
        if (otherOrdinal < myOrdinal) {
            format = otherTile.getFileName() + "_"
                    + fileName;
        } else {
            format = fileName + "_"
                    + otherTile.getFileName();
        }

        return getFile(format + "_"
                + transition.ordinal());
    }

}
