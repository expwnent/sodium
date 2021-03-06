import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import sodium.*;

public class bite {
    public static void main(String[] args)
    {
        Animate.animate(
            "Zombicus bite",

(double t0, Cell<Double> clock, Stream<Unit> sTick,
                                Dimension windowSize) -> {
    World world = new World(windowSize);
    List<Cell<Character>> chars = new ArrayList<>();
    List<Stream<Integer>> sBites = new ArrayList<>();
    CellLoop<List<Character>> scene = new CellLoop<>();
    StreamLoop<Integer> sBite = new StreamLoop<>();
    int id = 0;
    for (int x = 100; x < windowSize.width; x += 80)
        for (int y = 150; y < windowSize.height; y += 120) {
            Point pos0 = new Point(x, y);
            if (id != 3 && id != 21) {
                BitableHomoSapiens h = new BitableHomoSapiens(world, id,
                    t0, pos0, clock, sTick,
                    sBite, scene);
                chars.add(h.character);
                sBites.add(h.sBite);
            }
            else {
                HomoZombicus z = new HomoZombicus(id, t0, pos0,
                    clock, sTick, scene);
                chars.add(z.character);
                sBites.add(z.sBite);
            }
            id++;
        }
    Cell<List<Character>> scene_ = new Cell<>(new ArrayList<Character>());
    for (Cell<Character> c : chars) {
        scene_ = Cell.lift(
            (cc, l0) -> {
                List<Character> l = new ArrayList<Character>(l0);
                l.add(cc);
                return l;
            },
            c, scene_);
    }
    Stream<Integer> sBite_ = new Stream<Integer>();
    for (Stream<Integer> sb : sBites)
        sBite_ = sBite_.merge(sb);
    sBite.loop(sBite_);
    ArrayList<Character> emptyScene = new ArrayList<>();
    Cell<List<Character>> fixedScene = scene_.updates().hold(emptyScene);
    scene.loop(fixedScene);
    return scene;
}

        );
    }
}

