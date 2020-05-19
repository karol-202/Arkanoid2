package screens.game

import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.component.containers.translate
import pl.karol202.uranium.webcanvas.component.physics.collider.collider
import pl.karol202.uranium.webcanvas.component.primitives.rectFill
import pl.karol202.uranium.webcanvas.physics.collider.RectCollider
import pl.karol202.uranium.webcanvas.values.Bounds
import pl.karol202.uranium.webcanvas.values.Color
import pl.karol202.uranium.webcanvas.values.Vector

val BRICK_BOUNDS = Bounds(-25.0, -12.5, 50.0, 25.0)

fun WCRenderScope.brick(brick: Brick) =
		translate(key = brick.id,
		          vector = brick.position) {
			+ collider(collider = RectCollider(bounds = BRICK_BOUNDS,
			                                   payload = ColliderType.Brick(brick.id)))
			+ rectFill(bounds = BRICK_BOUNDS,
			           fillStyle = Color.raw("green"))
		}

data class Brick(val id: Int,
                 val position: Vector,
                 val hp: Int)
{
	fun hit() = if(hp > 1) copy(hp = hp - 1) else null
}

fun generateBricks() = List(10) { id ->
	Brick(id = id,
	      position = Vector(100 + id * 100.0, 100.0),
	      hp = 1)
}
