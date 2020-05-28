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

fun WCRenderScope.brick(brick: Brick) =
		group(key = brick.id) {
			+ collider(collider = brick.collider)
			+ rectFill(bounds = brick.bounds,
			           fillStyle = brick.color)
		}

data class Brick(val id: String,
                 val collider: Collider,
                 val bounds: Bounds,
                 val hpColorMap: Map<Int, Color>,
                 val hp: Int)
{
	val color get() = hpColorMap[hp] ?: Color.raw("white")

	fun hit() = if(hp > 1) copy(hp = hp - 1) else null
}
