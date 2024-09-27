package pacman.model.maze;

import pacman.entityfactory.FactoryCollectionSingleton;
import pacman.entityfactory.EntityFactory;
import pacman.model.entity.Renderable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static java.lang.System.exit;

/**
 * Responsible for creating renderables and storing it in the Maze
 */
public class MazeCreator {

    private final String fileName;
    public static final int RESIZING_FACTOR = 16;

    public MazeCreator(String fileName){
        this.fileName = fileName;
    }

    public Maze createMaze(){
        File f = new File(this.fileName);
        Maze maze = new Maze();

        try {
            Scanner scanner = new Scanner(f);

            int y = 0;

            while (scanner.hasNextLine()){

                String line = scanner.nextLine();
                char[] row = line.toCharArray();

                for (int x = 0; x < row.length; x++){
                    FactoryCollectionSingleton factoryCollection = FactoryCollectionSingleton.getInstance();
                    EntityFactory entityCreator = factoryCollection.getEntityCreator(row[x]);
                    Renderable renderable = entityCreator == null ? null : entityCreator.createRenderableObject(x * 16, y * 16);
                    maze.addRenderable(renderable, row[x], x, y);
                }
                y += 1;
            }

            scanner.close();
        }
        catch (FileNotFoundException e){
            System.out.println("No maze file was found.");
            exit(0);
        } catch (Exception e){
            System.out.println("Error");
            e.printStackTrace();
            exit(0);
        }

        return maze;
    }
}
