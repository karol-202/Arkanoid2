package screens.game

import org.w3c.dom.Image
import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.component.containers.group
import pl.karol202.uranium.webcanvas.component.physics.collider.collider
import pl.karol202.uranium.webcanvas.component.primitives.image
import pl.karol202.uranium.webcanvas.component.primitives.rectFill
import pl.karol202.uranium.webcanvas.physics.collider.Collider
import pl.karol202.uranium.webcanvas.values.Bounds
import pl.karol202.uranium.webcanvas.values.Color

fun WCRenderScope.brick(brick: Brick) =
		group(key = brick.id) {
			+ collider(collider = brick.collider)
			+ image(image = brick.image,
					drawBounds = brick.bounds)
		}

data class Brick(val id: String,
                 val collider: Collider,
                 val bounds: Bounds,
                 val hpImageMap: Map<Int, Image>,
                 val hp: Int)
{
	val image get() = hpImageMap[hp] ?: throw IllegalStateException("No color defined for hp: $hp")

	fun hit() = if(hp > 1) copy(hp = hp - 1) else null
}
