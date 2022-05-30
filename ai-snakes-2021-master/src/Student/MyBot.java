package Student;

import snakes.Bot;
import snakes.Coordinate;
import snakes.Direction;
import snakes.Snake;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class MyBot implements Bot {
    private static final Direction[] DIRECTIONS = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};

    @Override
    public Direction chooseDirection(Snake snake, Snake opponent, Coordinate mazeSize, Coordinate apple) {
        //coordenada de la cabeza
        Coordinate head = snake.getHead();

        //para la coordenada del cuerpo anterior de la cabeza
        Coordinate afterHeadNotFinal = null;
        if (snake.body.size() >= 2) {
            Iterator<Coordinate> it = snake.body.iterator();
            it.next();
            afterHeadNotFinal = it.next();
        }
        //valor de la coordenada anterior de la cabeza
        final Coordinate afterHead = afterHeadNotFinal;

        //lista de movimientos excluyendo la direccion de la parte anterior de la cabeza
        Direction[] validMoves = Arrays.stream(DIRECTIONS)
                .filter(d -> !head.moveTo(d).equals(afterHead))
                .sorted()
                .toArray(Direction[]::new);

        //lista de movimientos que puede hacer la serpiente sin perder (tocar su propio cuerpo/cuerpo de la oponente/bordes del mapa)
        Direction[] notLosing = Arrays.stream(validMoves)
                .filter(d -> head.moveTo(d).inBounds(mazeSize))
                .filter(d -> !opponent.elements.contains(head.moveTo(d)))
                .filter(d -> !snake.elements.contains(head.moveTo(d)))
                .sorted()
                .toArray(Direction[]::new);

        if (notLosing.length > 0) return notLosing[0];
        else return validMoves[0];
    }
}
