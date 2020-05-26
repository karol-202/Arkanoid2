package screens.game

import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.component.containers.group
import pl.karol202.uranium.webcanvas.component.physics.collider.collider
import pl.karol202.uranium.webcanvas.component.primitives.rectFill
import pl.karol202.uranium.webcanvas.physics.collider.Collider
import pl.karol202.uranium.webcanvas.physics.collider.RectCollider
import pl.karol202.uranium.webcanvas.values.Bounds
import pl.karol202.uranium.webcanvas.values.Color
import pl.karol202.uranium.webcanvas.values.Path
import pl.karol202.uranium.webcanvas.values.Vector

private const val BRICK_WIDTH_HEIGHT_RATIO = 2.0

fun WCRenderScope.brick(brick: Brick) =
		group(key = brick.id) {
			+ collider(collider = brick.collider)
			+ rectFill(bounds = brick.centerBounds,
			           fillStyle = brick.color)
			/*+ pathFill(key = 1,
			           path = brick.leftPath,
			           fillStyle = brick.colorLighter)
			+ pathFill(key = 2,
			           path = brick.topPath,
			           fillStyle = brick.colorLighter)
			+ pathFill(key = 3,
			           path = brick.rightPath,
			           fillStyle = brick.colorDarker)
			+ pathFill(key = 4,
			           path = brick.bottomPath,
			           fillStyle = brick.colorDarker)*/
		}

data class Brick(val id: String,
                 val collider: Collider,
                 val centerBounds: Bounds,
                 val leftPath: Path,
                 val topPath: Path,
                 val rightPath: Path,
                 val bottomPath: Path,
                 val color: Color,
                 val colorLighter: Color,
                 val colorDarker: Color,
                 val hp: Int)
{
	fun hit() = if(hp > 1) copy(hp = hp - 1) else null
}

fun generateBricks(screenSize: Vector, rows: Int, columns: Int): List<Brick>
{
	val brickWidth = screenSize.x / columns
	val brickHeight = brickWidth / BRICK_WIDTH_HEIGHT_RATIO
	val brickSize = Vector(brickWidth, brickHeight)
	return (0 until rows).flatMap { row ->
		(0 until columns).map { column ->
			val id = "${column}x${row}"
			val bounds = Bounds(start = Vector(x = column * brickWidth, y = row * brickHeight),
			                    size = brickSize)
			val baseColor = Color.hsl(88, 0.92, 0.41)
			Brick(id = id,
			      collider = RectCollider(bounds = bounds,
			                              payload = ColliderType.Brick(id)),
			      /*centerBounds = Bounds(start = bounds.lerp(Vector(0.2, 0.2)),
			                            size = brickSize * 0.6),*/
			      centerBounds = bounds,
			      leftPath = Path.closed(bounds.lerp(Vector(0.0, 1.0)),
			                             bounds.lerp(Vector(0.0, 0.0)),
			                             bounds.lerp(Vector(0.2, 0.2)),
			                             bounds.lerp(Vector(0.2, 0.8))),
			      topPath = Path.closed(bounds.lerp(Vector(0.0, 0.0)),
			                            bounds.lerp(Vector(1.0, 0.0)),
			                            bounds.lerp(Vector(0.8, 0.2)),
			                            bounds.lerp(Vector(0.2, 0.2))),
			      rightPath = Path.closed(bounds.lerp(Vector(1.0, 0.0)),
			                              bounds.lerp(Vector(1.0, 1.0)),
			                              bounds.lerp(Vector(0.8, 0.8)),
			                              bounds.lerp(Vector(0.8, 0.2))),
			      bottomPath = Path.closed(bounds.lerp(Vector(1.0, 1.0)),
			                               bounds.lerp(Vector(0.0, 1.0)),
			                               bounds.lerp(Vector(0.2, 0.8)),
			                               bounds.lerp(Vector(0.8, 0.8))),
			      color = baseColor,
			      colorLighter = baseColor.copy(lightness = baseColor.lightness + 0.1),
			      colorDarker = baseColor.copy(lightness = baseColor.lightness - 0.1),
			      hp = 1)
		}
	}
}
